package dev.toapuro.mekanism_enchantable_machines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;

public class FoilRenderer {
    public FoilRenderer() {
    }

    public void render(BlockEntity tile, BlockRenderDispatcher dispatcher, float partialTick, PoseStack matrix, MultiBufferSource buffer) {
        Level level = tile.getLevel();
        if (level == null) return;

        RenderType renderType = MEMRenderTypes.MACHINE_GLINT;

        var consumer = new SheetedDecalTextureGenerator(
                buffer.getBuffer(renderType),
                matrix.last().pose(),
                matrix.last().normal(),
                1.0f
        );
        matrix.pushPose();

        dispatcher.renderBatched(
                tile.getBlockState(),
                tile.getBlockPos(),
                level,
                matrix,
                consumer,
                true,
                level.random,
                ModelData.EMPTY,
                renderType
        );

        matrix.popPose();
    }
}
