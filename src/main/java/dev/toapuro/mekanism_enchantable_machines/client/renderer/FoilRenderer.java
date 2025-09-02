package dev.toapuro.mekanism_enchantable_machines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class FoilRenderer {
    public FoilRenderer() {
    }

    public void render(BlockEntity tile, BlockRenderDispatcher dispatcher, float partialTick, PoseStack matrix, MultiBufferSource buffer, int light, int overlay) {
        Level level = tile.getLevel();
        if (level == null) return;

        RenderType renderType = MEMRenderTypes.MACHINE_GLINT;
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        var consumer = new SheetedDecalTextureGenerator(
                buffer.getBuffer(renderType),
                matrix.last().pose(),
                matrix.last().normal(),
                1.0f
        );


        matrix.pushPose();

        renderBlock(
                dispatcher,
                matrix,
                consumer,
                tile.getBlockState(),
                level,
                tile.getBlockPos(),
                blockColors,
                ModelData.EMPTY,
                light,
                overlay
        );

        matrix.popPose();
    }

    public void renderBlock(BlockRenderDispatcher dispatcher,
                            PoseStack poseStack,
                            VertexConsumer consumer,
                            BlockState blockState,
                            Level level,
                            BlockPos blockPos,
                            BlockColors blockColors,
                            ModelData modelData,
                            int light, int overlay) {
        BakedModel bakedmodel = dispatcher.getBlockModel(blockState);
        ModelBlockRenderer modelRenderer = dispatcher.getModelRenderer();

        int i = blockColors.getColor(blockState, level, blockPos, 0);
        float r = (float) (i >> 16 & 255) / 255.0F;
        float g = (float) (i >> 8 & 255) / 255.0F;
        float b = (float) (i & 255) / 255.0F;

        for (RenderType rt : bakedmodel.getRenderTypes(blockState, RandomSource.create(42L), modelData)) {
            modelRenderer.renderModel(
                    poseStack.last(),
                    consumer,
                    blockState,
                    bakedmodel,
                    r, g, b,
                    light,
                    overlay,
                    modelData,
                    rt
            );
        }
    }
}
