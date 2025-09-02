package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.compats.handlers.IContainerHandler;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.CapabilityItem;
import mekanism.common.item.ItemEnergized;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemEnergized.class, remap = false)
public class ItemEnergizedMixin extends CapabilityItem {

    protected ItemEnergizedMixin(Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "getMaxEnergy", at = @At("RETURN"))
    public FloatingLong getMaxEnergy(FloatingLong original, @Local(argsOnly = true) ItemStack stack) {
        float modifier = 1.0f;
        for (IContainerHandler compat : MEMCompats.COMPATS.getImplements(IContainerHandler.class)) {
            modifier = compat.applyMaxEnergyModifier(stack);
        }

        return original.multiply(modifier);
    }
}
