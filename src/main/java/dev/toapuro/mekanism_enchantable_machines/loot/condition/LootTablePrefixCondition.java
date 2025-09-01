package dev.toapuro.mekanism_enchantable_machines.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import dev.toapuro.mekanism_enchantable_machines.loot.MEMLootConditions;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record LootTablePrefixCondition(String location) implements LootItemCondition {
    @Override
    public @NotNull LootItemConditionType getType() {
        return MEMLootConditions.LOOT_TABLE_PREFIX.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getQueriedLootTableId().toString().startsWith(location);
    }


    public static class ConditionSerializer implements Serializer<LootTablePrefixCondition> {
        public ConditionSerializer() {
        }

        public void serialize(@NotNull JsonObject json, @NotNull LootTablePrefixCondition instance, @NotNull JsonSerializationContext ctx) {
            json.addProperty("loot_table_prefix", instance.location);
        }

        public @NotNull LootTablePrefixCondition deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext ctx) {
            return new LootTablePrefixCondition(GsonHelper.getAsString(object, "loot_table_prefix"));
        }
    }
}
