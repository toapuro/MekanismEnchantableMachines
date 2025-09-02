package dev.toapuro.mekanism_enchantable_machines.compats.handlers;

import net.minecraft.world.item.ItemStack;

public interface IContainerHandler {
    default float applyMaxEnergyModifier(ItemStack stack) {
        return 1.0f;
    }
}
