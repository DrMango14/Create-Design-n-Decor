package dev.lopyluna.dndecor.content.blocks.metal_supports;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DiagonalMetalSupportCtBehavior extends ConnectedTextureBehaviour.Base {
    private CTSpriteShiftEntry shift;

    public DiagonalMetalSupportCtBehavior(CTSpriteShiftEntry shift) {
        this.shift = shift;
    }

    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        return direction == Direction.UP ? this.shift : null;
    }

    protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        return Direction.UP;
    }

    protected Direction getRightDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        return ((Direction) state.getValue(HorizontalDirectionalBlock.FACING)).getCounterClockWise();
    }

    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
//
        if (pos.getY() != otherPos.getY())
            return false;
       // } else {
            if(!(other.getBlock() instanceof DiagonalMetalSupportBlock))
                return false;
            return state.getValue(HorizontalDirectionalBlock.FACING) == other.getValue(HorizontalDirectionalBlock.FACING);

        //}
    }

     public boolean buildContextForOccludedDirections() {
        return super.buildContextForOccludedDirections();
    }
}
