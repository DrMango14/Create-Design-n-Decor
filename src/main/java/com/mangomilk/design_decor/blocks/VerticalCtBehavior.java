package com.mangomilk.design_decor.blocks;


import com.mangomilk.design_decor.registry.CDDSpriteShifts;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class VerticalCtBehavior extends ConnectedTextureBehaviour.Base {


	private CTSpriteShiftEntry shift;
	public VerticalCtBehavior(CTSpriteShiftEntry shift) {
		this.shift = shift;
	}


	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {

	return shift;

	//if(direction.getAxis().isHorizontal())
	//	return shift;


	//return null;
	}

	@Override
	protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {



		return Direction.UP;
	}
//
//
//


	public boolean buildContextForOccludedDirections() {
		return super.buildContextForOccludedDirections();
	}



}