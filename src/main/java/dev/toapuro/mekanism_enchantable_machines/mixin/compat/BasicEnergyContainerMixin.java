package dev.toapuro.mekanism_enchantable_machines.mixin.compat;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Mixin(value = BasicEnergyContainer.class, remap = false)
public abstract class BasicEnergyContainerMixin implements IModifiableEnergyContainer {
    @Shadow
    @Final
    private FloatingLong maxEnergy;
    @Unique
    private UnaryOperator<FloatingLong> mem$maxEnergyModifier = null;

    @Shadow
    public abstract FloatingLong getMaxEnergy();

    @ModifyReturnValue(method = "getMaxEnergy", at = @At("RETURN"))
    public FloatingLong modifyMaxEnergy(FloatingLong original) {
        if (mem$maxEnergyModifier != null) {
            return mem$maxEnergyModifier.apply(original);
        }
        return original;
    }

    @Override
    public FloatingLong mem$getOriginalMaxEnergy() {
        return this.maxEnergy;
    }

    @Override
    public FloatingLong mem$getModifiedMaxEnergy() {
        return this.getMaxEnergy();
    }

    @Override
    public void mem$setMaxEnergyModifier(@Nullable UnaryOperator<FloatingLong> energyModifier) {
        this.mem$maxEnergyModifier = energyModifier;
    }
}
