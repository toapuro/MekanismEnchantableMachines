package dev.toapuro.mekanism_enchantable_machines.compats;

import net.minecraftforge.eventbus.api.IEventBus;

public interface ICompat {
    void initializeCompat(IEventBus modBus);

    String getCompatModid();

    default boolean isValid() {
        return true;
    }
}
