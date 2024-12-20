package btw.lowercase.chit.property.select

import com.mojang.serialization.MapCodec
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty.Type
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class RaritySelect() : SelectItemModelProperty<Rarity> {
    companion object {
        val CODEC = Type.create(MapCodec.unit(RaritySelect()), Rarity.CODEC)
    }

    override fun get(
        stack: ItemStack,
        clientLevel: ClientLevel?,
        livingEntity: LivingEntity?,
        layer: Int,
        itemDisplayContext: ItemDisplayContext,
    ): Rarity? {
        return stack.rarity
    }

    override fun type(): Type<RaritySelect, Rarity> {
        return CODEC
    }
}