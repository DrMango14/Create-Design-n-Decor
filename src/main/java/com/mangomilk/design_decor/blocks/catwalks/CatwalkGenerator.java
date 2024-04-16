package com.mangomilk.design_decor.blocks.catwalks;

import com.mangomilk.design_decor.blocks.floodlight.FloodlightBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CatwalkGenerator extends SpecialBlockStateGen {
    public CatwalkGenerator() {
    }

    protected int getXRotation(BlockState state) {
        return 0;
    }

    protected int getYRotation(BlockState state) {
        return 0;
    }

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return (Boolean)state.getValue(CatwalkBlock.DOWN) ? AssetLookup.partialBaseModel(ctx, prov, new String[]{"down"}) : AssetLookup.partialBaseModel(ctx, prov, new String[0]);
    }
}
