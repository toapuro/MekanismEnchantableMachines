package dev.toapuro.mekanism_enchantable_machines.loot;

import com.mojang.serialization.Codec;
import dev.toapuro.mekanism_enchantable_machines.MekanismEnchantableMachines;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEMLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> MODIFIER_SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,
            MekanismEnchantableMachines.MODID
    );

    public static final RegistryObject<? extends Codec<? extends IGlobalLootModifier>> ENCHANTMENT_LOOT_MODIFIER = MODIFIER_SERIALIZERS.register(
            "enchantment_loot",
            EnchantmentTagLoot.CODEC::codec
    );
}
