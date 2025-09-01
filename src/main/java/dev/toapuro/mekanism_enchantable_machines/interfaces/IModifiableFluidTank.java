package dev.toapuro.mekanism_enchantable_machines.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.function.IntUnaryOperator;

public interface IModifiableFluidTank {
    int mem$getOriginalCapacity();

    int mem$getModifiedCapacity();

    void mem$setCapacityModifier(@Nullable IntUnaryOperator capacityModifier);
}
