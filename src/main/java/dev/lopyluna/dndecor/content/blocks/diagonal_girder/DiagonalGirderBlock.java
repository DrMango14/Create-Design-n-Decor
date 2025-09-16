package dev.lopyluna.dndecor.content.blocks.diagonal_girder;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class DiagonalGirderBlock extends Block implements SimpleWaterloggedBlock, IWrenchable {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty FACING_UP = BooleanProperty.create("facing_up");

    public DiagonalGirderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(FACING_UP, false));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED,FACING, FACING_UP);
    }

    public static final VoxelShape SHAPE_EAST = Shapes.or(Block.box(11, 3, 3, 16, 13, 13), Block.box(3, 0, 3, 13, 5, 13), Block.box(4, 5, 4, 11, 12, 12));
    public static final VoxelShape SHAPE_WEST = Shapes.or(Block.box(0, 3, 3, 5, 13, 13), Block.box(3, 0, 3, 13, 5, 13), Block.box(5, 5, 4, 12, 12, 12));
    public static final VoxelShape SHAPE_NORTH = Shapes.or(Block.box(3, 3, 0, 13, 13, 5 ), Block.box(3, 0, 3, 13, 5, 13 ), Block.box(4, 5, 5, 12, 12, 12));
    public static final VoxelShape SHAPE_SOUTH = Shapes.or(Block.box(3, 3, 11, 13, 13, 16), Block.box(3, 0, 3, 13, 5, 13), Block.box(4, 5, 4, 12, 12, 11));
    public static final VoxelShape SHAPE_UP_EAST = Shapes.or(Block.box(11, 3, 3, 16, 13, 13), Block.box(3, 11, 3, 13, 16, 13), Block.box(4, 4, 4, 11, 11, 12));
    public static final VoxelShape SHAPE_UP_WEST = Shapes.or(Block.box(0, 3, 3, 5, 13, 13), Block.box(3, 11, 3, 13, 16, 13), Block.box(5, 4, 4, 12, 11, 12));
    public static final VoxelShape SHAPE_UP_NORTH = Shapes.or(Block.box(3, 3, 0, 13, 13, 5), Block.box(3, 11, 3, 13, 16, 13), Block.box(4, 4, 5, 12, 11, 12));
    public static final VoxelShape SHAPE_UP_SOUTH = Shapes.or(Block.box(3, 3, 11, 13, 13, 16), Block.box(3, 11, 3, 13, 16, 13), Block.box(4, 4, 4, 12, 11, 11));


    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(FACING_UP)) {
            return switch (state.getValue(FACING)) {
                case NORTH, UP, DOWN -> SHAPE_NORTH;
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
            };
        }
        if (state.getValue(FACING_UP)) {
            return switch (state.getValue(FACING)) {
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
        if (!onWrenched.consumesAction()) return onWrenched;
        var pos = context.getClickedPos();
        var level = context.getLevel();

        level.setBlock(pos,state.setValue(FACING_UP,!state.getValue(FACING_UP)),2);
        AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return onWrenched;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, direction, blockState, level, pos, neighborPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        Direction facing = Objects.requireNonNull(context.getPlayer()).getDirection();
        Direction clickedFace = context.getClickedFace();

        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            if (clickedFace == Direction.DOWN) return defaultBlockState().setValue(FACING, facing.getOpposite()).setValue(FACING_UP,true).setValue(WATERLOGGED, flag);
            else return defaultBlockState().setValue(FACING, facing.getOpposite()).setValue(FACING_UP,false).setValue(WATERLOGGED, flag);
        }
        if (clickedFace == Direction.DOWN) return defaultBlockState().setValue(FACING, facing).setValue(FACING_UP,true).setValue(WATERLOGGED, flag);
        return defaultBlockState().setValue(FACING, facing).setValue(FACING_UP,false).setValue(WATERLOGGED, flag);
    }
}