package dev.lopyluna.dndecor.content.blocks.container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DyedContainerCTBehaviour extends ConnectedTextureBehaviour.Base {
    @Override
    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        var color = DyedContainerBlock.getColor(state);
        var axis = DyedContainerBlock.getBlockAxisNon(state);
        var solidColor = DyedContainerBlock.isSolidColor(state);
        var small = !DyedContainerBlock.isLargeNon(state);

        if (direction.getAxis() == axis) return DnDecorSpriteShifts.getColoredStorageFront(color, small, solidColor);
        if (direction == Direction.UP) return DnDecorSpriteShifts.getColoredStorageTop(color, small, solidColor);
        if (direction == Direction.DOWN) return DnDecorSpriteShifts.getColoredStorageBottom(color, small, solidColor);
        return DnDecorSpriteShifts.getColoredStorageSide(color, small, solidColor);
    }

    @Override
    protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        var axis = DyedContainerBlock.getBlockAxisNon(state);
        var alongX = axis == Direction.Axis.X;
        if (face.getAxis().isVertical() && alongX) return super.getUpDirection(reader, pos, state, face).getClockWise();
        if (face.getAxis() == axis || face.getAxis().isVertical()) return super.getUpDirection(reader, pos, state, face);
        return Direction.fromAxisAndDirection(axis, alongX ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);
    }

    @Override
    protected Direction getRightDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        var axis = DyedContainerBlock.getBlockAxisNon(state);
        if (face.getAxis().isVertical() && axis == Direction.Axis.X) return super.getRightDirection(reader, pos, state, face).getClockWise();
        if (face.getAxis() == axis || face.getAxis().isVertical()) return super.getRightDirection(reader, pos, state, face);
        return Direction.fromAxisAndDirection(Direction.Axis.Y, face.getAxisDirection());
    }

    public boolean buildContextForOccludedDirections() {
        return super.buildContextForOccludedDirections();
    }

    @Override
    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
        return state.getBlock() == other.getBlock() && ConnectivityHandler.isConnected(reader, pos, otherPos);
    }

}