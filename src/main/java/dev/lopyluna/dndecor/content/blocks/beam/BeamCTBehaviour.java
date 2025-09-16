package dev.lopyluna.dndecor.content.blocks.beam;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BeamCTBehaviour extends ConnectedTextureBehaviour.Base {

    protected CTSpriteShiftEntry topZShift;
    protected CTSpriteShiftEntry topXShift;
    protected CTSpriteShiftEntry layerShift;

    public BeamCTBehaviour(CTSpriteShiftEntry layerShift, CTSpriteShiftEntry topZShift, CTSpriteShiftEntry topXShift) {
        this.layerShift = layerShift;
        this.topZShift = topZShift;
        this.topXShift = topXShift;
    }

    @Override
    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        return direction.getAxis().isHorizontal() ? layerShift : state.getValue(BeamBlock.AXIS) == Direction.Axis.X ? topXShift : topZShift;
    }

    @Override
    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
        return super.connectsTo(state, other, reader, pos, otherPos, face) && sameBeam(state, other) && sameAxis(state, other);
    }

    public boolean sameAxis(BlockState state, BlockState other) {
        if (!state.hasProperty(BeamBlock.AXIS) || !other.hasProperty(BeamBlock.AXIS)) return false;
        return state.getValue(BeamBlock.AXIS) == other.getValue(BeamBlock.AXIS);
    }
    public boolean sameBeam(BlockState state, BlockState other) {
        if (!state.hasProperty(BeamBlock.BEAM) || !other.hasProperty(BeamBlock.BEAM)) return false;
        return state.getValue(BeamBlock.BEAM) == other.getValue(BeamBlock.BEAM);
    }
}