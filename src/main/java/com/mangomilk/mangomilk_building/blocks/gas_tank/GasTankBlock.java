package com.mangomilk.mangomilk_building.blocks.gas_tank;

import com.mangomilk.mangomilk_building.registry.MmbBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.equipment.wrench.IWrenchableWithBracket;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
