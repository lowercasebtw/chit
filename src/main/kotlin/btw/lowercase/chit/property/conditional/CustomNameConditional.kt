package btw.lowercase.chit.property.conditional

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty
import net.minecraft.util.ExtraCodecs
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import kotlin.text.Regex

class CustomNameConditional(val regex: String) : ConditionalItemModelProperty {
    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("regex").forGetter(CustomNameConditional::regex)
            ).apply(instance, ::CustomNameConditional)
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): Boolean {
        val regex = Regex.fromLiteral(regex)
        return if (stack.customName != null) {
            regex.matches(stack.customName!!.string)
        } else {
            false
        }
    }

    override fun type(): MapCodec<out ConditionalItemModelProperty?> {
        return CODEC
    }
}