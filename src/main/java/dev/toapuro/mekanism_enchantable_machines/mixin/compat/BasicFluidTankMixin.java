package dev.toapuro.mekanism_enchantable_machines.mixin.compat;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableFluidTank;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.IntUnaryOperator;

@Mixin(value = BasicFluidTank.class, remap = false)
public abstract class BasicFluidTankMixin implements IModifiableFluidTank {
    @Shadow
    @Final
    private int capacity;
    @Unique
    private IntUnaryOperator mem$capacityModifier = null;

    @Shadow
    public abstract int getCapacity();

    @ModifyReturnValue(method = "getCapacity", at = @At("RETURN"))
    public int modifyCapacity(int original) {
        if (mem$capacityModifier != null) {
            return mem$capacityModifier.applyAsInt(original);
        }
        return original;
    }

    @Override
    public int mem$getOriginalCapacity() {
        return this.capacity;
    }

    @Override
    public int mem$getModifiedCapacity() {
        return this.getCapacity();
    }

    @Override
    public void mem$setCapacityModifier(@Nullable IntUnaryOperator capacityModifier) {
        this.mem$capacityModifier = capacityModifier;
    }
}
