package dev.toapuro.mekanism_enchantable_machines.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AutoRegisterCapability
public class EnchantmentCapability implements ITagSerializeable {

    public static final Capability<EnchantmentCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private static final String ENCHANTMENTS_TAG = "Enchantments";
    private List<EnchantmentEntry> enchantmentEntries = new ArrayList<>();

    public void save(CompoundTag tag) {
        ListTag enchantmentsTag = new ListTag();

        for (EnchantmentEntry entry : enchantmentEntries) {
            CompoundTag enchantTag = EnchantmentHelper.storeEnchantment(
                    EnchantmentHelper.getEnchantmentId(entry.enchantment),
                    entry.level
            );

            enchantmentsTag.add(enchantTag);
        }

        tag.put(ENCHANTMENTS_TAG, enchantmentsTag);
    }

    public void load(CompoundTag tag) {
        loadFromList(tag.getList(ENCHANTMENTS_TAG, Tag.TAG_COMPOUND));
    }

    public void loadFromList(ListTag listTag) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.deserializeEnchantments(listTag);

        List<EnchantmentEntry> deserialized = new ArrayList<>();
        enchantments.forEach((enchantment, level) ->
                deserialized.add(new EnchantmentEntry(enchantment, level))
        );

        this.enchantmentEntries = deserialized;
    }

    public boolean isEnchanted() {
        return !this.enchantmentEntries.isEmpty();
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return getEnchantmentLevel(enchantment) != 0;
    }

    public int getEnchantmentLevel(Enchantment enchantment) {
        for (EnchantmentEntry entry : enchantmentEntries) {
            if (entry.enchantment == enchantment) {
                return entry.level;
            }
        }

        return 0;
    }

    public List<EnchantmentEntry> getEnchantmentEntries() {
        return enchantmentEntries;
    }

    public record EnchantmentEntry(Enchantment enchantment, int level) {
    }
}
