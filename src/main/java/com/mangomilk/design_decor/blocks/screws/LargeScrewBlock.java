package com.mangomilk.design_decor.blocks.screws;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LargeScrewBlock extends ScrewBlock{
    public LargeScrewBlock(Properties p_52591_) {
        super(p_52591_);
    }

    public static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 2.0D, 12.0D, 12.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(14.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    public static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 14.0D, 12.0D, 12.0D, 16.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 2.0D);
    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        return switch (p_54561_.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            case UP -> SHAPE_UP;
            case DOWN -> SHAPE_DOWN;
            default -> SHAPE_NORTH;
        };
    }
}
