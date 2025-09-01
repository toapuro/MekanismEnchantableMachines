package dev.toapuro.mekanism_enchantable_machines;

import com.mojang.logging.LogUtils;
import dev.toapuro.mekanism_enchantable_machines.compats.CoFHCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.IEnchantmentCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import dev.toapuro.mekanism_enchantable_machines.loot.MEMLootConditions;
import dev.toapuro.mekanism_enchantable_machines.loot.MEMLootModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MekanismEnchantableMachines.MODID)
public class MekanismEnchantableMachines {

    public static final String MODID = "mekanism_enchantable_machines";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public MekanismEnchantableMachines() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MEMConfig.initializeConfig();

        modEventBus.addListener(this::commonSetup);

        MEMLootModifiers.MODIFIER_SERIALIZERS.register(modEventBus);
        MEMLootConditions.CONDITION_TYPES.register(modEventBus);

        this.registerCompats();

        MEMCompats.COMPATS.initialize(modEventBus);
        MEMCompats.COMPATS.freezeCompats();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MEMConfig.COMMON);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        MEMCompats.COMPATS.runImplements(IEnchantmentCompat.class, IEnchantmentCompat::registerBlockSupports);
        MEMCompats.COMPATS.runImplements(IEnchantmentCompat.class, IEnchantmentCompat::registerEntitySupports);
    }

    public void registerCompats() {
        MEMCompats.COMPATS.registerCompat(new CoFHCompat());
    }
}
