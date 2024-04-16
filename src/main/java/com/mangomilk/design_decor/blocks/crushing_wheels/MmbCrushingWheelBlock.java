package com.mangomilk.design_decor.blocks.crushing_wheels;

import com.mangomilk.design_decor.registry.CDDBlockEntities;
import com.mangomilk.design_decor.registry.CDDBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock.VALID;

public class MmbCrushingWheelBlock extends CrushingWheelBlock {

	public MmbCrushingWheelBlock(Properties properties) {
		super(properties);
	}


	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		for (Direction d : Iterate.directions) {
			if (d.getAxis() == state.getValue(AXIS))
				continue;
			if (CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.has(worldIn.getBlockState(pos.relative(d))))
				worldIn.removeBlock(pos.relative(d), isMoving);
			if (AllBlocks.CRUSHING_WHEEL_CONTROLLER.has(worldIn.getBlockState(pos.relative(d))))
				worldIn.removeBlock(pos.relative(d), isMoving);
		}

		super.onRemove(state, worldIn, pos, newState, isMoving);
	}


	public void updateControllers(BlockState state, Level world, BlockPos pos, Direction side) {
		if (side.getAxis() == state.getValue(AXIS))
			return;
		if (world == null)
			return;

		BlockPos controllerPos = pos.relative(side);
		BlockPos otherWheelPos = pos.relative(side, 2);

		boolean controllerExists =
				CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.has(world.getBlockState(controllerPos));
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
				CDDBlocks.GRANITE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.DIORITE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.LIMESTONE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.OCHRUM_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.SCORCHIA_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.SCORIA_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.TUFF_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.VERIDIUM_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.DRIPSTONE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.DEEPSLATE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.CRIMSITE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.CALCITE_CRUSHING_WHEEL.has(otherState)||
				CDDBlocks.ASURINE_CRUSHING_WHEEL.has(otherState)

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
			world.setBlockAndUpdate(controllerPos, CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.getDefaultState()
					.setValue(VALID, controllerShouldBeValid)
					.setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
		} else if (controllerIsValid != controllerShouldBeValid || controllerOldDirection != controllerNewDirection) {
			world.setBlockAndUpdate(controllerPos, world.getBlockState(controllerPos)
					.setValue(VALID, controllerShouldBeValid)
					.setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
		}

		( CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.get())
				.updateSpeed(world.getBlockState(controllerPos), world, controllerPos);

	}


	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		for (Direction direction : Iterate.directions) {
			BlockPos neighbourPos = pos.relative(direction);
			BlockState neighbourState = worldIn.getBlockState(neighbourPos);
			Axis stateAxis = state.getValue(AXIS);
			if (CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER.has(neighbourState) && direction.getAxis() != stateAxis)
				return false;
			if (AllBlocks.CRUSHING_WHEEL_CONTROLLER.has(neighbourState) && direction.getAxis() != stateAxis)
				return false;
			if (!(worldIn.getBlockState(neighbourPos).getBlock() instanceof CrushingWheelBlock))
				continue;
			if (neighbourState.getValue(AXIS) != stateAxis || stateAxis != direction.getAxis())
				return false;
		}

		return true;
	}

	@Override
	public Class<CrushingWheelBlockEntity> getBlockEntityClass() {
		return CrushingWheelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
		return CDDBlockEntities.MMB_CRUSHING_WHEEL.get();
	}

}
