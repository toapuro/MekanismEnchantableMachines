package dev.toapuro.mekanism_enchantable_machines.mixin.supports;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import mekanism.api.Upgrade;
import mekanism.common.tile.machine.TileEntityDigitalMiner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityDigitalMiner.class, remap = false)
public class TileEntityDigitalMinerMixin {
    @Shadow
    private int delayLength;

    @Inject(method = "recalculateUpgrades", at = @At("TAIL"))
    public void recalculateUpgrades(Upgrade upgrade, CallbackInfo ci) {
        int enchantmentLevel = EnchantmentTagUtil.getEnchantmentLevel((BlockEntity) (Object) this, Enchantments.BLOCK_EFFICIENCY);
        delayLength = (int) (delayLength * Math.exp(-enchantmentLevel / 4.0f));
    }

    @ModifyExpressionValue(method = "getDrops", at = @At(value = "INVOKE", target = "Lmekanism/common/item/gear/ItemAtomicDisassembler;fullyChargedStack()Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack applyFortune(ItemStack stack) {
        BlockEntity tile = (BlockEntity) (Object) this;

        for (EnchantmentCapability.EnchantmentEntry entry : EnchantmentTagUtil.getEnchantments(tile)) {
            stack.enchant(entry.enchantment(), entry.level());
        }

        return stack;
    }
}
