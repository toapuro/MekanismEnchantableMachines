package dev.toapuro.mekanism_enchantable_machines.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class EnchantmentTagLoot extends LootModifier {

    public static final MapCodec<EnchantmentTagLoot> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, EnchantmentTagLoot::new)
            );


    protected EnchantmentTagLoot(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loots, LootContext lootContext) {
        BlockEntity blockEntity = lootContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (blockEntity == null) return loots;

        blockEntity.getCapability(EnchantmentCapability.CAPABILITY).ifPresent(capability -> {
            for (ItemStack loot : loots) {
                for (EnchantmentCapability.EnchantmentEntry entry : capability.getEnchantmentEntries()) {
                    loot.enchant(entry.enchantment(), entry.level());
                }
            }
        });

        return loots;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.codec();
    }
}
