package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {
    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isBookEnchantable(Lnet/minecraft/world/item/ItemStack;)Z"), remap = false, require = 0)
    public boolean isBookEnchantable(ItemStack stack, ItemStack book, Operation<Boolean> original) {
        return MEMSupportedEnchantments.ITEM_ENCHANTMENTS.hasSupports(stack.getItem()) ||
                original.call(stack, book);
    }
}
