package dev.toapuro.mekanism_enchantable_machines.mixin.enchantments;

import dev.toapuro.mekanism_enchantable_machines.capability.EnchantmentCapability;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import mekanism.common.block.prefab.BlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockTile.class, remap = false)
public class BlockTileMixin extends Block {

    public BlockTileMixin(Properties prop) {
        super(prop);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!stack.hasTag() || !stack.isEnchanted()) return;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;

        blockEntity.getCapability(EnchantmentCapability.CAPABILITY).ifPresent(capability -> {
            ListTag enchantmentTags = stack.getEnchantmentTags();
            capability.loadFromList(enchantmentTags);
        });
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {

        float resistance = super.getExplosionResistance(state, level, pos, explosion);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return resistance;

        if (EnchantmentTagUtil.hasEnchantment(blockEntity, Enchantments.BLAST_PROTECTION)) {
            int enchantmentLevel = EnchantmentTagUtil.getEnchantmentLevel(blockEntity, Enchantments.BLAST_PROTECTION);

            return resistance + enchantmentLevel * 2.0f;
        }

        return resistance;
    }
}
