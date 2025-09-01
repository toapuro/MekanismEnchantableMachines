package dev.toapuro.mekanism_enchantable_machines.capability;

import mekanism.common.capabilities.resolver.ICapabilityResolver;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantmentCapabilityResolver implements ICapabilityResolver {

    public LazyOptional<EnchantmentCapability> enchantCapability;

    public EnchantmentCapabilityResolver(EnchantmentCapability capability) {
        this.enchantCapability = LazyOptional.of(() -> capability);
    }

    @Override
    public @NotNull List<Capability<?>> getSupportedCapabilities() {
        return List.of(
                EnchantmentCapability.CAPABILITY
        );
    }

    @Override
    public <T> @NotNull LazyOptional<T> resolve(@NotNull Capability<T> capability, @Nullable Direction side) {
        return capability == EnchantmentCapability.CAPABILITY ?
                this.enchantCapability.cast() :
                LazyOptional.empty();
    }

    @Override
    public void invalidate(@NotNull Capability<?> capability, @Nullable Direction side) {
    }

    @Override
    public void invalidateAll() {
    }
}
