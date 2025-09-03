package dev.toapuro.mekanism_enchantable_machines.event;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class EntityEnchantmentEventHandler {
    public static void onLivingHurt(LivingHurtEvent event) {
        int protection = 0;

        List<EnchantmentCapability.EnchantmentEntry> enchantments = EnchantmentTagUtil.getEnchantments(event.getEntity());
        if (enchantments.isEmpty()) {
            return;
        }

        for (EnchantmentCapability.EnchantmentEntry entry : enchantments) {
            protection += entry.enchantment().getDamageProtection(entry.level(), event.getSource());
        }

        if (protection > 0) {
            float amount = CombatRules.getDamageAfterMagicAbsorb(event.getAmount(), protection);
            event.setAmount(amount);
        }
    }
}
