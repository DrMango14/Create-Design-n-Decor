package dev.lopyluna.dndecor;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.lopyluna.dndecor.content.blocks.cogs.DnDCogWheelBlock;
import dev.lopyluna.dndecor.content.blocks.metal_supports.MetalSupportBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;


import static dev.lopyluna.dndecor.register.DnDecorBlocks.getBlockModel;

public class DnDecorBlockStateGen {

    public static NonNullBiConsumer<DataGenContext<Block, DnDCogWheelBlock>, RegistrateBlockstateProvider> cogwheelBlockState(boolean large){
        return (c, p) -> {

            String location = large ? "block/large_cogwheels/" : "block/cogwheels/";

            p.models().withExistingParent("block/" + c.getName() + "/block_shaftless", Create.asResource(large ? "block/large_cogwheel_shaftless":"block/cogwheel_shaftless"))
                    .texture(large ? "4":"1_2", DnDecor.asResource(location + c.getName()))
                    .texture("particle", DnDecor.asResource(location + c.getName()));
            p.models().withExistingParent("block/" + c.getName() + "/block", ResourceLocation.withDefaultNamespace("air"))
                    .texture("particle", DnDecor.asResource(location + c.getName()));;
            p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource(large ? "block/large_cogwheel" : "block/cogwheel"))
                    .texture(large ? "4":"1_2", DnDecor.asResource(location + c.getName()))
                    .texture("particle", DnDecor.asResource(location + c.getName()));

            BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
        };
    }

    public static NonNullBiConsumer<DataGenContext<Block, MetalSupportBlock>, RegistrateBlockstateProvider> metalSupportBlockState(){
        return (c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
            Direction.Axis axis = s.getValue(MetalSupportBlock.HORIZONTAL_AXIS);
            boolean top = s.getValue(MetalSupportBlock.TOP);


            ModelFile topModel = AssetLookup.partialBaseModel(c,p,"top");
            ModelFile bottomModel = AssetLookup.partialBaseModel(c,p,"bottom");

             return ConfiguredModel.builder()
                    .modelFile(top?topModel:bottomModel)
                    .rotationY(axis == Direction.Axis.Z ? 0 : 90)
                    .build();
        });
    }




}
