package com.mangomilk.design_decor.blocks.millstone.instance;

import com.mangomilk.design_decor.base.DecoPartialModels;
import com.mangomilk.design_decor.blocks.millstone.DecoMillStoneBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

public class CrimsiteMillStoneCogInstance extends SingleRotatingInstance<DecoMillStoneBlockEntity> {

    public CrimsiteMillStoneCogInstance(MaterialManager materialManager, DecoMillStoneBlockEntity blockEntity) {super(materialManager, blockEntity);}

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(DecoPartialModels.CRIMSITE_MILLSTONE_COG, blockEntity.getBlockState());
    }
}
