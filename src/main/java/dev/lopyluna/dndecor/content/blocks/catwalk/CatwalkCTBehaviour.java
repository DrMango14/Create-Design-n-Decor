package dev.lopyluna.dndecor.content.blocks.catwalk;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import dev.lopyluna.dndecor.content.blocks.BulkCTBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class CatwalkCTBehaviour extends BulkCTBehaviour {
    public CatwalkCTBehaviour(CTSpriteShiftEntry... shifts) {
        super(shifts);
    }

    @Override
    public boolean buildContextForOccludedDirections() {
        return true;
    }

    @Override
    public @Nullable CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        return direction.getAxis().isVertical() ? super.getShift(state, direction, sprite) : null;
    }

    @Override
    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
        if ((otherPos.getY() - pos.getY()) != 0) return false;
        return super.connectsTo(state, other, reader, pos, otherPos, face) &&
                state.getValue(CatwalkBlock.LAYER).equals(other.getValue(CatwalkBlock.LAYER));
    }
}
