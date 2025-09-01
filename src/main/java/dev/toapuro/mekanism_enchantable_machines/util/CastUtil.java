package dev.toapuro.mekanism_enchantable_machines.util;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CastUtil {
    @SuppressWarnings("unchecked")
    public static <R> R unsafeCast(Object value) {
        return (R) value;
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> safeCast(Object value) {
        try {
            return Optional.of((T) value);
        } catch (ClassCastException exception) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T safeCastNullable(Object value) {
        try {
            return (T) value;
        } catch (ClassCastException exception) {
            return null;
        }
    }
}
