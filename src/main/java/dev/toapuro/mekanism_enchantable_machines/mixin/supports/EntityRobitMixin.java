package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapabilityResolver;
import mekanism.common.capabilities.resolver.ICapabilityResolver;
import mekanism.common.entity.EntityRobit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityRobit.class, remap = false)
public abstract class EntityRobitMixin extends PathfinderMob {

    @Shadow
    protected abstract void addCapabilityResolver(ICapabilityResolver resolver);

    @Unique
    private final EnchantmentCapability mem$enchantments = new EnchantmentCapability();

    protected EntityRobitMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityType<?> type, Level world, CallbackInfo ci) {
        this.addCapabilityResolver(new EnchantmentCapabilityResolver(mem$enchantments));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"), remap = true)
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.save(tag);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"), remap = true)
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        mem$enchantments.load(tag);
    }

    @Inject(method = "getItemVariant", at = @At("TAIL"))
    public void getItemVariant(CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = cir.getReturnValue();
        for (EnchantmentCapability.EnchantmentEntry entry : mem$enchantments.getEnchantmentEntries()) {
            stack.enchant(entry.enchantment(), entry.level());
        }
    }
}
