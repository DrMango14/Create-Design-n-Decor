package dev.lopyluna.dndecor.content.blocks.bolt;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BoltBlock extends Block implements IWrenchable {
    public static final VoxelShape SHAPE_DOWN = Block.box(3.0D, 13.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    public static final VoxelShape SHAPE_UP = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 3.0D, 13.0D);

    public static final VoxelShape SHAPE_EAST = Block.box(0.0D, 3.0D, 3.0D, 3.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(13.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_NORTH = Block.box(3.0D, 3.0D, 13.0D, 13.0D, 13.0D, 16.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 3.0D);

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<BoltRotation> ROT = EnumProperty.create("rotation", BoltRotation.class);

    public BoltBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
                .setValue(ROT, BoltRotation.D0)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            case UP -> SHAPE_UP;
            case DOWN -> SHAPE_DOWN;
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        //var dir = state.getValue(FACING);
        //return Block.canSupportCenter(level, pos.relative(dir.getOpposite()), dir);
        return true;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROT);
    }

    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        var player = context.getPlayer();
        BlockState base = defaultBlockState().setValue(FACING, face);

        if (!base.canSurvive(level, pos)) return null;

        BoltRotation rot = BoltRotation.D180;
        if (player != null && face.getAxis().isVertical()) {
            double yaw = player.getYRot();
            if (face == Direction.UP) yaw = -(yaw + 180.0);
            rot = BoltRotation.fromYaw(yaw);
        }
        return base.setValue(ROT, rot);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var rotated = state.setValue(ROT, state.getValue(ROT).getNext());
        if (!rotated.canSurvive(level, context.getClickedPos())) return InteractionResult.PASS;
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(rotated, context));
        if (level.getBlockState(pos) != state) playRotateSound(level, pos);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var rotated = state.setValue(ROT, state.getValue(ROT).getPrev());
        if (!rotated.canSurvive(level, context.getClickedPos())) return InteractionResult.PASS;
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(rotated, context));
        if (level.getBlockState(pos) != state) playRotateSound(level, pos);
        return InteractionResult.SUCCESS;
    }

    public void playRotateSound(Level level, BlockPos pos) {
        AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
    }
}
