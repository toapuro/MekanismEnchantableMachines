package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.item.block.ItemBlockMekanism;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemBlockMekanism.class, remap = false)
public class ItemBlockMekanismMixin extends BlockItem {

    public ItemBlockMekanismMixin(BlockTile<?, ?> block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return MEMSupportedEnchantments.BLOCK_ENCHANTMENTS.isEnchantmentSupported(this.getBlock(), enchantment) ||
                MEMSupportedEnchantments.ITEM_ENCHANTMENTS.isEnchantmentSupported(this, enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return isEnchantable(stack) ? 1 : 0;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        if (MEMSupportedEnchantments.BLOCK_ENCHANTMENTS.hasSupports(this.getBlock()) ||
                MEMSupportedEnchantments.ITEM_ENCHANTMENTS.hasSupports(this)) {
            return true;
        }

        return super.isEnchantable(stack);
    }
}
