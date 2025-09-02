package dev.toapuro.mekanism_enchantable_machines.enchant;

import mekanism.api.providers.IBlockProvider;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

public class MEMSupportedEnchantments {
    public static final EnchantmentSupporters<Block> BLOCK_ENCHANTMENTS = new EnchantmentSupporters<>();
    public static final EnchantmentSupporters<EntityType<?>> ENTITY_ENCHANTMENTS = new EnchantmentSupporters<>();

    public static void initBlocks() {
        for (IBlockProvider blockProvider : MekanismBlocks.BLOCKS.getAllBlocks()) {
            BLOCK_ENCHANTMENTS.registerSupports(blockProvider.getBlock(), Enchantments.BLAST_PROTECTION);
        }

        BLOCK_ENCHANTMENTS.registerSupports(MekanismBlocks.DIGITAL_MINER.getPrimary(), EnchantmentCategory.DIGGER);
    }

    public static void initEntities() {
        ENTITY_ENCHANTMENTS.registerSupports(MekanismEntityTypes.ROBIT.get(),
                EnchantmentCategory.ARMOR,
                EnchantmentCategory.ARMOR_HEAD,
                EnchantmentCategory.ARMOR_CHEST,
                EnchantmentCategory.ARMOR_LEGS,
                EnchantmentCategory.ARMOR_FEET
        );
    }

    static {
        initBlocks();
        initEntities();
    }
}
