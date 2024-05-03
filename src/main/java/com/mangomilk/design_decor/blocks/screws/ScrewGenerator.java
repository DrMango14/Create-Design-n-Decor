package com.mangomilk.design_decor.blocks.screws;

import com.mangomilk.design_decor.blocks.floodlight.FloodlightBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ScrewGenerator extends SpecialBlockStateGen {
    public ScrewGenerator() {
    }

    protected int getXRotation(BlockState state) {
        short value;
        switch ((Direction)state.getValue(FloodlightBlock.FACING)) {
            case NORTH:
                value = 90;
                break;
            case SOUTH:
                value = 90;
                break;
            case WEST:
                value = 90;
                break;
            case EAST:
                value = 90;
                break;
            case DOWN:
                value = 180;
                break;
            case UP:
                value = 0;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return value;
    }

    protected int getYRotation(BlockState state) {
        short value;
        switch ((Direction)state.getValue(FloodlightBlock.FACING)) {
            case NORTH:
                value = 0;
                break;
            case SOUTH:
                value = 180;
                break;
            case WEST:
                value = 270;
                break;
            case EAST:
                value = 90;
                break;
            case DOWN:
                value = 0;
                break;
            case UP:
                value = 0;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        return value;
    }


    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return (Boolean)state.getValue(ScrewBlock.ROTATED) ? AssetLookup.partialBaseModel(ctx, prov, new String[]{"rotated"}) : AssetLookup.partialBaseModel(ctx, prov, new String[0]);
    }
}
