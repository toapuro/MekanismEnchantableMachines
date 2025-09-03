package dev.toapuro.mekanism_enchantable_machines;

import dev.toapuro.mekanism_enchantable_machines.compats.CoFHCompat;
import dev.toapuro.mekanism_enchantable_machines.compats.handlers.IEnchantmentHandler;
import dev.toapuro.mekanism_enchantable_machines.compats.system.MEMCompats;
import dev.toapuro.mekanism_enchantable_machines.event.EntityEnchantmentEventHandler;
import dev.toapuro.mekanism_enchantable_machines.loot.MEMLootConditions;
import dev.toapuro.mekanism_enchantable_machines.loot.MEMLootModifiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MekanismEnchantableMachines.MODID)
public class MekanismEnchantableMachines {

    public static final String MODID = "mekanism_enchantable_machines";

    @SuppressWarnings("removal")
    public MekanismEnchantableMachines() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MEMConfig.initializeConfig();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.addListener(EntityEnchantmentEventHandler::onLivingHurt);

        MEMLootModifiers.MODIFIER_SERIALIZERS.register(modEventBus);
        MEMLootConditions.CONDITION_TYPES.register(modEventBus);

        this.registerCompats();

        MEMCompats.COMPATS.initialize(modEventBus);
        MEMCompats.COMPATS.freezeCompats();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MEMConfig.COMMON);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        MEMCompats.COMPATS.runImplements(IEnchantmentHandler.class, IEnchantmentHandler::registerItemSupports);
        MEMCompats.COMPATS.runImplements(IEnchantmentHandler.class, IEnchantmentHandler::registerBlockSupports);
        MEMCompats.COMPATS.runImplements(IEnchantmentHandler.class, IEnchantmentHandler::registerEntitySupports);
    }

    public void registerCompats() {
        MEMCompats.COMPATS.registerCompat(new CoFHCompat());
    }
}
