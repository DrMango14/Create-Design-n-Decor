package com.mangomilk.design_decor.blocks.glass;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class ConnectedTintedGlassBlock extends TintedGlassBlock {
    public ConnectedTintedGlassBlock(Properties p_154822_) {
        super(p_154822_);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() instanceof ConnectedTintedGlassBlock || super.skipRendering(state, adjacentBlockState, side);
    }

    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
