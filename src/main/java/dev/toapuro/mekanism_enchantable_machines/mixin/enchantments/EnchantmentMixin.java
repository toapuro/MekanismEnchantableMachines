package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Enchantment.class, remap = false)
public abstract class EnchantmentMixin {

    @ModifyReturnValue(method = "canApplyAtEnchantingTable", at = @At("RETURN"))
    public boolean canApplyAtEnchantingTable(boolean original, @Local(argsOnly = true) ItemStack stack) {
        if (!original) {
            return MEMSupportedEnchantments.ITEM_ENCHANTMENTS.isEnchantmentSupported(stack.getItem(), (Enchantment) (Object) this);
        }
        return true;
    }
}
