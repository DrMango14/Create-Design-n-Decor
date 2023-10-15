package com.mangomilk.design_decor.blocks.floodlight;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class FloodlightBlock extends DirectionalBlock implements IWrenchable {

    public static final BooleanProperty TURNED_ON = BooleanProperty.create("turned_on");

    public static final VoxelShape SHAPE_DOWN = Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    public static final VoxelShape SHAPE_UP = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);

    public static final VoxelShape SHAPE_EAST = Block.box(0.0D, 3.0D, 3.0D, 8.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(8.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_NORTH = Block.box(3.0D, 3.0D, 8.0D, 13.0D, 13.0D, 16.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 8.0D);
    public FloodlightBlock(Properties p_52591_) {
        super(p_52591_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(TURNED_ON, false));
    }

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
                return SHAPE_UP;
            case DOWN:
                return SHAPE_DOWN;
            default:
                return SHAPE_NORTH;
        }
    }
    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = IWrenchable.super.onWrenched(state, context);
        if (!onWrenched.consumesAction())
            return onWrenched;

        context.getLevel().setBlock(context.getClickedPos(),state.setValue(TURNED_ON,!state.getValue(TURNED_ON)),2);


        return onWrenched;
    }


    public BlockState updateShape(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
        return !this.canSurvive(p_57503_, p_57506_, p_57507_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(FACING,TURNED_ON);
    }
    public boolean canSurvive(BlockState p_58133_, LevelReader p_58134_, BlockPos p_58135_) {
        Direction direction = p_58133_.getValue(FACING);
        BlockPos blockpos = p_58135_.relative(direction.getOpposite());
        BlockState blockstate = p_58134_.getBlockState(blockpos);
        return blockstate.isFaceSturdy(p_58134_, blockpos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_58126_) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = p_58126_.getLevel();
        BlockPos blockpos = p_58126_.getClickedPos();
        Direction[] adirection = p_58126_.getNearestLookingDirections();

        for(Direction direction : adirection) {

            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }
}
