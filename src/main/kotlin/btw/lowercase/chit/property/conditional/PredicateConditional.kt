package btw.lowercase.chit.property.conditional

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.ItemSubPredicate
import net.minecraft.advancements.critereon.SingleComponentItemPredicate
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

class PredicateConditional(val predicate: Map<ItemSubPredicate.Type<*>, ItemSubPredicate>) :
    ConditionalItemModelProperty {
    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                SingleComponentItemPredicate.CODEC.fieldOf("predicate").forGetter(PredicateConditional::predicate)
            ).apply(instance, ::PredicateConditional)
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): Boolean {
        // TODO/NOTE: Enchantments/Potions have registry errors
        // TODO/NOTE: Best to use the other conditional
        for (entry in predicate.entries) {
            if (!entry.value.matches(stack)) {
                return false
            }
        }

        return true
    }

    override fun type(): MapCodec<out ConditionalItemModelProperty?> {
        return CODEC
    }
}