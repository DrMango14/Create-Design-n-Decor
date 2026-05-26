package dev.lopyluna.dndecor.content.blocks.boiler;

import com.simibubi.create.foundation.blockEntity.CachedRenderBBBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BoilerBlockEntity extends CachedRenderBBBlockEntity {
    public BoilerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }
}