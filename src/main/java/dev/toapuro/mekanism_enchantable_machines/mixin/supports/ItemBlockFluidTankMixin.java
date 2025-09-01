package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.toapuro.mekanism_enchantable_machines.compats.IContainerCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import dev.toapuro.mekanism_enchantable_machines.interfaces.IModifiableFluidTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.capabilities.fluid.item.RateLimitFluidHandler;
import mekanism.common.item.block.machine.ItemBlockFluidTank;
import mekanism.common.item.block.machine.ItemBlockMachine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ItemBlockFluidTank.class, remap = false)
public class ItemBlockFluidTankMixin extends ItemBlockMachine {
    @Unique
    public List<IExtendedFluidTank> mem$fluidTanks = new ArrayList<>();

    public ItemBlockFluidTankMixin(BlockTile<?, ?> block) {
        super(block);
    }

    @ModifyExpressionValue(method = "gatherCapabilities", at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/fluid/item/RateLimitFluidHandler;create(Lmekanism/common/tier/FluidTankTier;)Lmekanism/common/capabilities/fluid/item/RateLimitFluidHandler;"))
    public RateLimitFluidHandler modifyFluidHandler(RateLimitFluidHandler original) {
        mem$fluidTanks.addAll(original.getFluidTanks(null));
        return original;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);

        float capacityModifier = 1.0f;
        for (IContainerCompat compat : MEMCompats.COMPATS.getImplements(IContainerCompat.class)) {
            capacityModifier *= compat.applyCapacityModifier(stack);
        }

        for (IExtendedFluidTank fluidTank : mem$fluidTanks) {
            if (fluidTank instanceof IModifiableFluidTank modifiableFluidTank) {
                float modifier = capacityModifier;
                modifiableFluidTank.mem$setCapacityModifier(capacity -> (int) (capacity * modifier));
            }
        }
    }
}
