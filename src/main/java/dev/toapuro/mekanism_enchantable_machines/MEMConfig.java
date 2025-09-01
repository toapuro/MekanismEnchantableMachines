package dev.toapuro.mekanism_enchantable_machines;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MekanismEnchantableMachines.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MEMConfig {
    public static ForgeConfigSpec COMMON;

    static void initializeConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        COMMON = builder.build();
    }
}
