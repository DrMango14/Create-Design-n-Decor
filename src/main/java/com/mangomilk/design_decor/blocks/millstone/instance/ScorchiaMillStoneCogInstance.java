package com.mangomilk.design_decor.blocks.millstone.instance;

import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mangomilk.design_decor.blocks.millstone.DecoMillStoneBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

public class ScorchiaMillStoneCogInstance extends SingleRotatingInstance<DecoMillStoneBlockEntity> {

    public ScorchiaMillStoneCogInstance(MaterialManager materialManager, DecoMillStoneBlockEntity blockEntity) {super(materialManager, blockEntity);}

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(CDDPartialModels.SCORCHIA_MILLSTONE_COG, blockEntity.getBlockState());
    }
}
