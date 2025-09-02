package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapabilityResolver;
import dev.toapuro.mekanism_enchantable_machines.compats.IContainerCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableEnergyContainer;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableFluidTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.CapabilityTileEntity;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = TileEntityMekanism.class, remap = false)
public abstract class TileEntityMekanismMixin extends CapabilityTileEntity {

    @Shadow
    @NotNull
    public abstract List<IExtendedFluidTank> getFluidTanks(@Nullable Direction side);

    @Shadow
    public abstract @NotNull List<IEnergyContainer> getEnergyContainers(@Nullable Direction side);

    @Unique
    private final EnchantmentCapability mem$enchantments = new EnchantmentCapability();

    public TileEntityMekanismMixin(TileEntityTypeRegistryObject<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(IBlockProvider blockProvider, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.addCapabilityResolver(new EnchantmentCapabilityResolver(mem$enchantments));
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void load(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.load(tag);
        mem$onUpdateEnchantments();
    }

    @Inject(method = "getReducedUpdateTag", at = @At("RETURN"))
    public void getReducedUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();
        mem$enchantments.save(tag);
    }

    @Inject(method = "handleUpdateTag", at = @At("RETURN"))
    public void handleUpdateTag(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.load(tag);
        mem$onUpdateEnchantments();
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.save(tag);
    }

    @Unique
    public void mem$onUpdateEnchantments() {
        for (IExtendedFluidTank fluidTank : this.getFluidTanks(null)) {
            if (!(fluidTank instanceof IModifiableFluidTank modifiableFluidTank)) {
                continue;
            }
            float capacityMultiplier = 1.0f;

            for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
                capacityMultiplier = compat.applyCapacityModifier(fluidTank, (TileEntityMekanism) (Object) this);
            }

            //noinspection ConstantValue
            if (capacityMultiplier != 1.0f) {
                float multiplier = capacityMultiplier;
                modifiableFluidTank.mem$setCapacityModifier(capacity -> (int) (multiplier * capacity));
            }
        }

        for (IEnergyContainer energyContainer : this.getEnergyContainers(null)) {
            if (!(energyContainer instanceof IModifiableEnergyContainer modifiableEnergyContainer)) {
                continue;
            }
            float maxEnergyMultiplier = 1.0f;

            for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
                maxEnergyMultiplier = compat.applyMaxEnergyModifier((TileEntityMekanism) (Object) this);
            }

            //noinspection ConstantValue
            if (maxEnergyMultiplier != 1.0f) {
                float multiplier = maxEnergyMultiplier;
                modifiableEnergyContainer.mem$setMaxEnergyModifier(maxEnergy -> maxEnergy.multiply(multiplier));
            }
        }

    }
}
