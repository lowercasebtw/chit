package btw.lowercase.chit.property.conditional

import btw.lowercase.chit.EnchantmentUtil
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

class HasEnchantmentsConditional(val enchantmentIds: List<ResourceLocation>) :
    ConditionalItemModelProperty {
    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ResourceLocation.CODEC.listOf().fieldOf("enchantments")
                    .forGetter(HasEnchantmentsConditional::enchantmentIds)
            ).apply(instance, ::HasEnchantmentsConditional)
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): Boolean {
        if (enchantmentIds.isNotEmpty() && stack.enchantments.size() == 0) {
            return false
        } else {
            for (enchantmentId in enchantmentIds) {
                if (EnchantmentUtil.getEnchantmentLevel(clientLevel, stack, enchantmentId) == 0) {
                    return false
                }
            }

            return true
        }
    }

    override fun type(): MapCodec<out ConditionalItemModelProperty?> {
        return CODEC
    }
}
