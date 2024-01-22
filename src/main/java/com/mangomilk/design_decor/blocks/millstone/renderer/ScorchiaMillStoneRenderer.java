package com.mangomilk.design_decor.blocks.millstone.renderer;

import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ScorchiaMillStoneRenderer extends MillstoneRenderer {

    public ScorchiaMillStoneRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(MillstoneBlockEntity be, BlockState state) {
        return CachedBufferer.partial(CDDPartialModels.SCORCHIA_MILLSTONE_COG, state);
    }
}
