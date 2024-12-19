package btw.lowercase.chit.property.conditional

import btw.lowercase.chit.EnchantmentUtil
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.ExtraCodecs
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

class HasEnchantmentConditional(val enchantmentId: ResourceLocation, val enchantmentLevel: Int) :
    ConditionalItemModelProperty {
    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("enchantment")
                    .forGetter(HasEnchantmentConditional::enchantmentId),
                ExtraCodecs.POSITIVE_INT.optionalFieldOf("level", 0)
                    .forGetter(HasEnchantmentConditional::enchantmentLevel)
            ).apply(instance, ::HasEnchantmentConditional)
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): Boolean {
        val level = EnchantmentUtil.getEnchantmentLevel(clientLevel, stack, enchantmentId)
        return level != 0 && !(enchantmentLevel != 0 && enchantmentLevel != level)
    }

    override fun type(): MapCodec<out ConditionalItemModelProperty?> {
        return CODEC
    }
}
