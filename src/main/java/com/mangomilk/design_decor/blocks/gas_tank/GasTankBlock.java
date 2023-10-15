package com.mangomilk.design_decor.blocks.gas_tank;

import com.mangomilk.design_decor.registry.MmbBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class GasTankBlock extends Block implements IWrenchable, IBE<GasTankBlockEntity> {
    public GasTankBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public Class<GasTankBlockEntity> getBlockEntityClass() {
        return GasTankBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GasTankBlockEntity> getBlockEntityType() {
        return MmbBlockEntities.GAS_TANK.get();
    }
}
