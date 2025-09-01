package dev.toapuro.mekanism_enchantable_machines.compats.system;

import dev.toapuro.mekanism_enchantable_machines.compats.ICompat;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CompatRegistry {
    private final List<ICompat> compats;
    private List<ICompat> frozenCompats = null;

    private List<ICompat> loadedCompats = null;

    public CompatRegistry() {
        this.compats = new ArrayList<>();
    }

    public void initialize(IEventBus modBus) {
        this.loadedCompats = this.loadCompats();

        for (ICompat loadedCompat : loadedCompats) {
            loadedCompat.initializeCompat(modBus);
        }
    }

    public void freezeCompats() {
        if (loadedCompats == null) {
            return;
        }

        frozenCompats = Collections.unmodifiableList(loadedCompats);
    }

    public <T> void runImplements(Class<T> type, Consumer<T> consumer) {
        if (frozenCompats == null) return;

        frozenCompats.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .forEach(consumer);
    }

    public @NotNull @Unmodifiable <T> List<T> getImplements(Class<T> type) {
        if (frozenCompats == null) return List.of();

        return frozenCompats.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    private List<ICompat> loadCompats() {
        List<ICompat> loadedCompats = new ArrayList<>();
        for (ICompat compat : compats) {
            if (this.isCompatValid(compat)) {
                loadedCompats.add(compat);
            }
        }

        return loadedCompats;
    }

    public boolean isCompatValid(ICompat compat) {
        return ModList.get().isLoaded(compat.getCompatModid()) && compat.isValid();
    }

    public <T extends ICompat> void registerCompat(T compat) {
        this.compats.add(compat);
    }
}
