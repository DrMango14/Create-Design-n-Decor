package dev.lopyluna.dndecor.content.blocks.frontlight;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.redstone.diodes.BrassDiodeBlock;
import dev.lopyluna.dndecor.register.DnDecorShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FrontlightBlock extends Block implements IWrenchable {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<Frontlight> ADDITIVE = EnumProperty.create("additive", Frontlight.class);
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

    public FrontlightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(ADDITIVE, Frontlight.TOP)
                .setValue(ROTATED, false)
        );
    }

    public static int getLight(BlockState state) {
        var lit = state.getValue(FrontlightBlock.LIT);
        return lit ? 15 : 0;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        var face = pState.getValue(FACING);
        if (face.getAxis().isVertical()) face = face == Direction.DOWN ? Direction.UP : Direction.DOWN;
        return DnDecorShapes.shape(3, 3, 8, 13, 13, 16).forDirectional(Direction.NORTH).get(face);
    }

    protected boolean hasNeighborSignal(Level level, BlockPos pos, Direction direction) {
        return level.hasSignal(pos.relative(direction), direction);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        boolean flag = level.hasSignal(neighborPos, Direction.getNearest(Vec3.atLowerCornerOf(pos.subtract(neighborPos))));
        var neighborState = level.getBlockState(neighborPos);
        if (neighborState.hasProperty(BlockStateProperties.POWERED) || neighborState.hasProperty(BrassDiodeBlock.POWERING)) flag = false;
        if (flag) {
            level.setBlockAndUpdate(pos, state.cycle(LIT));
            level.playSound(null, pos, state.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var face = context.getClickedFace();
        var pos = context.getClickedPos();
        var level = context.getLevel();
        var base = defaultBlockState().setValue(FACING, face);
        if (!base.canSurvive(level, pos)) return null;
        boolean flag = this.hasNeighborSignal(level, pos, face.getOpposite());
        if (flag != base.getValue(LIT)) base = base.setValue(LIT, flag);
        if (face.getAxis().isVertical()) base = base.setValue(ADDITIVE, Frontlight.EMPTY);
        return base;
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
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var face = context.getClickedFace();
        var rotated = face.getAxis().equals(state.getValue(FACING).getAxis()) ? state.cycle(ROTATED) : state.cycle(ADDITIVE);
        if (!rotated.canSurvive(level, context.getClickedPos())) return InteractionResult.PASS;
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(rotated, context));
        if (level.getBlockState(pos) != state) AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, ADDITIVE, ROTATED);
    }
}
