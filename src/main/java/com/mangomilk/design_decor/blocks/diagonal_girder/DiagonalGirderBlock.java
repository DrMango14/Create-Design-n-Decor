package com.mangomilk.design_decor.blocks.diagonal_girder;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DiagonalGirderBlock extends DirectionalBlock implements IWrenchable {

    public static final BooleanProperty FACING_UP = BooleanProperty.create("facing_up");
    public DiagonalGirderBlock(Properties p_54120_) {
        super(p_54120_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACING_UP, false));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(FACING, FACING_UP);
    }

    public static final VoxelShape SHAPE_EAST = eShape();
    public static final VoxelShape SHAPE_WEST = wShape();
    public static final VoxelShape SHAPE_NORTH = nShape();
    public static final VoxelShape SHAPE_SOUTH = sShape();

    public static final VoxelShape SHAPE_UP_EAST = ueShape();
    public static final VoxelShape SHAPE_UP_WEST = uwShape();
    public static final VoxelShape SHAPE_UP_NORTH = unShape();
    public static final VoxelShape SHAPE_UP_SOUTH = usShape();

    public static VoxelShape nShape(){
        return Shapes.or(
                Block.box(3, 3, 0, 13, 13, 5),
                Block.box(3, 0, 3, 13, 5, 13),
                Block.box(4, 5, 5, 12, 12, 12)
        );
    }
    public static VoxelShape eShape(){
        return Shapes.or(
                Block.box(11, 3, 3, 16, 13, 13),
                Block.box(3, 0, 3, 13, 5, 13),
                Block.box(4, 5, 4, 11, 12, 12)
        );
    }

    public static VoxelShape sShape(){
        return Shapes.or(
                Block.box(3, 3, 11, 13, 13, 16),
                Block.box(3, 0, 3, 13, 5, 13),
                Block.box(4, 5, 4, 12, 12, 11)
        );
    }

    public static VoxelShape wShape(){
        return Shapes.or(
                Block.box(0, 3, 3, 5, 13, 13),
                Block.box(3, 0, 3, 13, 5, 13),
                Block.box(5, 5, 4, 12, 12, 12)
        );
    }

    public static VoxelShape unShape(){
        return Shapes.or(
                Block.box(3, 3, 0, 13, 13, 5),
                Block.box(3, 11, 3, 13, 16, 13),
                Block.box(4, 4, 5, 12, 11, 12)
        );
    }
    public static VoxelShape ueShape(){
        return Shapes.or(
                Block.box(11, 3, 3, 16, 13, 13),
                Block.box(3, 11, 3, 13, 16, 13),
                Block.box(4, 4, 4, 11, 11, 12)
        );
    }

    public static VoxelShape usShape(){
        return Shapes.or(
                Block.box(3, 3, 11, 13, 13, 16),
                Block.box(3, 11, 3, 13, 16, 13),
                Block.box(4, 4, 4, 12, 11, 11)
        );
    }

    public static VoxelShape uwShape(){
        return Shapes.or(
                Block.box(0, 3, 3, 5, 13, 13),
                Block.box(3, 11, 3, 13, 16, 13),
                Block.box(5, 4, 4, 12, 11, 12)
        );
    }

    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        if (!p_54561_.getValue(FACING_UP)) {
            return switch (p_54561_.getValue(FACING)) {
                case NORTH -> SHAPE_NORTH;
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                case UP -> SHAPE_EAST;
                case DOWN -> SHAPE_EAST;
            };
        }
        if (p_54561_.getValue(FACING_UP)) {
            return switch (p_54561_.getValue(FACING)) {
                case NORTH -> SHAPE_UP_NORTH;
                case SOUTH -> SHAPE_UP_SOUTH;
                case EAST -> SHAPE_UP_EAST;
                case WEST -> SHAPE_UP_WEST;
                case UP -> SHAPE_UP_EAST;
                case DOWN -> SHAPE_UP_EAST;
            };
        }
        return SHAPE_NORTH;
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
