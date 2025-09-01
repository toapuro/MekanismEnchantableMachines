package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.item.block.ItemBlockMekanism;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemBlockMekanism.class, remap = false)
public class ItemBlockMekanismMixin<BLOCK extends Block> extends BlockItem {

    @Shadow
    @Final
    private @NotNull BLOCK block;

    public ItemBlockMekanismMixin(BlockTile<?, ?> block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return MEMSupportedEnchantments.BLOCK_ENCHANTMENTS.supportEnchantment(this.getBlock(), enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return isEnchantable(stack) ? 1 : 0;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        if (block instanceof BlockTile) {
            return MEMSupportedEnchantments.BLOCK_ENCHANTMENTS.hasSupports(this.getBlock());
        }
        return super.isEnchantable(stack);
    }
}
