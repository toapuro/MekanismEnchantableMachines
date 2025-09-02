package dev.toapuro.mekanism_enchantable_machines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.toapuro.mekanism_enchantable_machines.util.EnchantmentTagUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityRendererFoilWrapper<T extends BlockEntity> implements BlockEntityRenderer<T> {
    @Nullable
    public final BlockEntityRenderer<T> internal;
    private final BlockRenderDispatcher dispatcher;
    private final FoilRenderer foilRenderer;

    public TileEntityRendererFoilWrapper(BlockRenderDispatcher dispatcher, @Nullable BlockEntityRenderer<T> internal) {
        this.dispatcher = dispatcher;
        this.internal = internal;
        this.foilRenderer = new FoilRenderer();
    }

    @Override
    public void render(@NotNull T tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
        if (internal != null) {
            internal.render(tile, partialTick, poseStack, buffer, light, overlay);
        }

        if (EnchantmentTagUtil.isEnchanted(tile)) {
            foilRenderer.render(tile, dispatcher, partialTick, poseStack, buffer, light, overlay);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull T tile) {
        if (internal != null) {
            return internal.shouldRenderOffScreen(tile);
        }

        return false;
    }

    @Override
    public int getViewDistance() {
        if (internal != null) {
            return internal.getViewDistance();
        }

        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(@NotNull T tile, @NotNull Vec3 cameraPos) {
        if (EnchantmentTagUtil.isEnchanted(tile)) {
            return true;
        }

        if (internal != null) {
            return internal.shouldRender(tile, cameraPos);
        }

        return false;
    }
}
