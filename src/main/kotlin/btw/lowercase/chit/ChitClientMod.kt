package btw.lowercase.chit

import btw.lowercase.chit.property.conditional.HasEnchantmentConditional
import btw.lowercase.chit.property.numeric.EnchantmentLevelNumeric
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties
import net.minecraft.resources.ResourceLocation

object ChitClientMod : ClientModInitializer {
    override fun onInitializeClient() {
        // Enchantments
        ConditionalItemModelProperties.ID_MAPPER.put(id("has_enchantment"), HasEnchantmentConditional.CODEC)
        RangeSelectItemModelProperties.ID_MAPPER.put(id("enchantment_level"), EnchantmentLevelNumeric.CODEC)
    }

    fun id(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath("chit", path)
    }
}