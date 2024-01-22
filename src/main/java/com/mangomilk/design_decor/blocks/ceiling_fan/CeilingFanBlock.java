package com.mangomilk.design_decor.blocks.ceiling_fan;

import com.mangomilk.design_decor.registry.CDDBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CeilingFanBlock extends KineticBlock implements IBE<CeilingFanBlockEntity> {
    public CeilingFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face==Direction.UP;
    }
    @Override
    public Class<CeilingFanBlockEntity> getBlockEntityClass() {
        return CeilingFanBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CeilingFanBlockEntity> getBlockEntityType() {
        return CDDBlockEntities.CEILING_FAN.get();
    }
}
