package com.mangomilk.design_decor.blocks.millstone.instance;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.mangomilk.design_decor.base.DecoPartialModels;
import com.mangomilk.design_decor.blocks.millstone.DecoMillStoneBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;

public class AsurineMillStoneCogInstance extends SingleRotatingInstance<DecoMillStoneBlockEntity> {

    public AsurineMillStoneCogInstance(MaterialManager materialManager, DecoMillStoneBlockEntity blockEntity) {super(materialManager, blockEntity);}

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(DecoPartialModels.ASURINE_MILLSTONE_COG, blockEntity.getBlockState());
    }
}
