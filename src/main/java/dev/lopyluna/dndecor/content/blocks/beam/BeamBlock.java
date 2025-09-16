package dev.lopyluna.dndecor.content.blocks.beam;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class BeamBlock extends Block implements IWrenchable {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<BeamStates> BEAM = EnumProperty.create("beam", BeamStates.class);

    public BeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(BEAM, BeamStates.BOTH));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var face = context.getClickedFace();
        var beam = state.getValue(BEAM);
        if (face == Direction.UP) {
            if (beam == BeamStates.TOP) beam = BeamStates.BOTH;
            else beam = BeamStates.TOP;
        }
        if (face == Direction.DOWN) {
            if (beam == BeamStates.BOTTOM) beam = BeamStates.BOTH;
            else beam = BeamStates.BOTTOM;
        }
        KineticBlockEntity.switchToBlockState(level, pos, state.setValue(BEAM, beam));
        if (level.getBlockState(pos) != state) AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, BEAM);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        var stateForPlacement = super.getStateForPlacement(pContext);
        var face = pContext.getClickedFace();
        var direction = face.getAxis().isHorizontal() ? face.getAxis() : pContext.getHorizontalDirection().getAxis();
        if (stateForPlacement != null) stateForPlacement = stateForPlacement.setValue(AXIS, direction);
        return stateForPlacement;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        Direction.Axis axis = state.getValue(AXIS);
        return state.setValue(AXIS, rot.rotate(Direction.get(Direction.AxisDirection.POSITIVE, axis)).getAxis());
    }

    public enum BeamStates implements StringRepresentable {
        TOP,
        BOTTOM,
        BOTH;

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase();
        }
    }
}
