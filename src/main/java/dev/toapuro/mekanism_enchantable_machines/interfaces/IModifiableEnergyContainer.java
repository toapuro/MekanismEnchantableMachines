package dev.toapuro.mekanism_enchantable_machines.interfaces;

import mekanism.api.math.FloatingLong;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public interface IModifiableEnergyContainer {
    FloatingLong mem$getOriginalMaxEnergy();

    FloatingLong mem$getModifiedMaxEnergy();

    void mem$setMaxEnergyModifier(@Nullable UnaryOperator<FloatingLong> energyModifier);
}
