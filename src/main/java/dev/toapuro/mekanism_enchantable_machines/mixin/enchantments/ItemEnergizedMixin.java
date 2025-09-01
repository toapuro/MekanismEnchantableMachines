package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.compats.IContainerCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.CapabilityItem;
import mekanism.common.item.ItemEnergized;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemEnergized.class, remap = false)
public class ItemEnergizedMixin extends CapabilityItem {

    @Unique
    private float mem$capacityMultiplier = 1.0f;

    protected ItemEnergizedMixin(Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "getMaxEnergy", at = @At("RETURN"))
    public FloatingLong getMaxEnergy(FloatingLong original, @Local(argsOnly = true) ItemStack stack) {
        return original.multiply(mem$capacityMultiplier);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        float modifier = 1.0f;
        for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
            modifier = compat.applyMaxEnergyModifier(stack);
        }
        mem$capacityMultiplier = modifier;
    }
}
