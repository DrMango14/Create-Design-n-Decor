package dev.lopyluna.dndecor.content.blocks.cogs;


import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import net.minecraft.core.Direction;

import java.util.function.Consumer;

public class DnDCogwheelVisual {

	public static BlockEntityVisual<BracketedKineticBlockEntity> create(VisualizationContext context, BracketedKineticBlockEntity be, float partialTick) {
		if (ICogWheel.isLargeCog(be.getBlockState())) {
			return new LargeCogVisual(context, be, partialTick);
		} else {

			return new SmallCogVisual(context, be, partialTick);
		}
	}
	public static class SmallCogVisual extends SingleAxisRotatingVisual<BracketedKineticBlockEntity> {

		protected final RotatingInstance additionalShaft;

		private SmallCogVisual(VisualizationContext context, BracketedKineticBlockEntity blockEntity, float partialTick) {
			super(context, blockEntity, partialTick, getSmallModel(blockEntity));

			Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);

			additionalShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.COGWHEEL_SHAFT))
					.createInstance();


			additionalShaft.rotateToFace(axis)
					.setup(blockEntity)
					.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos))
					.setPosition(getVisualPosition())
					.setChanged();
		}



		public static Model getSmallModel(BracketedKineticBlockEntity be){


			PartialModel partialModel = DnDecorPartialModels.DYED_COGWHEEL.get(((DnDCogWheelBlock)be.getBlockState().getBlock()).color);
			if(((DnDCogWheelBlock)be.getBlockState().getBlock()).customModel !=null)
				partialModel = ((DnDCogWheelBlock)be.getBlockState().getBlock()).customModel;
			return Models.partial(partialModel);
		}



		@Override
		public void update(float pt) {
			super.update(pt);
			additionalShaft.setup(blockEntity)
					.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(rotationAxis(), pos))
					.setChanged();
		}

		@Override
		public void updateLight(float partialTick) {
			super.updateLight(partialTick);
			relight(additionalShaft);
		}

		@Override
		protected void _delete() {
			super._delete();
			additionalShaft.delete();
		}

		@Override
		public void collectCrumblingInstances(Consumer<Instance> consumer) {
			super.collectCrumblingInstances(consumer);
			consumer.accept(additionalShaft);
		}
	}

	public static class LargeCogVisual extends SingleAxisRotatingVisual<BracketedKineticBlockEntity> {

		protected final RotatingInstance additionalShaft;

		private LargeCogVisual(VisualizationContext context, BracketedKineticBlockEntity blockEntity, float partialTick) {
			super(context, blockEntity, partialTick, getLargeModel(blockEntity));

			Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);

			additionalShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.COGWHEEL_SHAFT))
				.createInstance();


			additionalShaft.rotateToFace(axis)
				.setup(blockEntity)
				.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos))
				.setPosition(getVisualPosition())
				.setChanged();
		}



		public static Model getLargeModel(BracketedKineticBlockEntity be){


			PartialModel partialModel = DnDecorPartialModels.DYED_LARGE_COGWHEEL.get(((DnDCogWheelBlock)be.getBlockState().getBlock()).color);

			if(((DnDCogWheelBlock)be.getBlockState().getBlock()).customModel !=null)
				partialModel = ((DnDCogWheelBlock)be.getBlockState().getBlock()).customModel;

			return Models.partial(partialModel);
		}



		@Override
		public void update(float pt) {
			super.update(pt);
			additionalShaft.setup(blockEntity)
				.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(rotationAxis(), pos))
				.setChanged();
		}

		@Override
		public void updateLight(float partialTick) {
			super.updateLight(partialTick);
			relight(additionalShaft);
		}

		@Override
		protected void _delete() {
			super._delete();
			additionalShaft.delete();
		}

		@Override
		public void collectCrumblingInstances(Consumer<Instance> consumer) {
			super.collectCrumblingInstances(consumer);
			consumer.accept(additionalShaft);
		}
	}
}
