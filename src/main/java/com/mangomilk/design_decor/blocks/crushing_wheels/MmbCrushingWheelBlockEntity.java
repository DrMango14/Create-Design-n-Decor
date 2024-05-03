package com.mangomilk.design_decor.blocks.crushing_wheels;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;


public class MmbCrushingWheelBlockEntity extends CrushingWheelBlockEntity {

	public static final DamageSource DAMAGE_SOURCE = new DamageSource("create.crush").bypassArmor()
			.setScalesWithDifficulty();

	public MmbCrushingWheelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		setLazyTickRate(20);
	}

	@Override
	protected AABB createRenderBoundingBox() {
		return new AABB(worldPosition).inflate(1);
	}
}
