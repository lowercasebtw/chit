package btw.lowercase.chit.property.select

import com.google.common.collect.HashMultiset
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.MapCodec
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.SelectItemModel.SwitchCase
import net.minecraft.client.renderer.item.SelectItemModel.UnbakedSwitch
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty.Type
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

/**
 *
 * NOTE:
 * I do not own this part of this code, this code has been sourced from 25w03a and
 * belongs to Mojang/Minecraft!
 *
 * I will remove this if any issues arise.
 *
 */

fun <T> validateCases(list: List<SwitchCase<T>>): DataResult<List<SwitchCase<T>>> {
    if (list.isEmpty()) {
        return DataResult.error {
            return@error "Empty case list"
        }
    } else {
        val multiset = HashMultiset.create<T>()
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val switchCase = iterator.next()
            multiset.addAll(switchCase.values())
        }

        return if (multiset.size != multiset.entrySet().size) {
            DataResult.error {
                val duplicates = multiset.entrySet()
                    .filter { it.count > 1 }
                    .joinToString(", ") { it.element.toString() }
                "Duplicate case conditions: $duplicates"
            }
        } else {
            DataResult.success(list)
        }
    }
}

fun <T> createCasesFieldCodec(codec: Codec<T>): MapCodec<List<SwitchCase<T>>> {
    return SwitchCase.codec(codec).listOf().validate(::validateCases).fieldOf("cases")
}

@Suppress("UNCHECKED_CAST")
class ComponentContents<T>(val componentType: DataComponentType<T>) : SelectItemModelProperty<T> {
    companion object {
        private val TYPE: Type<out ComponentContents<*>, *> = createType<Any>()

        private fun <T> createType(): Type<ComponentContents<T>, T> {
            val codec: Codec<out DataComponentType<*>> = BuiltInRegistries.DATA_COMPONENT_TYPE.byNameCodec()
                .validate { dataComponentType ->
                    if (dataComponentType.isTransient) {
                        DataResult.error { "Component can't be serialized" }
                    } else {
                        DataResult.success(dataComponentType)
                    }
                }

            return Type((codec as Codec<DataComponentType<T>>).dispatchMap("component", { unbakedSwitch ->
                (unbakedSwitch.property() as ComponentContents<*>).componentType as DataComponentType<T>?
            }, { dataComponentType ->
                createCasesFieldCodec(dataComponentType.codecOrThrow())
                    .xmap(
                        { list -> UnbakedSwitch(ComponentContents(dataComponentType), list) },
                        { UnbakedSwitch<*, *>::cases as List<SwitchCase<T>> }
                    )
            }))
        }

        fun <T> castType(): Type<ComponentContents<T>, T> {
            return TYPE as Type<ComponentContents<T>, T>
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): T? {
        return stack.get(componentType)
    }

    override fun type(): Type<ComponentContents<T>, T> {
        return castType()
    }
}