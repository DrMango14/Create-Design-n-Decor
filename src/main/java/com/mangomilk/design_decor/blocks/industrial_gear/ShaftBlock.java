package com.mangomilk.design_decor.blocks.industrial_gear;

import com.mangomilk.design_decor.registry.CDDBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ShaftBlock extends AbstractSimpleShaftBlock {
    public ShaftBlock(Properties properties) {
        super(properties);
    }


    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return CDDBlockEntities.BRACKETED_KINETIC.get();
    }
}
