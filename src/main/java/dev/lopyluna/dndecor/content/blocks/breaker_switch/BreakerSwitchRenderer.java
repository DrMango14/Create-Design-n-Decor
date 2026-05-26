package dev.lopyluna.dndecor.content.blocks.breaker_switch;

import com.mojang.blaze3d.vertex.PoseStack;
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
public class BreakerSwitchRenderer extends SafeBlockEntityRenderer<BreakerSwitchBE> {
    public BreakerSwitchRenderer(BlockEntityRendererProvider.Context context) {
    }

    protected void renderSafe(BreakerSwitchBE be, float pt, PoseStack ms, MultiBufferSource bs, int light, int overlay) {
        var leverState = be.getBlockState();
        var state = be.clientState.getValue(pt);
        var vb = bs.getBuffer(RenderType.cutoutMipped());
        var handle = CachedBuffers.partial(DnDecorPartialModels.BREAKER_SWITCH_HANDLE, leverState);
        var added = 35f;
        var angle = (((state/2f * (90f+added))-(added/2f)) / 180f) * (float) Math.PI;

        transform(handle, leverState).translate(8/16f, 0.5/16f, 8/16f).rotate(Direction.EAST.getAxis(), angle).translate(-8/16f, -0.5/16f, -8/16f);
        handle.light(light).renderInto(ms, vb);
    }

    private SuperByteBuffer transform(SuperByteBuffer buffer, BlockState leverState) {
        var face = leverState.getValue(AnalogLeverBlock.FACE);
        var rX = face == AttachFace.FLOOR ? 0.0F : (face == AttachFace.WALL ? 90.0F : 180.0F);
        var rY = AngleHelper.horizontalAngle(leverState.getValue(AnalogLeverBlock.FACING));
        buffer.rotateCentered((rY / 180.0F) * (float) Math.PI, Direction.UP);
        buffer.rotateCentered((rX / 180.0F) * (float) Math.PI, Direction.EAST);
        return buffer;
    }
}