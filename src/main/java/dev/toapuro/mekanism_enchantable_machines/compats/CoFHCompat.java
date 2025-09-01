package dev.toapuro.mekanism_enchantable_machines.compats;

import cofh.core.init.CoreEnchantments;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeEnergy;
import mekanism.common.block.attribute.Attributes.AttributeInventory;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;

public class CoFHCompat implements ICompat, IContainerCompat, IEnchantmentCompat {

    @Override
    public void initializeCompat(IEventBus modBus) {

    }

    @Override
    public void registerBlockSupports() {
        List<Block> containers = new ArrayList<>();

        for (IBlockProvider block : MekanismBlocks.BLOCKS.getAllBlocks()) {
            AttributeEnergy attributeEnergy = Attribute.get(block, AttributeEnergy.class);
            AttributeInventory<?> attributeInventory = Attribute.get(block, AttributeInventory.class);

            if (attributeEnergy == null && attributeInventory == null) {
                continue;
            }

            containers.add(block.getBlock());
        }

        for (Block container : containers) {
            MEMSupportedEnchantments.BLOCK_ENCHANTMENTS.supportEnchantment(
                    container,
                    CoreEnchantments.HOLDING.get()
            );
        }
    }

    @Override
    public void registerEntitySupports() {

    }

    public float getModifier(ICapabilityProvider tile) {
        int enchantmentLevel = EnchantmentTagUtil.getEnchantmentLevel(tile, CoreEnchantments.HOLDING.get());
        return 1.0F + enchantmentLevel / 2F;
    }

    @Override
    public float applyMaxEnergyModifier(TileEntityMekanism tile) {
        return getModifier(tile);
    }

    @Override
    public float applyCapacityModifier(IFluidTank tank, TileEntityMekanism tile) {
        return getModifier(tile);
    }

    @Override
    public float applyCapacityModifier(ItemStack stack) {
        return getModifier(stack);
    }

    @Override
    public float applyMaxEnergyModifier(ItemStack stack) {
        return getModifier(stack);
    }

    @Override
    public FloatingLong modifyTransferRate(TileEntityMekanism tile, FloatingLong rate) {
        return rate.multiply(this.getModifier(tile));
    }

    @Override
    public String getCompatModid() {
        return "cofh_core";
    }
}
