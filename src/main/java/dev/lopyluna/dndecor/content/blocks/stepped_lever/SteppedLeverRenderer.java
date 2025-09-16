package dev.lopyluna.dndecor.content.blocks.stepped_lever;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlock;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

@SuppressWarnings("unused")
public class SteppedLeverRenderer extends SafeBlockEntityRenderer<SteppedLeverBlockEntity> {
    public SteppedLeverRenderer(BlockEntityRendererProvider.Context context) {
    }

    protected void renderSafe(SteppedLeverBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
            BlockState leverState = be.getBlockState();
            float state = be.clientState.getValue(partialTicks);
            VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
            SuperByteBuffer handle = CachedBuffers.partial(DnDecorPartialModels.STEPPED_LEVER_HANDLE, leverState);
            float angle = (((state / 15.0F * 135.0F) - 22.5f) / 180.0F) * (float) Math.PI;

            this.transform(handle, leverState).translate(0.5F, 0.25F, 0.5F).rotate(Direction.EAST.getAxis(), angle).translate(-0.5F, -0.25F, -0.5F);
            handle.light(light).renderInto(ms, vb);

    }

    private SuperByteBuffer transform(SuperByteBuffer buffer, BlockState leverState) {
        AttachFace face = leverState.getValue(AnalogLeverBlock.FACE);
        float rX = face == AttachFace.FLOOR ? 0.0F : (face == AttachFace.WALL ? 90.0F : 180.0F);
        float rY = AngleHelper.horizontalAngle(leverState.getValue(AnalogLeverBlock.FACING));
        buffer.rotateCentered((rY / 180.0F) * (float) Math.PI, Direction.UP);
        buffer.rotateCentered((rX / 180.0F) * (float) Math.PI, Direction.EAST);
        return buffer;
    }
}