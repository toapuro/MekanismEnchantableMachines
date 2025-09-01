package dev.toapuro.mekanism_enchantable_machines.compats;

import mekanism.api.math.FloatingLong;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IContainerCompat {
    default float applyMaxEnergyModifier(TileEntityMekanism tile) {
        return 1.0f;
    }

    default float applyMaxEnergyModifier(ItemStack stack) {
        return 1.0f;
    }

    default float applyCapacityModifier(IFluidTank tank, TileEntityMekanism tile) {
        return 1.0f;
    }

    default float applyCapacityModifier(ItemStack stack) {
        return 1.0f;
    }

    default FloatingLong modifyTransferRate(TileEntityMekanism tile, FloatingLong rate) {
        return rate;
    }
}
