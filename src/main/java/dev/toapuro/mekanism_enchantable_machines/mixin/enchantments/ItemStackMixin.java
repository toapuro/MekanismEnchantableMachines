package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    public boolean isEnchantable(boolean original) {
        return original ||
                MEMSupportedEnchantments.ITEM_ENCHANTMENTS.hasSupports(getItem());
    }
}
