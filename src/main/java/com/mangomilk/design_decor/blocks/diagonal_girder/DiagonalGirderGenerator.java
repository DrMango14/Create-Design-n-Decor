package com.mangomilk.design_decor.blocks.diagonal_girder;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.simibubi.create.foundation.data.AssetLookup.partialBaseModel;

public class DiagonalGirderGenerator extends SpecialBlockStateGen {

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return switch (state.getValue(DiagonalGirderBlock.FACING)) {
            case NORTH -> 270;
            case SOUTH -> 90;
            case WEST -> 180;
            case EAST -> 0;
            case DOWN -> 0;
            case UP -> 0;
        };
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
												BlockState state) {
       // return AssetLookup.forPowered(ctx, prov)
       //         .apply(state);

        return state.getValue(DiagonalGirderBlock.FACING_UP) ? partialBaseModel(ctx, prov, "up")
                : partialBaseModel(ctx, prov);
    }

}
