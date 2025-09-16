package dev.lopyluna.dndecor.content.blocks.storage_container;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.simibubi.create.api.connectivity.ConnectivityHandler.isConnected;

public class ColoredStorageContainerCTBehaviour extends ConnectedTextureBehaviour.Base {

    @Override
    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        Direction.Axis vaultBlockAxis = ColoredStorageContainerBlock.getVaultBlockAxis(state);
        boolean small = !ColoredStorageContainerBlock.isLarge(state);
        if (vaultBlockAxis == null) return null;
        DyeColor color = state.getValue(ColoredStorageContainerBlock.COLOR);

        if (direction.getAxis() == vaultBlockAxis) return DnDecorSpriteShifts.getColoredStorageFront(color, small);
        if (direction == Direction.UP) return DnDecorSpriteShifts.getColoredStorageTop(color, small);
        if (direction == Direction.DOWN) return DnDecorSpriteShifts.getColoredStorageBottom(color, small);
        return DnDecorSpriteShifts.getColoredStorageSide(color, small);
    }

    @Override
    protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        Direction.Axis vaultBlockAxis = ColoredStorageContainerBlock.getVaultBlockAxis(state);
        boolean alongX = vaultBlockAxis == Direction.Axis.X;
        if (face.getAxis().isVertical() && alongX) return super.getUpDirection(reader, pos, state, face).getClockWise();
        if (face.getAxis() == vaultBlockAxis || face.getAxis().isVertical()) return super.getUpDirection(reader, pos, state, face);
        assert vaultBlockAxis != null;
        return Direction.fromAxisAndDirection(vaultBlockAxis, alongX ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);
    }

    @Override
    protected Direction getRightDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        Direction.Axis vaultBlockAxis = ColoredStorageContainerBlock.getVaultBlockAxis(state);
        if (face.getAxis().isVertical() && vaultBlockAxis == Direction.Axis.X) return super.getRightDirection(reader, pos, state, face).getClockWise();
        if (face.getAxis() == vaultBlockAxis || face.getAxis().isVertical()) return super.getRightDirection(reader, pos, state, face);
        return Direction.fromAxisAndDirection(Direction.Axis.Y, face.getAxisDirection());
    }

    public boolean buildContextForOccludedDirections() {
        return super.buildContextForOccludedDirections();
    }

    @Override
    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos,
                              BlockPos otherPos, Direction face) {
        return state == other && isConnected(reader, pos, otherPos);
    }
}