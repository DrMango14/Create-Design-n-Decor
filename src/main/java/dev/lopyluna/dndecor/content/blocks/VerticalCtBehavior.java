package dev.lopyluna.dndecor.content.blocks;


import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class VerticalCtBehavior extends ConnectedTextureBehaviour.Base {
    private CTSpriteShiftEntry shift;

    public VerticalCtBehavior(CTSpriteShiftEntry shift) {
        this.shift = shift;
    }

    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        return this.shift;
    }

    protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        return Direction.UP;
    }

    public boolean buildContextForOccludedDirections() {
        return super.buildContextForOccludedDirections();
    }
}
