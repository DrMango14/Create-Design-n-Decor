package com.mangomilk.design_decor.blocks.metal_support.diagonal;


import com.mangomilk.design_decor.registry.CDDBlocks;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.mangomilk.design_decor.blocks.metal_support.MetalSupportBlock.TOP;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class DiagonalMetalSupportCtBehavior extends ConnectedTextureBehaviour.Base {


	private CTSpriteShiftEntry shift;
	public DiagonalMetalSupportCtBehavior(CTSpriteShiftEntry shift) {
		this.shift = shift;
	}


	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {





	if(direction == Direction.UP)
		return shift;


	return null;
	}

	@Override
	protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {



		return Direction.UP;
	}

	@Override
	protected Direction getRightDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
		return state.getValue(FACING).getCounterClockWise();
	}



	//
//
//


	@Override
	public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {

		if(pos.getY()!=otherPos.getY())
			return false;


		if(other.is(state.getBlock()))
			if(state.getValue(FACING)!=other.getValue(FACING))
				return false;

		return super.connectsTo(state, other, reader, pos, otherPos, face);
	}

	public boolean buildContextForOccludedDirections() {
		return super.buildContextForOccludedDirections();
	}



}