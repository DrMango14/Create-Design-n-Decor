package com.mangomilk.design_decor.blocks;

import com.mangomilk.design_decor.registry.CDDShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodSupportBlock extends Block {
    public WoodSupportBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return CDDShapes.WOODEN_SUPPORT;
    }
}
