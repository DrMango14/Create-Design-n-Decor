package com.mangomilk.mangomilk_building.blocks.diagonal_girder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DiagonalGirderBlock extends DirectionalBlock {




    public static final BooleanProperty FACING_UP = BooleanProperty.create("facing_up");
    public DiagonalGirderBlock(Properties p_54120_) {
        super(p_54120_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACING_UP, false));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(FACING, FACING_UP);
    }

    public static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(0.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    public static final VoxelShape SHAPE_NORTH = Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 16.0D);

    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        switch (p_54561_.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            case UP:
                return SHAPE_WEST;
            case DOWN:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();

        Direction clickedFace = context.getClickedFace();

        Direction facing = context.getPlayer().getDirection();
    if(clickedFace == Direction.DOWN)
        return blockstate.setValue(FACING, facing).setValue(FACING_UP,true);




    return blockstate.setValue(FACING, facing).setValue(FACING_UP,false);
    }
}
