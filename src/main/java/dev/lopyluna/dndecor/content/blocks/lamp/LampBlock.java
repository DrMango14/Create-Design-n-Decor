package dev.lopyluna.dndecor.content.blocks.lamp;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class LampBlock extends Block implements IWrenchable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public LampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, false)
                .setValue(POWERED, false));
    }

    public static int getLight(BlockState state) {
        var lit = state.getValue(LIT);
        return lit ? 15 : 0;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel server) checkAndFlip(state, server, pos);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        return IWrenchable.super.updateAfterWrenched(newState, context).cycle(LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var pos = context.getClickedPos();
        var level = context.getLevel();
        var base = defaultBlockState();
        var flag = level.hasNeighborSignal(pos);
        return base.setValue(POWERED, flag).setValue(LIT, flag != base.getValue(LIT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, POWERED);
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
}
