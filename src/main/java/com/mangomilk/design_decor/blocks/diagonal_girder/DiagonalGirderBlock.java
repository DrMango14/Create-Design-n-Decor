package com.mangomilk.design_decor.blocks.diagonal_girder;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@SuppressWarnings({"unused","deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DiagonalGirderBlock extends DirectionalBlock implements SimpleWaterloggedBlock, IWrenchable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty FACING_UP = BooleanProperty.create("facing_up");
    public DiagonalGirderBlock(Properties p_54120_) {
        super(p_54120_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(FACING_UP, false));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(WATERLOGGED,FACING, FACING_UP);
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
                Block.box(3, 3, 0, 13, 13, 5 ),
                Block.box(3, 0, 3, 13, 5, 13 ),
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
    @Override
    public FluidState getFluidState(BlockState p_51475_) {
        return p_51475_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51475_);
    }
    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        if (!p_54561_.getValue(FACING_UP)) {
            return switch (p_54561_.getValue(FACING)) {
                case NORTH, UP, DOWN -> SHAPE_NORTH;
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
            };
        }
        if (p_54561_.getValue(FACING_UP)) {
            return switch (p_54561_.getValue(FACING)) {
                case NORTH, UP, DOWN -> SHAPE_UP_NORTH;
                case SOUTH -> SHAPE_UP_SOUTH;
                case EAST -> SHAPE_UP_EAST;
                case WEST -> SHAPE_UP_WEST;
            };
        }
        return SHAPE_NORTH;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = IWrenchable.super.onWrenched(state, context);
        if (!onWrenched.consumesAction())
            return onWrenched;

        context.getLevel().setBlock(context.getClickedPos(),state.setValue(FACING_UP,!state.getValue(FACING_UP)),2);

        playRotateSound(context.getLevel(), context.getClickedPos());
        return onWrenched;
    }

    @Override
    public BlockState updateShape(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
        if (p_51461_.getValue(WATERLOGGED)) {
            p_51464_.scheduleTick(p_51465_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51464_));
        }

        return super.updateShape(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        Direction facing = Objects.requireNonNull(context.getPlayer()).getDirection();
        Direction clickedFace = context.getClickedFace();

        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            if (clickedFace == Direction.DOWN)
                return defaultBlockState().setValue(FACING, facing.getOpposite()).setValue(FACING_UP,true).setValue(WATERLOGGED, flag);
                else
            return defaultBlockState().setValue(FACING, facing.getOpposite()).setValue(FACING_UP,false).setValue(WATERLOGGED, flag);
        }
        if (clickedFace == Direction.DOWN)
            return defaultBlockState().setValue(FACING, facing).setValue(FACING_UP,true).setValue(WATERLOGGED, flag);


    return defaultBlockState().setValue(FACING, facing).setValue(FACING_UP,false).setValue(WATERLOGGED, flag);
    }
}
