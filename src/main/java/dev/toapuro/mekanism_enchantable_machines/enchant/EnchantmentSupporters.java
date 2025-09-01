package dev.toapuro.mekanism_enchantable_machines.enchant;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;

public class EnchantmentSupporters<T> {

    private final ListMultimap<T, IEnchantmentSupporter> enchantmentSupporters = ArrayListMultimap.create();

    public void registerSupporter(T entry, IEnchantmentSupporter supporter) {
        this.enchantmentSupporters.put(entry, supporter);
    }

    public void registerSupports(T entry, Enchantment... supportEnchantments) {
        List<Enchantment> supported = Arrays.asList(supportEnchantments);
        registerSupporter(entry, supported::contains);
    }

    public void registerSupports(T entry, EnchantmentCategory... supportCategories) {
        List<EnchantmentCategory> supported = Arrays.asList(supportCategories);
        registerSupporter(entry, enchantment -> supported.stream()
                .anyMatch(enchantmentCategory -> enchantment.category == enchantmentCategory)
        );
    }

    public boolean hasSupports(T entry) {
        return enchantmentSupporters.containsKey(entry);
    }

    public boolean supportEnchantment(T entry, Enchantment enchantment) {
        List<IEnchantmentSupporter> resolvers = enchantmentSupporters.get(entry);
        for (IEnchantmentSupporter resolver : resolvers) {
            if (resolver.supportEnchantment(enchantment)) {
                return true;
            }
        }

        return false;
    }
}
