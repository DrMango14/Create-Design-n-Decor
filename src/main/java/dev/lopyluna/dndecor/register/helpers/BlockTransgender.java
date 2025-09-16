package dev.lopyluna.dndecor.register.helpers;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.GlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.GlassPaneCTBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.DnDecorUtils;
import dev.lopyluna.dndecor.content.blocks.OrnateGrateBlock;
import dev.lopyluna.dndecor.content.blocks.VelvetBlock;
import dev.lopyluna.dndecor.register.DnDecorTags;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.simibubi.create.AllTags.commonItemTag;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndecor.DnDecor.REG;

@SuppressWarnings({"removal", "unused"})
public class BlockTransgender {

    public static BlockEntry<VelvetBlock> velvetBlock(String colorId, MapColor mapColor, DyeColor dye) {
        return REG.block(colorId + "_velvet_block", p -> new VelvetBlock(p, dye))
                .initialProperties(() -> Blocks.WHITE_WOOL)
                .properties(p -> p.mapColor(mapColor)).properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.5f,1.5f))
                .recipe((c, p) ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 4)
                                .pattern("WB")
                                .pattern("BW")
                                .define('W', DnDecorUtils.getWool(dye))
                                .define('B', commonItemTag("nuggets/brass"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().cubeAll(c.getName(), Create.asResource("block/seat/top_" + colorId))))
                .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                .item()
                .tag(DnDecorTags.modItemTag("dyed_velvet_block"))
                .build()
                .register();
    }

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

    public static <B extends OrnateGrateBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> ornateConnected(
            Supplier<CTSpriteShiftEntry> ct) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.standardModel(c, p)))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> block(Supplier<CTSpriteShiftEntry> ct ) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing
                        (block, ct.get())))
                .item()
                .build();
    }

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> blockV2( Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2 ) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                        .cubeColumn(c.getName(), ct.get()
                                        .getOriginalResourceLocation(),
                                ct2.get()
                                        .getOriginalResourceLocation())))
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(ct.get(), ct2.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())))
                .item()
                .build();
    }

    public static BlockBuilder<WindowBlock, CreateRegistrate> customWindowBlock(String name, Supplier<CTSpriteShiftEntry> ct,
                                                            Supplier<Supplier<RenderType>> renderType, boolean translucent, Supplier<MapColor> color) {
        NonNullFunction<String, ResourceLocation> end_texture = n -> DnDecor.loc(palettesDir() + name + "_end");
        NonNullFunction<String, ResourceLocation> side_texture = n -> DnDecor.loc(palettesDir() + n);
        return windowBlock(name, ct, null, renderType, translucent, end_texture, side_texture, color);
    }

    public static BlockBuilder<WindowBlock, CreateRegistrate> customWindowBlock(String name, Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2,
                    Supplier<Supplier<RenderType>> renderType, boolean translucent, Supplier<MapColor> color) {
        NonNullFunction<String, ResourceLocation> end_texture = n -> DnDecor.loc(palettesDir() + name + "_end");
        NonNullFunction<String, ResourceLocation> side_texture = n -> DnDecor.loc(palettesDir() + n);
        return windowBlock(name, ct, ct2, renderType, translucent, end_texture, side_texture, color);
    }

    public static BlockBuilder<WindowBlock, CreateRegistrate> windowBlock(String name,
                    Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2, Supplier<Supplier<RenderType>> renderType, boolean translucent,
                    NonNullFunction<String, ResourceLocation> endTexture, NonNullFunction<String, ResourceLocation> sideTexture, Supplier<MapColor> color) {
        return REG.block(name, p -> new WindowBlock(p, translucent))
                .onRegister(ct == null ? $ -> {
                } : connectedTextures(() -> ct2 != null ? new HorizontalCTBehaviour(ct.get(), ct2.get()) : new HorizontalCTBehaviour(ct.get())))
                .addLayer(renderType)
                .initialProperties(() -> Blocks.GLASS)
                .properties(BlockTransgender::glassProperties)
                .properties(p -> p.mapColor(color.get()))
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                        .cubeColumn(c.getName(), sideTexture.apply(c.getName()), endTexture.apply(c.getName()))))
                .tag(BlockTags.IMPERMEABLE)
                .simpleItem();
    }

    public static BlockEntry<ConnectedGlassBlock> framedGlass(String name, Supplier<ConnectedTextureBehaviour> behaviour) {
        return REG.block(name, ConnectedGlassBlock::new)
                .onRegister(connectedTextures(behaviour))
                .addLayer(() -> RenderType::cutout)
                .initialProperties(() -> Blocks.GLASS)
                .properties(BlockTransgender::glassProperties)
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .recipe((c, p) -> p.stonecutting(DataIngredient.tag(net.neoforged.neoforge.common.Tags.Items.GLASS_BLOCKS_COLORLESS),
                        RecipeCategory.BUILDING_BLOCKS, c))
                .blockstate((c, p) -> BlockStateGen.cubeAll(c, p, "palettes/", name))
                .tag(net.neoforged.neoforge.common.Tags.Blocks.GLASS_BLOCKS_COLORLESS, BlockTags.IMPERMEABLE)
                .item()
                .tag(net.neoforged.neoforge.common.Tags.Items.GLASS_BLOCKS_COLORLESS)
                .model((c, p) -> p.cubeColumn(c.getName(), p.modLoc(palettesDir() + c.getName()),
                        p.modLoc("block/palettes/" + name)))
                .build()
                .register();
    }

    public static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> customWindowPane(String name, Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift, Supplier<Supplier<RenderType>> renderType) {
        var topTexture = DnDecor.loc(palettesDir() + name + "_pane_top");
        var sideTexture = DnDecor.loc(palettesDir() + name);
        return connectedGlassPane(name, parent, ctshift, sideTexture, sideTexture, topTexture, renderType, false);
    }

    public static BlockEntry<ConnectedGlassPaneBlock> framedGlassPane(String name, Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift) {
        var sideTexture = DnDecor.loc(palettesDir() + name);
        var itemSideTexture = DnDecor.loc(palettesDir() + name);
        var topTexture = DnDecor.loc(palettesDir() + name + "_pane_top");
        Supplier<Supplier<RenderType>> renderType = () -> RenderType::cutoutMipped;
        return connectedGlassPane(name, parent, ctshift, sideTexture, itemSideTexture, topTexture, renderType, true).register();
    }

    private static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> connectedGlassPane(String name, Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift,
        ResourceLocation sideTexture, ResourceLocation itemSideTexture, ResourceLocation topTexture, Supplier<Supplier<RenderType>> renderType, boolean colorless) {
        NonNullConsumer<? super ConnectedGlassPaneBlock> connectedTextures = ctshift == null ? $ -> {
        } : connectedTextures(() -> new GlassPaneCTBehaviour(ctshift.get()));
        String CGPparents = "block/connected_glass_pane/";
        String prefix = name + "_pane_";

        Function<RegistrateBlockstateProvider, ModelFile> post =
                getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
                side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
                sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
                noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
                noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

        NonNullBiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
                (c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p), noSideAlt.apply(p));

        return glassPane(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new, renderType, connectedTextures, stateProvider, colorless);
    }

    private static Function<RegistrateBlockstateProvider, ModelFile> getPaneModelProvider(String CGPparents, String prefix, String partial, ResourceLocation sideTexture, ResourceLocation topTexture) {
        return p -> p.models().withExistingParent(prefix + partial, Create.asResource(CGPparents + partial))
                .texture("pane", sideTexture).texture("edge", topTexture);
    }

    private static <G extends GlassPaneBlock> BlockBuilder<G, CreateRegistrate> glassPane(String name, Supplier<? extends Block> parent, ResourceLocation sideTexture,
        ResourceLocation topTexture, NonNullFunction<BlockBehaviour.Properties, G> factory, Supplier<Supplier<RenderType>> renderType,
        NonNullConsumer<? super G> connectedTextures, NonNullBiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider, boolean colorless) {
        name += "_pane";
        ItemBuilder<BlockItem, BlockBuilder<G, CreateRegistrate>> itemBuilder = REG.block(name, factory)
                .onRegister(connectedTextures)
                .addLayer(renderType)
                .initialProperties(() -> Blocks.GLASS_PANE)
                .properties(p -> p.mapColor(parent.get().defaultMapColor()))
                .blockstate(stateProvider)
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 16)
                            .pattern("###").pattern("###").define('#', parent.get())
                            .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(parent.get())).save(p);
                    if (colorless) p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_PANES_COLORLESS), RecipeCategory.BUILDING_BLOCKS, c);
                })
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .item();
        if (colorless) itemBuilder.tag(Tags.Items.GLASS_PANES, Tags.Items.GLASS_PANES_COLORLESS);
        else itemBuilder.tag(Tags.Items.GLASS_PANES);
        BlockBuilder<G, CreateRegistrate> blockBuilder = itemBuilder.model((c, p) -> p.generated(c, sideTexture)).build();
        if (colorless) blockBuilder.tag(Tags.Blocks.GLASS_PANES, Tags.Blocks.GLASS_PANES_COLORLESS);
        else blockBuilder.tag(Tags.Blocks.GLASS_PANES);
        return blockBuilder;
    }

    private static BlockBehaviour.Properties glassProperties(BlockBehaviour.Properties p) {
        return p.isValidSpawn(BlockTransgender::never)
                .isRedstoneConductor(BlockTransgender::never)
                .isSuffocating(BlockTransgender::never)
                .isViewBlocking(BlockTransgender::never);
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
    private static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static String palettesDir() {
        return "block/palettes/";
    }
}
