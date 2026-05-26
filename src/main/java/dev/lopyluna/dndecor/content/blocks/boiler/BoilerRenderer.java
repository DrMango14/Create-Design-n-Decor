package dev.lopyluna.dndecor.content.blocks.boiler;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;

@SuppressWarnings({"NullableProblems", "unused"})
public class BoilerRenderer implements BlockEntityRenderer<BoilerBlockEntity> {
    public BoilerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BoilerBlockEntity be, float pt, PoseStack ps, MultiBufferSource bs, int l, int o) {
        var level = be.getLevel();
        if (level == null) return;
        var state = be.getBlockState();
        if (!(state.getBlock() instanceof BoilerBlock)) return;
        var pos = be.getBlockPos();
        var axis = state.getValue(BoilerBlock.AXIS);

        var vb = bs.getBuffer(RenderType.cutout());
        var block = CachedBuffers.block(state);


        int totalBlock = 0;
        int bestSky = 0;
        int samples = 0;

        for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) {
            var samplePos = axis == Direction.Axis.Y ? pos.offset(a, 0, b) : axis == Direction.Axis.Z ? pos.offset(a, b, 0) : pos.offset(0, a, b);
            var sampleState = level.getBlockState(samplePos);
            if (sampleState.isSolidRender(level, samplePos)) continue;

            totalBlock += level.getBrightness(LightLayer.BLOCK, samplePos);
            bestSky = Math.max(bestSky, level.getBrightness(LightLayer.SKY, samplePos));
            samples++;
        }

        int avgBlock = totalBlock / samples;
        int avgLight = LightTexture.pack(avgBlock, bestSky);

        block.light(avgLight).overlay(o).renderInto(ps, vb);
    }
}