package dev.toapuro.mekanism_enchantable_machines.loot;

import dev.toapuro.mekanism_enchantable_machines.MekanismEnchantableMachines;
import dev.toapuro.mekanism_enchantable_machines.loot.condition.LootTablePrefixCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MEMLootConditions {

    public static final DeferredRegister<LootItemConditionType> CONDITION_TYPES = DeferredRegister.create(
            Registries.LOOT_CONDITION_TYPE,
            MekanismEnchantableMachines.MODID
    );

    public static final RegistryObject<LootItemConditionType> LOOT_TABLE_PREFIX = CONDITION_TYPES.register(
            "loot_table_prefix",
            () -> new LootItemConditionType(new LootTablePrefixCondition.ConditionSerializer())
    );
}
