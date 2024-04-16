package com.mangomilk.design_decor.blocks.breaker_switch;

import com.mangomilk.design_decor.blocks.floodlight.FloodlightBlock;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

public class LeverGenerator extends SpecialBlockStateGen {

    @Override
    protected int getXRotation(BlockState state) {

        if(state.getValue(LeverBlock.FACE) == AttachFace.WALL)
            return 90;
        if(state.getValue(LeverBlock.FACE) == AttachFace.CEILING)
            return 180;


        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return horizontalAngle(state.getValue(LeverBlock.FACING).getOpposite());
    }

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return (Boolean)state.getValue(LeverBlock.POWERED) ? AssetLookup.partialBaseModel(ctx, prov, new String[]{"powered"}) : AssetLookup.partialBaseModel(ctx, prov, new String[0]);
    }

}
