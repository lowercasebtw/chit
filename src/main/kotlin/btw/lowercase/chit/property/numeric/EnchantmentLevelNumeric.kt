package btw.lowercase.chit.property.numeric

import btw.lowercase.chit.EnchantmentUtil
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class EnchantmentLevelNumeric(val enchantmentId: ResourceLocation) : RangeSelectItemModelProperty {
    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("enchantment")
                    .forGetter(EnchantmentLevelNumeric::enchantmentId)
            ).apply(instance, ::EnchantmentLevelNumeric)
        }
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
    ): Float {
        return EnchantmentUtil.getEnchantmentLevel(clientLevel, stack, enchantmentId).toFloat()
    }

    override fun type(): MapCodec<out RangeSelectItemModelProperty?>? {
        return CODEC
    }
}