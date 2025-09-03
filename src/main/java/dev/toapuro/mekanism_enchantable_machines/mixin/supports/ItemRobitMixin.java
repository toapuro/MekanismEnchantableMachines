package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import mekanism.api.math.FloatingLongSupplier;
import mekanism.common.entity.EntityRobit;
import mekanism.common.item.ItemEnergized;
import mekanism.common.item.ItemRobit;
import mekanism.common.registries.MekanismEntityTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemRobit.class, remap = false)
public class ItemRobitMixin extends ItemEnergized {

    public ItemRobitMixin(FloatingLongSupplier chargeRateSupplier, FloatingLongSupplier maxEnergySupplier, Properties properties) {
        super(chargeRateSupplier, maxEnergySupplier, properties);
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), remap = true)
    public void attachEnchantment(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir,
                                  @Local ItemStack stack,
                                  @Local EntityRobit robit) {
        robit.getCapability(EnchantmentCapability.CAPABILITY).ifPresent(capability -> {
            ListTag enchantmentTags = stack.getEnchantmentTags();
            capability.loadFromList(enchantmentTags);
        });
    }

    @Unique
    public EntityType<?> mem$getEntityType() {
        return MekanismEntityTypes.ROBIT.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return MEMSupportedEnchantments.ENTITY_ENCHANTMENTS.isEnchantmentSupported(mem$getEntityType(), enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return isEnchantable(stack) ? 1 : 0;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return MEMSupportedEnchantments.ENTITY_ENCHANTMENTS.hasSupports(mem$getEntityType());
    }
}
