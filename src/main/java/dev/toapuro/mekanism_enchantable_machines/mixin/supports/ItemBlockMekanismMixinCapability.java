package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.compats.IContainerCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableEnergyContainer;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableFluidTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.energy.IMekanismStrictEnergyHandler;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.fluid.IMekanismFluidHandler;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.item.block.ItemBlockMekanism;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ItemBlockMekanism.class, remap = false)
public class ItemBlockMekanismMixinCapability<BLOCK extends Block> extends BlockItem {

    @Unique
    public List<IModifiableFluidTank> mem$fluidTanks = new ArrayList<>();
    @Unique
    public List<IModifiableEnergyContainer> mem$energyContainers = new ArrayList<>();

    public ItemBlockMekanismMixinCapability(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Inject(method = "initCapabilities", at = @At(value = "INVOKE", target = "Lmekanism/common/item/block/ItemBlockMekanism;gatherCapabilities(Ljava/util/List;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER))
    @SuppressWarnings("ConstantValue")
    public void afterCapabilities(ItemStack stack, CompoundTag nbt, CallbackInfoReturnable<ICapabilityProvider> cir,
                                  @Local List<ItemCapabilityWrapper.ItemCapability> capabilities) {
        for (ItemCapabilityWrapper.ItemCapability capability : capabilities) {
            if (capability instanceof IMekanismStrictEnergyHandler strict) {
                List<IEnergyContainer> energyContainers = strict.getEnergyContainers(null);
                if (energyContainers != null) {
                    mem$energyContainers.addAll(energyContainers.stream()
                            .filter(iEnergyContainer -> iEnergyContainer instanceof IModifiableEnergyContainer)
                            .map(energyContainer -> (IModifiableEnergyContainer) energyContainer)
                            .toList());
                }
            }
            if (capability instanceof IMekanismFluidHandler fluidHandler) {
                List<IExtendedFluidTank> fluidTanks = fluidHandler.getFluidTanks(null);
                if (fluidTanks != null) {
                    mem$fluidTanks.addAll(fluidTanks.stream()
                            .filter(fluidTank -> fluidTank instanceof IModifiableFluidTank)
                            .map(fluidTank -> (IModifiableFluidTank) fluidTank)
                            .toList());
                }
            }
        }
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);

        float capacityModifier = 1.0f;
        for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
            capacityModifier *= compat.applyCapacityModifier(stack);
        }

        for (IModifiableFluidTank fluidTank : mem$fluidTanks) {
            float modifier = capacityModifier;
            fluidTank.mem$setCapacityModifier(capacity -> (int) (capacity * modifier));
        }


        float maxEnergyModifier = 1.0f;
        for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
            maxEnergyModifier *= compat.applyMaxEnergyModifier(stack);
        }

        for (IModifiableEnergyContainer energyContainer : mem$energyContainers) {
            float modifier = maxEnergyModifier;
            energyContainer.mem$setMaxEnergyModifier(maxEnergy -> maxEnergy.multiply(modifier));
        }
    }
}
