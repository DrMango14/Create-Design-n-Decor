package com.mangomilk.design_decor.blocks.millstone.renderer;

import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class GraniteMillStoneRenderer extends MillstoneRenderer {

    public GraniteMillStoneRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(MillstoneBlockEntity be, BlockState state) {
        return CachedBufferer.partial(CDDPartialModels.GRANITE_MILLSTONE_COG, state);
    }
}
