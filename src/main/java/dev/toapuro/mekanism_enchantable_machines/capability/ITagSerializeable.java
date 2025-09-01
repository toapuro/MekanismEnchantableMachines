package dev.toapuro.mekanism_enchantable_machines.capability;

import net.minecraft.nbt.CompoundTag;

public interface ITagSerializeable {
    void load(CompoundTag tag);

    void save(CompoundTag tag);
}
