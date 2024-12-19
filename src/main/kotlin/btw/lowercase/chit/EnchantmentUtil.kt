package btw.lowercase.chit

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.ItemEnchantments
import kotlin.jvm.optionals.getOrNull

class EnchantmentUtil {
    companion object {
        fun getEnchantmentLevel(clientLevel: ClientLevel?, stack: ItemStack, id: ResourceLocation): Int {
            if (clientLevel != null) {
                val enchantmentRegistry = clientLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                val enchantment = enchantmentRegistry.get(id).getOrNull() ?: return 0
                return if (stack.enchantments.isEmpty) {
                    stack.getOrDefault<ItemEnchantments>(
                        DataComponents.STORED_ENCHANTMENTS,
                        ItemEnchantments.EMPTY
                    )
                } else {
                    stack.enchantments
                }.getLevel(enchantment)
            } else {
                return 0
            }
        }
    }
}