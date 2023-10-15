package com.mangomilk.design_decor.blocks.crushing_wheels;

import com.mangomilk.design_decor.registry.MmbBlockEntities;
import com.mangomilk.design_decor.registry.MmbBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock.VALID;

public class MmbCrushingWheelBlock extends CrushingWheelBlock {

	public MmbCrushingWheelBlock(Properties properties) {
		super(properties);
	}
	public void updateControllers(BlockState state, Level world, BlockPos pos, Direction side) {
		if (side.getAxis() == state.getValue(AXIS))
			return;
		if (world == null)
			return;

		BlockPos controllerPos = pos.relative(side);
		BlockPos otherWheelPos = pos.relative(side, 2);

		boolean controllerExists =
				MmbBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.has(world.getBlockState(controllerPos));
		boolean controllerIsValid = controllerExists && world.getBlockState(controllerPos)
				.getValue(VALID);
		Direction controllerOldDirection = controllerExists ? world.getBlockState(controllerPos)
				.getValue(CrushingWheelControllerBlock.FACING) : null;

		boolean controllerShouldExist = false;
		boolean controllerShouldBeValid = false;
		Direction controllerNewDirection = Direction.DOWN;

		BlockState otherState = world.getBlockState(otherWheelPos);
		if (
				AllBlocks.CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.GRANITE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.DIORITE_CRUSHING_WHEEL.has(otherState)||

				MmbBlocks.LIMESTONE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.OCHRUM_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.SCORCHIA_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.SCORIA_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.TUFF_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.VERIDIUM_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.DRIPSTONE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.DEEPSLATE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.CRIMSITE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.CALCITE_CRUSHING_WHEEL.has(otherState)||
				MmbBlocks.ASURINE_CRUSHING_WHEEL.has(otherState)



		) {
			controllerShouldExist = true;

			CrushingWheelBlockEntity be = getBlockEntity(world, pos);
			CrushingWheelBlockEntity otherBE = getBlockEntity(world, otherWheelPos);

			if (be != null && otherBE != null && (be.getSpeed() > 0) != (otherBE.getSpeed() > 0)
					&& be.getSpeed() != 0) {
				Axis wheelAxis = state.getValue(AXIS);
				Axis sideAxis = side.getAxis();
				int controllerADO = Math.round(Math.signum(be.getSpeed())) * side.getAxisDirection()
						.getStep();
				Vec3 controllerDirVec = new Vec3(wheelAxis == Axis.X ? 1 : 0, wheelAxis == Axis.Y ? 1 : 0,
						wheelAxis == Axis.Z ? 1 : 0).cross(
						new Vec3(sideAxis == Axis.X ? 1 : 0, sideAxis == Axis.Y ? 1 : 0, sideAxis == Axis.Z ? 1 : 0));

				controllerNewDirection = Direction.getNearest(controllerDirVec.x * controllerADO,
						controllerDirVec.y * controllerADO, controllerDirVec.z * controllerADO);

				controllerShouldBeValid = true;
			}
			if (otherState.getValue(AXIS) != state.getValue(AXIS))
				controllerShouldExist = false;
		}

		if (!controllerShouldExist) {
			if (controllerExists)
				world.setBlockAndUpdate(controllerPos, Blocks.AIR.defaultBlockState());
			return;
		}

		if (!controllerExists) {
			if (!world.getBlockState(controllerPos)
					.getMaterial()
					.isReplaceable())
				return;
			world.setBlockAndUpdate(controllerPos, MmbBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.getDefaultState()
					.setValue(VALID, controllerShouldBeValid)
					.setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
		} else if (controllerIsValid != controllerShouldBeValid || controllerOldDirection != controllerNewDirection) {
			world.setBlockAndUpdate(controllerPos, world.getBlockState(controllerPos)
					.setValue(VALID, controllerShouldBeValid)
					.setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
		}

		( MmbBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.get())
				.updateSpeed(world.getBlockState(controllerPos), world, controllerPos);

	}

	@Override
	public Class<CrushingWheelBlockEntity> getBlockEntityClass() {
		return CrushingWheelBlockEntity.class;
	}
	
	@Override
	public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
		return MmbBlockEntities.MMB_CRUSHING_WHEEL.get();
	}

}
