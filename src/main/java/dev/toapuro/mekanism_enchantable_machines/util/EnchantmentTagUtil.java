package dev.toapuro.mekanism_enchantable_machines.util;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class EnchantmentTagUtil {
    public static boolean isEnchanted(ICapabilityProvider provider) {
        var capability = provider.getCapability(EnchantmentCapability.CAPABILITY).resolve();
        return capability.isPresent() ?
                capability.get().isEnchanted() : false;
    }

    public static boolean hasEnchantment(ICapabilityProvider provider, Enchantment enchantment) {
        var capability = provider.getCapability(EnchantmentCapability.CAPABILITY).resolve();
        return capability.isPresent() ?
                capability.get().hasEnchantment(enchantment) : false;
    }

    public static int getEnchantmentLevel(ICapabilityProvider provider, Enchantment enchantment) {
        var capability = provider.getCapability(EnchantmentCapability.CAPABILITY).resolve();
        return capability.isPresent() ?
                capability.get().getEnchantmentLevel(enchantment) : 0;
    }

    public static List<EnchantmentCapability.EnchantmentEntry> getEnchantments(ICapabilityProvider provider) {
        var capability = provider.getCapability(EnchantmentCapability.CAPABILITY).resolve();
        return capability.isPresent() ?
                capability.get().getEnchantmentEntries() : List.of();
    }
}
