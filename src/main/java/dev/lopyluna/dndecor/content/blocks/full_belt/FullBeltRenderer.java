package dev.lopyluna.dndecor.content.blocks.full_belt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.belt.*;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FullBeltRenderer extends BeltRenderer {

	public FullBeltRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}
	@Override
	protected void renderSafe(BeltBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		if (!VisualizationManager.supportsVisualization(be.getLevel())) {
			BlockState blockState = be.getBlockState();
			if (!DnDecorBlocks.BELT.has(blockState)) return;

			BeltSlope beltSlope = blockState.getValue(BeltBlock.SLOPE);
			BeltPart part = blockState.getValue(BeltBlock.PART);
			Direction facing = blockState.getValue(BeltBlock.HORIZONTAL_FACING);
			AxisDirection axisDirection = facing.getAxisDirection();

			boolean downward = beltSlope == BeltSlope.DOWNWARD;
			boolean upward = beltSlope == BeltSlope.UPWARD;
			boolean diagonal = downward || upward;
			boolean start = part == BeltPart.START;
			boolean end = part == BeltPart.END;
			boolean sideways = beltSlope == BeltSlope.SIDEWAYS;
			boolean alongX = facing.getAxis() == Direction.Axis.X;

			PoseStack localTransforms = new PoseStack();
			var msr = TransformStack.of(localTransforms);
			VertexConsumer vb = buffer.getBuffer(RenderType.solid());
			float renderTick = AnimationTickHolder.getRenderTime(be.getLevel());

			msr.center().rotateYDegrees(AngleHelper.horizontalAngle(facing) + (upward ? 180 : 0) + (sideways ? 270 : 0))
					.rotateZDegrees(sideways ? 90 : 0)
					.rotateXDegrees(!diagonal && beltSlope != BeltSlope.HORIZONTAL ? 90 : 0)
					.uncenter();

			if (downward || beltSlope == BeltSlope.VERTICAL && axisDirection == AxisDirection.POSITIVE) {
				boolean b = start;
				start = end;
				end = b;
			}

			DyeColor color = be.color.orElse(null);
			for (boolean bottom : Iterate.trueAndFalse) {
				PartialModel beltPartial = getBeltPartial(diagonal, start, end, bottom);
				SuperByteBuffer beltBuffer = CachedBuffers.partial(beltPartial, blockState).light(light);
				SpriteShiftEntry spriteShift = getSpriteShiftEntry(color, diagonal, bottom);

				// UV shift
				float speed = be.getSpeed();
				if (speed != 0 || be.color.isPresent()) {
					float time = renderTick * axisDirection.getStep();
					if (diagonal && (downward ^ alongX) || !sideways && !diagonal && alongX || sideways && axisDirection == AxisDirection.NEGATIVE) speed = -speed;

					float scrollMulti = diagonal ? 3f / 8f : 0.5f;
					float spriteSize = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();

					double scroll = speed * time / (31.5 * 16) + (bottom ? 0.5 : 0.0);
					scroll = scroll - Math.floor(scroll);
					scroll = scroll * spriteSize * scrollMulti;

					beltBuffer.shiftUVScrolling(spriteShift, (float) scroll);
				}
				beltBuffer.transform(localTransforms).renderInto(ms, vb);
				if (diagonal) break;
			}

			if (be.hasPulley()) {
				Direction dir = sideways ? Direction.UP : blockState.getValue(BeltBlock.HORIZONTAL_FACING).getClockWise();

				Supplier<PoseStack> matrixStackSupplier = () -> {
					PoseStack stack = new PoseStack();
					var stacker = TransformStack.of(stack);
					stacker.center();
					if (dir.getAxis() == Direction.Axis.X) stacker.rotateYDegrees(90);
					if (dir.getAxis() == Direction.Axis.Y) stacker.rotateXDegrees(90);
					stacker.rotateXDegrees(90);
					stacker.uncenter();
					return stack;
				};

				SuperByteBuffer superBuffer = CachedBuffers.partialDirectional(AllPartialModels.BELT_PULLEY, blockState, dir, matrixStackSupplier);
				KineticBlockEntityRenderer.standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);
			}
		}
		renderItems(be, partialTicks, ms, buffer, light, overlay);
	}

	public static SpriteShiftEntry getSpriteShiftEntry(DyeColor color, boolean diagonal, boolean bottom) {
		if (color != null) return (diagonal ? DnDecorSpriteShifts.DYED_DIAGONAL_BELTS : bottom ? DnDecorSpriteShifts.DYED_OFFSET_BELTS : DnDecorSpriteShifts.DYED_BELTS).get(color);
		else return diagonal ? DnDecorSpriteShifts.BELT_DIAGONAL : bottom ? DnDecorSpriteShifts.BELT_OFFSET : DnDecorSpriteShifts.BELT;
	}

	public static PartialModel getBeltPartial(boolean diagonal, boolean start, boolean end, boolean bottom) {
		if (diagonal) {
			if (start) return DnDecorPartialModels.BELT_DIAGONAL_START;
			if (end) return DnDecorPartialModels.BELT_DIAGONAL_END;
			return DnDecorPartialModels.BELT_DIAGONAL_MIDDLE;
		} else if (bottom) {
			if (start) return DnDecorPartialModels.BELT_START_BOTTOM;
			if (end) return DnDecorPartialModels.BELT_END_BOTTOM;
			return DnDecorPartialModels.BELT_MIDDLE_BOTTOM;
		} else {
			if (start) return DnDecorPartialModels.BELT_START;
			if (end) return DnDecorPartialModels.BELT_END;
			return DnDecorPartialModels.BELT_MIDDLE;
		}
	}
}
