package com.mangomilk.design_decor.blocks.breaker_switch;

import com.mangomilk.design_decor.registry.CDDShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BreakerSwitchBlock extends LeverBlock {
    public BreakerSwitchBlock(Properties p_54633_) {
        super(p_54633_);
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        AttachFace face = state.getValue(FACE);
        Direction direction = state.getValue(FACING);
        return face == AttachFace.CEILING ? CDDShapes.BREAKER_SWITCH_CEILING.get(direction.getAxis())
                : face == AttachFace.FLOOR ? CDDShapes.BREAKER_SWITCH.get(direction.getAxis())
                : CDDShapes.BREAKER_SWITCH_WALL.get(direction);
    }
}
