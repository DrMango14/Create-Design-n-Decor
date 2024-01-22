package com.mangomilk.design_decor.blocks.stepped_lever;

import com.jozufozu.flywheel.backend.Backend;
import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

public class SteppedLeverRenderer extends SafeBlockEntityRenderer<SteppedLeverBlockEntity> {

	public SteppedLeverRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(SteppedLeverBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
							  int light, int overlay) {

		if (Backend.canUseInstancing(be.getLevel())) return;

		BlockState leverState = be.getBlockState();
		float state = be.clientState.getValue(partialTicks);

		VertexConsumer vb = buffer.getBuffer(RenderType.solid());

		// Handle
		SuperByteBuffer handle = CachedBufferer.partial(CDDPartialModels.STEPPED_LEVER_HANDLE, leverState);
		float angle = (float) ((state / 15) * 90 / 180 * Math.PI);
		transform(handle, leverState)
				.translate(1 / 2f, 1 / 4f, 1 / 2f)
				.rotate(Direction.EAST, angle)
				.translate(-1 / 2f, -1 / 4f, -1 / 2f);
		handle.light(light)
				.renderInto(ms, vb);

		//// Indicator
		//int color = Color.mixColors(0x2C0300, 0xCD0000, state / 15f);
		//SuperByteBuffer indicator = transform(CachedBufferer.partial(AllPartialModels.ANALOG_LEVER_INDICATOR, leverState), leverState);
		//indicator.light(light)
		//		.color(color)
		//		.renderInto(ms, vb);
	}

	private SuperByteBuffer transform(SuperByteBuffer buffer, BlockState leverState) {
		AttachFace face = leverState.getValue(SteppedLeverBlock.FACE);
		float rX = face == AttachFace.FLOOR ? 0 : face == AttachFace.WALL ? 90 : 180;
		float rY = AngleHelper.horizontalAngle(leverState.getValue(SteppedLeverBlock.FACING));
		buffer.rotateCentered(Direction.UP, (float) (rY / 180 * Math.PI));
		buffer.rotateCentered(Direction.EAST, (float) (rX / 180 * Math.PI));
		return buffer;
	}

}
