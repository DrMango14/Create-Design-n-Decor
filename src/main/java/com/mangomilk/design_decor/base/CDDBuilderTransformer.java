package com.mangomilk.design_decor.base;

import com.mangomilk.design_decor.DesignDecor;
import com.mangomilk.design_decor.blocks.OrnateGrateBlock;
import com.mangomilk.design_decor.blocks.glass.ConnectedTintedGlassBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static com.mangomilk.design_decor.registry.CDDBlocks.DecoTags.*;
import static com.mangomilk.design_decor.registry.CDDBlocks.DecoTags.CreateItemTag;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CDDBuilderTransformer {

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> connected(
            Supplier<CTSpriteShiftEntry> ct) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> layeredConnected(
            Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                        .cubeColumn(c.getName(), ct.get()
                                        .getOriginalResourceLocation(),
                                ct2.get()
                                        .getOriginalResourceLocation())))
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(ct.get(), ct2.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }

    public static <B extends OrnateGrateBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> ornateconnected(
            Supplier<CTSpriteShiftEntry> ct) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.standardModel(c, p)))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }

    private static BlockBehaviour.Properties glassProperties(BlockBehaviour.Properties p) {
        return p.isValidSpawn(CDDBuilderTransformer::never)
                .isRedstoneConductor(CDDBuilderTransformer::never)
                .isSuffocating(CDDBuilderTransformer::never)
                .isViewBlocking(CDDBuilderTransformer::never)
                .noOcclusion();
    }

    public static BlockEntry<ConnectedTintedGlassBlock> tintedframedGlass(Supplier<ConnectedTextureBehaviour> behaviour) {
        return DesignDecor.REGISTRATE.block("tinted_framed_glass", ConnectedTintedGlassBlock::new)
                .onRegister(connectedTextures(behaviour))
                .addLayer(() -> RenderType::translucent)
                .initialProperties(() -> Blocks.TINTED_GLASS)
                .properties(CDDBuilderTransformer::glassProperties)
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_TINTED), RecipeCategory.DECORATIONS, c))
                .blockstate((c, p) -> BlockStateGen.cubeAll(c, p, "palettes/", "tinted_framed_glass"))
                .tag(Tags.Blocks.GLASS_TINTED, Tags.Blocks.GLASS, BlockTags.IMPERMEABLE)
                .lang("Tinted Framed Glass")
                .item()
                .tag(Tags.Items.GLASS_TINTED, Tags.Items.GLASS)
                .model((c, p) -> p.cubeColumn(c.getName(), p.modLoc(palettesDir() + c.getName()),
                        p.modLoc("block/palettes/tinted_framed_glass")))
                .build()
                .register();
    }

    public static BlockEntry<ConnectedTintedGlassBlock> tintedframedGlass(String type,String name, Supplier<ConnectedTextureBehaviour> behaviour) {
        return DesignDecor.REGISTRATE.block(type + "_tinted_framed_glass", ConnectedTintedGlassBlock::new)
                .onRegister(connectedTextures(behaviour))
                .addLayer(() -> RenderType::translucent)
                .initialProperties(() -> Blocks.TINTED_GLASS)
                .properties(CDDBuilderTransformer::glassProperties)
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_TINTED), RecipeCategory.DECORATIONS, c))
                .blockstate((c, p) -> BlockStateGen.cubeAll(c, p, "palettes/",  type + "_tinted_framed_glass"))
                .tag(Tags.Blocks.GLASS_TINTED, Tags.Blocks.GLASS, BlockTags.IMPERMEABLE)
                .lang(name + " Tinted Framed Glass")
                .item()
                .tag(Tags.Items.GLASS_TINTED, Tags.Items.GLASS)
                .model((c, p) -> p.cubeColumn(c.getName(), p.modLoc(palettesDir() + c.getName()),
                        p.modLoc("block/palettes/" + type + "_tinted_framed_glass")))
                .build()
                .register();
    }




    public static BlockEntry<Block> CastelBricks(String id, String lang, MapColor mapColor, Block block) {
        DesignDecor.REGISTRATE.block(id + "_castel_brick_stairs", p -> new StairBlock(block.defaultBlockState(), p))
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("stairs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.stairsBlock(c.get(), DesignDecor.asResource("block/stairs/" + id + "_castel_bricks")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("stairs"))
                .build()
                .lang(lang + " Castel Brick Stairs")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_brick_slab", SlabBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("slabs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 2))
                .blockstate((c, p) -> p.slabBlock(c.get(), DesignDecor.asResource("block/slabs/" + id + "_castel_bricks"), DesignDecor.asResource("block/" + id + "_castel_bricks")))
                .item()
                .tag(MCItemTag("slabs"))
                .build()
                .lang(lang + " Castel Brick Slab")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_brick_wall", WallBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("walls"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.wallBlock(c.get(), DesignDecor.asResource("block/walls/" + id + "_castel_bricks")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("walls"))
                .build()
                .lang(lang + " Castel Brick Wall")
                .register();

        return DesignDecor.REGISTRATE.block(id + "_castel_bricks", Block::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .build()
                .lang(lang + " Castel Bricks")
                .register();
    }

    public static BlockEntry<Block> CastelBricks(String id, String lang, Block block) {
        DesignDecor.REGISTRATE.block(id + "_castel_brick_stairs", p -> new StairBlock(block.defaultBlockState(), p))
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("stairs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.stairsBlock(c.get(), DesignDecor.asResource("block/stairs/" + id + "_castel_bricks")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("stairs"))
                .build()
                .lang(lang + " Castel Brick Stairs")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_brick_slab", SlabBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("slabs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 2))
                .blockstate((c, p) -> p.slabBlock(c.get(), DesignDecor.asResource("block/slabs/" + id + "_castel_bricks"), DesignDecor.asResource("block/" + id + "_castel_bricks")))
                .item()
                .tag(MCItemTag("slabs"))
                .build()
                .lang(lang + " Castel Brick Slab")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_brick_wall", WallBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("walls"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.wallBlock(c.get(), DesignDecor.asResource("block/walls/" + id + "_castel_bricks")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("walls"))
                .build()
                .lang(lang + " Castel Brick Wall")
                .register();

        return DesignDecor.REGISTRATE.block(id + "_castel_bricks", Block::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .build()
                .lang(lang + " Castel Bricks")
                .register();
    }
    public static BlockEntry<Block> CastelTiles(String id, String lang, MapColor mapColor, Block block) {
        DesignDecor.REGISTRATE.block(id + "_castel_tile_stairs", p -> new StairBlock(block.defaultBlockState(), p))
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("stairs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.stairsBlock(c.get(), DesignDecor.asResource("block/stairs/" + id + "_castel_tiles")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("stairs"))
                .build()
                .lang(lang + " Castel Tile Stairs")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_tile_slab", SlabBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("slabs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 2))
                .blockstate((c, p) -> p.slabBlock(c.get(), DesignDecor.asResource("block/slabs/" + id + "_castel_tiles"), DesignDecor.asResource("block/" + id + "_castel_tiles")))
                .item()
                .tag(MCItemTag("slabs"))
                .build()
                .lang(lang + " Castel Tile Slab")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_tile_wall", WallBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("walls"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.wallBlock(c.get(), DesignDecor.asResource("block/walls/" + id + "_castel_tiles")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("walls"))
                .build()
                .lang(lang + " Castel Tile Wall")
                .register();

        return DesignDecor.REGISTRATE.block(id + "_castel_tiles", Block::new)
                .initialProperties(() -> block)
                .properties(p -> p.mapColor(mapColor))
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .build()
                .lang(lang + " Castel Tiles")
                .register();
    }
    public static BlockEntry<Block> CastelTiles(String id, String lang, Block block) {
        DesignDecor.REGISTRATE.block(id + "_castel_tile_stairs", p -> new StairBlock(block.defaultBlockState(), p))
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("stairs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.stairsBlock(c.get(), DesignDecor.asResource("block/stairs/" + id + "_castel_tiles")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("stairs"))
                .build()
                .lang(lang + " Castel Tile Stairs")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_tile_slab", SlabBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("slabs"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 2))
                .blockstate((c, p) -> p.slabBlock(c.get(), DesignDecor.asResource("block/slabs/" + id + "_castel_tiles"), DesignDecor.asResource("block/" + id + "_castel_tiles")))
                .item()
                .tag(MCItemTag("slabs"))
                .build()
                .lang(lang + " Castel Tile Slab")
                .register();

        DesignDecor.REGISTRATE.block(id + "_castel_tile_wall", WallBlock::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .tag(MCBlockTag("walls"))
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .blockstate((c, p) -> p.wallBlock(c.get(), DesignDecor.asResource("block/walls/" + id + "_castel_tiles")))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .tag(MCItemTag("walls"))
                .build()
                .lang(lang + " Castel Tile Wall")
                .register();

        return DesignDecor.REGISTRATE.block(id + "_castel_tiles", Block::new)
                .initialProperties(() -> block)
                .properties(p -> p.destroyTime(1.25f))
                .transform(pickaxeOnly())
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(CreateItemTag("stone_types/" + id)), RecipeCategory.BUILDING_BLOCKS, c, 1))
                .item()
                .tag(CreateItemTag("stone_types/" + id))
                .build()
                .lang(lang + " Castel Tiles")
                .register();
    }
    private static boolean never(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {return false;}
    private static Boolean never(BlockState p_235427_0_, BlockGetter p_235427_1_, BlockPos p_235427_2_, EntityType<?> p_235427_3_) {return false;}
    private static String palettesDir() {return "block/palettes/";}
}
