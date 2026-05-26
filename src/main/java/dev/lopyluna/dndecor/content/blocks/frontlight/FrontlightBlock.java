package dev.lopyluna.dndecor.content.blocks.frontlight;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.lopyluna.dndecor.register.DnDecorShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("NullableProblems")
@ParametersAreNonnullByDefault
public class FrontlightBlock extends Block implements IWrenchable {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<Frontlight> ADDITIVE = EnumProperty.create("additive", Frontlight.class);
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public static final BooleanProperty NO_BASE = BooleanProperty.create("no_base");

    public FrontlightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(POWERED, false)
                .setValue(ADDITIVE, Frontlight.TOP)
                .setValue(ROTATED, false)
                .setValue(NO_BASE, false)
        );
    }

    public static int getLight(BlockState state) {
        var lit = state.getValue(FrontlightBlock.LIT);
        return lit ? 15 : 0;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        var face = pState.getValue(FACING);
        if (face.getAxis().isVertical()) face = face == Direction.DOWN ? Direction.UP : Direction.DOWN;
        return DnDecorShapes.shape(3, 3, pState.getValue(NO_BASE) ? 12 : 8, 13, 13, 16).forDirectional(Direction.NORTH).get(face);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel server) checkAndFlip(state, server, pos);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var face = context.getClickedFace();
        var pos = context.getClickedPos();
        var level = context.getLevel();
        var base = defaultBlockState().setValue(FACING, face);
        var flag = level.hasNeighborSignal(pos);
        base = base.setValue(POWERED, flag).setValue(LIT, flag != base.getValue(LIT));
        if (face.getAxis().isVertical()) base = base.setValue(ADDITIVE, Frontlight.EMPTY);
        if (context.getPlayer() instanceof Player player && player.isShiftKeyDown()) base = base.setValue(NO_BASE, true);
        return base;
    }

    public void checkAndFlip(BlockState state, ServerLevel level, BlockPos pos) {
        var flag = level.hasNeighborSignal(pos);
        if (flag != state.getValue(POWERED)) {
            if (!state.getValue(POWERED)) {
                state = state.cycle(LIT);
                level.playSound(null, pos, state.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
            }
            level.setBlockAndUpdate(pos, state.setValue(POWERED, flag));
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
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
        builder.add(FACING, LIT, POWERED, ADDITIVE, ROTATED, NO_BASE);
    }
}
