package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapabilityResolver;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.CapabilityTileEntity;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityMekanism.class, remap = false)
public abstract class TileEntityMekanismMixin extends CapabilityTileEntity {

    @Unique
    private final EnchantmentCapability mem$enchantments = new EnchantmentCapability();

    public TileEntityMekanismMixin(TileEntityTypeRegistryObject<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(IBlockProvider blockProvider, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.addCapabilityResolver(new EnchantmentCapabilityResolver(mem$enchantments));
    }

    @Inject(method = "load", at = @At("TAIL"), remap = true)
    public void load(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.load(tag);
    }

    @Inject(method = "getReducedUpdateTag", at = @At("RETURN"))
    public void getReducedUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();
        mem$enchantments.save(tag);
    }

    @Inject(method = "handleUpdateTag", at = @At("RETURN"))
    public void handleUpdateTag(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.load(tag);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"), remap = true)
    public void saveAdditional(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.save(tag);
    }
}
