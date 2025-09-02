package dev.toapuro.mekanism_enchantable_machines.compats;

import cofh.core.init.CoreEnchantments;
import dev.toapuro.mekanism_enchantable_machines.compats.handlers.IContainerHandler;
import dev.toapuro.mekanism_enchantable_machines.compats.handlers.IEnchantmentHandler;
import dev.toapuro.mekanism_enchantable_machines.enchant.MEMSupportedEnchantments;
import mekanism.api.providers.IItemProvider;
import mekanism.common.item.ItemEnergized;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registries.MekanismItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;

public class CoFHCompat implements ICompat, IContainerHandler, IEnchantmentHandler {

    @Override
    public void initializeCompat(IEventBus modBus) {

    }

    @Override
    public void registerItemSupports() {
        for (IItemProvider itemLike : MekanismItems.ITEMS.getAllItems()) {
            if (itemLike instanceof ItemRegistryObject<?> itemRegistryObject) {
                Item item = itemRegistryObject.asItem();
                if (item instanceof ItemEnergized) {
                    MEMSupportedEnchantments.ITEM_ENCHANTMENTS.registerSupports(item, CoreEnchantments.HOLDING.get());
                }
            }
        }
    }

    public float getItemModifier(ItemStack stack) {
        int enchantmentLevel = stack.getEnchantmentLevel(CoreEnchantments.HOLDING.get());
        return 1.0F + enchantmentLevel / 2F;
    }

    @Override
    public float applyMaxEnergyModifier(ItemStack stack) {
        return getItemModifier(stack);
    }

    @Override
    public String getCompatModid() {
        return "cofh_core";
    }
}
