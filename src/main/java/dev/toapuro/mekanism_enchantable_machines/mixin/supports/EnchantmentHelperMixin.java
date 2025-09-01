package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyReturnValue(method = "getEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)I", at = @At("RETURN"))
    private static int modifyEnchantmentLevel(int original, @Local(argsOnly = true) Enchantment enchantment, @Local(argsOnly = true) LivingEntity livingEntity) {
        int enchantmentLevel = EnchantmentTagUtil.getEnchantmentLevel(livingEntity, enchantment);
        return Math.max(original, enchantmentLevel);
    }
}
