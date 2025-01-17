package btw.lowercase.chit

import btw.lowercase.chit.property.conditional.HasEnchantmentConditional
import btw.lowercase.chit.property.conditional.HasEnchantmentsConditional
import btw.lowercase.chit.property.conditional.PredicateConditional
import btw.lowercase.chit.property.numeric.EnchantmentLevelNumeric
import btw.lowercase.chit.property.select.ComponentContents
import btw.lowercase.chit.property.conditional.CustomNameConditional
import btw.lowercase.chit.property.select.RaritySelect
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation

object ChitClientMod : ClientModInitializer {
    override fun onInitializeClient() {
        // Enchantments
        ConditionalItemModelProperties.ID_MAPPER.put(id("has_enchantment"), HasEnchantmentConditional.CODEC)
        ConditionalItemModelProperties.ID_MAPPER.put(id("has_enchantments"), HasEnchantmentsConditional.CODEC)
        RangeSelectItemModelProperties.ID_MAPPER.put(id("enchantment_level"), EnchantmentLevelNumeric.CODEC)

        // Components
        run {
            /**
             *
             * NOTE:
             * I do not own this part of this code, this code has been sourced from 25w03a and
             * belongs to Mojang/Minecraft!
             *
             * I will remove this if any issues arise.
             *
             */
            SelectItemModelProperties.ID_MAPPER.put(
                ResourceLocation.withDefaultNamespace("component"),
                ComponentContents.castType<DataComponents>()
            )
        }
        SelectItemModelProperties.ID_MAPPER.put(id("rarity"), RaritySelect.CODEC)
        ConditionalItemModelProperties.ID_MAPPER.put(id("custom_name"), CustomNameConditional.CODEC)

        // Other
        ConditionalItemModelProperties.ID_MAPPER.put(id("predicate"), PredicateConditional.CODEC)
    }

    fun id(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath("chit", path)
    }
}