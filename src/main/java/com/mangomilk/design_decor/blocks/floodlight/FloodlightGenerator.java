package com.mangomilk.design_decor.blocks.floodlight;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

import static com.simibubi.create.foundation.data.AssetLookup.partialBaseModel;

public class FloodlightGenerator extends SpecialBlockStateGen {

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return switch (state.getValue(FloodlightBlock.FACING)) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case WEST -> 270;
            case EAST -> 90;
            case DOWN -> 0;
            case UP -> 0;
        };
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
       // return AssetLookup.forPowered(ctx, prov)
       //         .apply(state);

        return state.getValue(FloodlightBlock.TURNED_ON) ? partialBaseModel(ctx, prov, "on")
                : partialBaseModel(ctx, prov);
    }

}