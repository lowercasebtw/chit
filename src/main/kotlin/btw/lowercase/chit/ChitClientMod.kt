package btw.lowercase.chit

import btw.lowercase.chit.property.conditional.HasEnchantmentConditional
import btw.lowercase.chit.property.numeric.EnchantmentLevelNumeric
import btw.lowercase.chit.property.select.RaritySelect
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties
import net.minecraft.resources.ResourceLocation

object ChitClientMod : ClientModInitializer {
    override fun onInitializeClient() {
        // Enchantments
        ConditionalItemModelProperties.ID_MAPPER.put(id("has_enchantment"), HasEnchantmentConditional.CODEC)
        RangeSelectItemModelProperties.ID_MAPPER.put(id("enchantment_level"), EnchantmentLevelNumeric.CODEC)

        // Components
        SelectItemModelProperties.ID_MAPPER.put(id("rarity"), RaritySelect.CODEC)

        // Custom Data

        // Other
    }

    fun id(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath("chit", path)
    }
}