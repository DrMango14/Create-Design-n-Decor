package dev.lopyluna.dndecor.content.entries;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.blocks.bolt.BoltBlock;
import dev.lopyluna.dndecor.register.AllMetalTypes;
import dev.lopyluna.dndecor.register.DnDecorTags;
import dev.lopyluna.dndecor.register.helpers.MetalTypeHelper;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

import java.util.function.Function;

import static dev.lopyluna.dndecor.DnDecor.REG;
import static dev.lopyluna.dndecor.content.blocks.bolt.BoltBlock.FACING;
import static dev.lopyluna.dndecor.content.blocks.bolt.BoltBlock.ROT;

@SuppressWarnings("unused")
public class BoltEntry<T extends Block> {
    public BlockEntry<BoltBlock> CROSS;
    public BlockEntry<BoltBlock> DASH;
    public BlockEntry<BoltBlock> DOT;
    public BlockEntry<BoltBlock> FLAT;

    public boolean is(Block entry) {
        return CROSS.get() == entry || DASH.get() == entry || DOT.get() == entry || FLAT.get() == entry;
    }

    public BlockBuilder<BoltBlock, CreateRegistrate> buildBolts(BlockBuilder<BoltBlock, CreateRegistrate> builder, MaterialTypeProvider.MetalType metal, MapColor color, String type, String boltType) {
        var build = builder.initialProperties(SharedProperties::netheriteMetal).properties(p -> p.mapColor(color))
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(boltStates(c, p, type, boltType)));
        if (metal.equals(AllMetalTypes.NETHERITE)) build = build.item().properties(Item.Properties::fireResistant).transform(b -> b
                .model((c, p) -> p.blockItem(() -> c.getEntry().getBlock(), "/block_0")).build());
        else build = build.item().transform(b -> b
                .model((c, p) -> p.blockItem(() -> c.getEntry().getBlock(), "/block_0")).build());
        return build;
    }

    public BoltEntry(MaterialTypeProvider.MetalType metal) {
        String type = metal.id;
        MapColor mapColor = metal.color;

        var builderCross = REG.block(type + "_cross_bolt", BoltBlock::new);
        builderCross = buildBolts(builderCross, metal, mapColor, type, "cross");
        var builderDash = REG.block(type + "_dash_bolt", BoltBlock::new);
        builderDash = buildBolts(builderDash, metal, mapColor, type, "dash");
        var builderDot = REG.block(type + "_dot_bolt", BoltBlock::new);
        builderDot = buildBolts(builderDot, metal, mapColor, type, "dot");
        var builderFlat = REG.block(type + "_flat_bolt", BoltBlock::new);
        builderFlat = buildBolts(builderFlat, metal, mapColor, type, "flat");

        builderCross = builderCross.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });
        builderDash = builderDash.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });
        builderDot = builderDot.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });
        builderFlat = builderFlat.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });

        var boltMineable = DnDecorTags.modBlockTag("mineable/pickaxe/" + type + "_bolt");
        MetalTypeHelper.create("pickaxe", boltMineable);
        builderCross = builderCross.tag(boltMineable);
        builderDash = builderDash.tag(boltMineable);
        builderDot = builderDot.tag(boltMineable);
        builderFlat = builderFlat.tag(boltMineable);

        CROSS = builderCross.register();
        DASH = builderDash.register();
        DOT = builderDot.register();
        FLAT = builderFlat.register();
    }

    public static <T extends Block> Function<BlockState, ConfiguredModel[]> boltStates(DataGenContext<Block, T> c, RegistrateBlockstateProvider p, String material, String type) {
        return state -> {
            var facing = state.getValue(FACING);
            var getX = switch (facing) { case DOWN -> 180; case UP -> 0; case NORTH, SOUTH, WEST, EAST -> 90; };
            var getY = switch (facing) { case DOWN, UP, NORTH -> 0; case SOUTH -> 180; case WEST -> 270; case EAST -> 90; };
            var rot = state.getValue(ROT);
            return ConfiguredModel.builder().modelFile(p.models()
                            .withExistingParent("block/" + c.getName() + "/block_" + rot.getName(),  DnDecor.loc("block/bolt_base/"+type+"/bolt_" + rot.getName()))
                            .texture("0", DnDecor.loc("block/"+material+"_bolt")).texture("particle", DnDecor.loc("block/"+material+"_bolt")))
                    .rotationY(getY)
                    .rotationX(getX)
                    .build();
        };
    }
}
