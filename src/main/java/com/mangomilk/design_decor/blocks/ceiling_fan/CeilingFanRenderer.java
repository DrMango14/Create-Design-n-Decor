package com.mangomilk.design_decor.blocks.ceiling_fan;

import com.jozufozu.flywheel.backend.Backend;
import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class CeilingFanRenderer extends KineticBlockEntityRenderer<CeilingFanBlockEntity> {

    public CeilingFanRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CeilingFanBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        float speed = be.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = be.angle + speed * partialTicks;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderFlywheel(be, ms, light, blockState, angle, vb);
    }

    private void renderFlywheel(CeilingFanBlockEntity be, PoseStack ms, int light, BlockState blockState, float angle,
                                VertexConsumer vb) {
        SuperByteBuffer fan = CachedBufferer.partial(CDDPartialModels.CEILING_FAN,blockState);
        kineticRotationTransform(fan, be, getRotationAxisOf(be), AngleHelper.rad(angle), light);
        fan.renderInto(ms, vb);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(CeilingFanBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state, Direction.UP);
    }
    @Override
    protected BlockState getRenderedBlockState(CeilingFanBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }

}