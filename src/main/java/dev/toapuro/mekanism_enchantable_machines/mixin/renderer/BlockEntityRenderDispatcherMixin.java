package dev.toapuro.mekanism_enchantable_machines.mixin.renderer;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.toapuro.mekanism_enchantable_machines.client.renderer.TileEntityRendererFoilWrapper;
import dev.toapuro.mekanism_enchantable_machines.util.CastUtil;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Unique
    private final Map<BlockEntityType<?>, BlockEntityRenderer<?>> mem$foilRenderers = new HashMap<>();
    @Shadow
    @Final
    private Supplier<BlockRenderDispatcher> blockRenderDispatcher;

    @ModifyReturnValue(method = "getRenderer", at = @At("RETURN"))
    public <E extends BlockEntity> BlockEntityRenderer<E> getRenderer(BlockEntityRenderer<E> original, @Local(argsOnly = true) E tile) {
        if (tile instanceof TileEntityMekanism) {
            BlockEntityType<?> tileType = tile.getType();
            if (mem$foilRenderers.containsKey(tileType)) {
                BlockEntityRenderer<E> renderer = CastUtil.safeCastNullable(mem$foilRenderers.get(tileType));
                if (renderer != null) return renderer;
            }

            TileEntityRendererFoilWrapper<E> foilRenderer = new TileEntityRendererFoilWrapper<>(blockRenderDispatcher.get(), original);
            mem$foilRenderers.put(tileType, foilRenderer);
        }

        return original;
    }
}
