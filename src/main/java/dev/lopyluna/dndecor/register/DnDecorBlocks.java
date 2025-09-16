package dev.lopyluna.dndecor.register;

import com.simibubi.create.*;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.block.ItemUseOverrides;
import com.simibubi.create.foundation.block.connected.*;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.DnDecorBlockStateGen;
import dev.lopyluna.dndecor.content.blocks.*;
import dev.lopyluna.dndecor.content.blocks.beam.BeamBlock;
import dev.lopyluna.dndecor.content.blocks.beam.BeamCTBehaviour;
import dev.lopyluna.dndecor.content.blocks.cogs.DnDCogWheelBlock;
import dev.lopyluna.dndecor.content.blocks.cogs.DnDCogwheelBlockItem;
import dev.lopyluna.dndecor.content.blocks.diagonal_girder.DiagonalGirderBlock;
import dev.lopyluna.dndecor.content.blocks.diagonal_girder.DiagonalGirderGenerator;
import dev.lopyluna.dndecor.content.blocks.flywheel.FlywheelTypeBlock;
import dev.lopyluna.dndecor.content.blocks.frontlight.Frontlight;
import dev.lopyluna.dndecor.content.blocks.frontlight.FrontlightBlock;
import dev.lopyluna.dndecor.content.blocks.full_belt.FullBeltBlock;
import dev.lopyluna.dndecor.content.blocks.full_belt.FullBeltGenerator;
import dev.lopyluna.dndecor.content.blocks.metal_supports.DiagonalMetalSupportBlock;
import dev.lopyluna.dndecor.content.blocks.metal_supports.DiagonalMetalSupportCtBehavior;
import dev.lopyluna.dndecor.content.blocks.metal_supports.MetalSupportBlock;
import dev.lopyluna.dndecor.content.blocks.stepped_lever.SteppedLeverBlock;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerBlock;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerCTBehaviour;
import dev.lopyluna.dndecor.content.configs.server.kinetics.DStress;
import dev.lopyluna.dndecor.content.entries.BoltEntry;
import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import dev.lopyluna.dndecor.register.helpers.list_providers.MetalTypeBlockList;
import dev.lopyluna.dndecor.register.helpers.list_providers.MetalTypeBoltBlockList;
import dev.lopyluna.dndecor.register.helpers.list_providers.StoneTypeBlockList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;

import static com.simibubi.create.AllTags.commonItemTag;
import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.display.DisplayTarget.displayTarget;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndecor.DnDecor.REG;
import static dev.lopyluna.dndecor.register.DnDecorTags.optionalTag;
import static dev.lopyluna.dndecor.register.helpers.BlockTransgender.*;

@SuppressWarnings({"removal", "deprecation", "SameParameterValue", "unused"})
public class DnDecorBlocks {

    public static TagKey<Item> darkMetalDecorTag = optionalTag(BuiltInRegistries.ITEM, DnDecor.loc("dark_metal_decor"));

    public static final BlockEntry<FullBeltBlock> BELT = REG.block("belt", FullBeltBlock::new)
            .properties(p -> p.sound(SoundType.WOOL).strength(0.8f).mapColor(MapColor.COLOR_GRAY))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> {
                p.models().withExistingParent("block/belt/diagonal_end", Create.asResource("block/belt/diagonal_end"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));
                p.models().withExistingParent("block/belt/diagonal_middle", Create.asResource("block/belt/diagonal_middle"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));
                p.models().withExistingParent("block/belt/diagonal_start", Create.asResource("block/belt/diagonal_start"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));

                p.models().withExistingParent("block/belt/end", Create.asResource("block/belt/end")).texture("0", DnDecor.loc("block/belt"));
                p.models().withExistingParent("block/belt/middle", Create.asResource("block/belt/middle")).texture("0", DnDecor.loc("block/belt"));
                p.models().withExistingParent("block/belt/start", Create.asResource("block/belt/start")).texture("0", DnDecor.loc("block/belt"));

                p.models().withExistingParent("block/belt/end_bottom", Create.asResource("block/belt/end_bottom")).texture("1", DnDecor.loc("block/belt_offset"));
                p.models().withExistingParent("block/belt/middle_bottom", Create.asResource("block/belt/middle_bottom")).texture("1", DnDecor.loc("block/belt_offset"));
                p.models().withExistingParent("block/belt/start_bottom", Create.asResource("block/belt/start_bottom")).texture("1", DnDecor.loc("block/belt_offset"));


                p.models().withExistingParent("block/belt/particle", Create.asResource("block/belt/particle")).texture("particle", DnDecor.loc("block/belt"));


                new FullBeltGenerator().generate(c, p);
            })
            .transform(DStress.setNoImpact())
            .transform(displaySource(AllDisplaySources.ITEM_NAMES))
            .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
            .clientExtension(() -> BeltBlock.RenderProperties::new)
            .register();

    public static final BlockEntry<SteppedLeverBlock> STEPPED_LEVER = REG.block("stepped_lever", SteppedLeverBlock::new)
            .initialProperties(() -> Blocks.LEVER)
            .transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
            .recipe((c, p) ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                            .pattern(" L ")
                            .pattern(" B ")
                            .pattern(" R ")
                            .define('L', Items.LEVER)
                            .define('R', Items.REDSTONE)
                            .define('B', DnDecorTags.commonItemTag("plates/brass"))
                            .unlockedBy("has_" + c.getName(), has(c.get()))
                            .save(p, DnDecor.loc("crafting/" + c.getName()))
            )
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.horizontalFaceBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .onRegister(ItemUseOverrides::addBlock)
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<MetalSupportBlock> METAL_SUPPORT = REG.block("metal_support", MetalSupportBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .blockstate(DnDecorBlockStateGen.metalSupportBlockState())
            .onRegister(CreateRegistrate.connectedTextures(() -> new VerticalCtBehavior(DnDecorSpriteShifts.METAL_SUPPORT)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DiagonalMetalSupportBlock> DIAGONAL_METAL_SUPPORT = REG.block("diagonal_metal_support", DiagonalMetalSupportBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .onRegister(CreateRegistrate.connectedTextures(() -> new DiagonalMetalSupportCtBehavior(DnDecorSpriteShifts.DIAGONAL_METAL_SUPPORT)))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();



    public static final DyedBlockList<FlapDisplayBlock> DYED_DISPLAY_BOARDS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_display_board", FlapDisplayTypeBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(color.getMapColor()))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .transform(DStress.setNoImpact())
                .recipe((c, p) ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                                .pattern("DDD")
                                .pattern("DCD")
                                .pattern("DDD")
                                .define('C', color.getTag())
                                .define('D', DnDecorTags.commonItemTag("create/display_boards"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", DnDecor.loc("block/display_board_base/block"))
                            .texture("7", DnDecor.loc("block/display_boards/" + colorName))
                            .texture("particle", DnDecor.loc("block/display_boards/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/display_board_base/item"))
                            .texture("7", DnDecor.loc("block/display_boards/" + colorName))
                            .texture("particle", DnDecor.loc("block/display_boards/" + colorName));

                    p.horizontalBlock(c.get(), AssetLookup.partialBaseModel(c, p));
                })
                .transform(displayTarget(AllDisplayTargets.DISPLAY_BOARD))
                .item()
                .tag(DnDecorTags.commonItemTag("create/display_boards"), DnDecorTags.commonItemTag("create/dyed_display_boards"))
                .transform(customItemModel())
                .register();
    });



    public static final DyedBlockList<FlywheelBlock> DYED_FLYWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_flywheel", p -> new FlywheelTypeBlock(color, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .recipe((c, p) ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                                .pattern("DDD")
                                .pattern("DCD")
                                .pattern("DDD")
                                .define('C', color.getTag())
                                .define('D', DnDecorTags.commonItemTag("create/flywheels"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/flywheel/block"))
                            .texture("0", DnDecor.loc("block/flywheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/flywheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/flywheel/item"))
                            .texture("0", DnDecor.loc("block/flywheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/flywheel/" + colorName));

                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                })
                .item()
                .tag(DnDecorTags.commonItemTag("create/flywheels"), DnDecorTags.commonItemTag("create/dyed_flywheels"))
                .transform(customItemModel())
                .register();
    });

    public static final BlockEntry<DnDCogWheelBlock> DARK_METAL_COGWHEEL = REG.block("dark_metal_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.DARK_METAL_COGWHEEL,false,p))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .recipe((c, p) ->
                     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,c.get())
                             .requires(AllBlocks.SHAFT)
                             .requires(DnDecorBlocks.DARK_METAL_BLOCK)
                             .unlockedBy("has_" + c.getName(), has(c.get()))
                             .save(p, DnDecor.loc("crafting/" + c.getName()))
             )
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .blockstate(DnDecorBlockStateGen.cogwheelBlockState(false))
            .item(DnDCogwheelBlockItem::new)
            .tag(DnDecorTags.modItemTag("cogwheel"))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DnDCogWheelBlock> LARGE_DARK_METAL_COGWHEEL = REG.block("large_dark_metal_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.LARGE_DARK_METAL_COGWHEEL,true,p))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .recipe((c, p) ->
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,c.get())
                            .requires(AllBlocks.SHAFT)
                            .requires(DnDecorBlocks.DARK_METAL_BLOCK)
                            .requires(DnDecorBlocks.DARK_METAL_BLOCK)
                            .unlockedBy("has_" + c.getName(), has(c.get()))
                            .save(p, DnDecor.loc("crafting/" + c.getName()))
            )
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .blockstate(DnDecorBlockStateGen.cogwheelBlockState(true))
            .item(DnDCogwheelBlockItem::new)
            .tag(DnDecorTags.modItemTag("large_cogwheel"))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DnDCogWheelBlock> INDUSTRIAL_COGWHEEL = REG.block("industrial_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.INDUSTRIAL_COGWHEEL,false,p))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(DyeColor.GRAY))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .recipe((c, p) ->
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,c.get())
                            .requires(AllBlocks.SHAFT)
                            .requires(DnDecorBlocks.INDUSTRIAL_PLATING_BLOCK)
                            .unlockedBy("has_" + c.getName(), has(c.get()))
                            .save(p, DnDecor.loc("crafting/" + c.getName()))
            )
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p)))
            .item(DnDCogwheelBlockItem::new)
            .tag(DnDecorTags.modItemTag("industrial_cogwheel"))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<DnDCogWheelBlock> LARGE_INDUSTRIAL_COGWHEEL = REG.block("large_industrial_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.LARGE_INDUSTRIAL_COGWHEEL,true,p))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(DyeColor.GRAY))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .recipe((c, p) ->
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,c.get())
                            .requires(AllBlocks.SHAFT)
                            .requires(DnDecorBlocks.INDUSTRIAL_PLATING_BLOCK)
                            .requires(DnDecorBlocks.INDUSTRIAL_PLATING_BLOCK)
                            .unlockedBy("has_" + c.getName(), has(c.get()))
                            .save(p, DnDecor.loc("crafting/" + c.getName()))
            )
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p)))
            .item(DnDCogwheelBlockItem::new)
            .tag(DnDecorTags.modItemTag("large_industrial_cogwheel"))
            .transform(customItemModel())
            .register();

    public static final DyedBlockList<DnDCogWheelBlock> DYED_INDUSTRIAL_COGWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_industrial_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.DYED_GEAR.get(color),false, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(color.getMapColor()))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact()).blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", ResourceLocation.withDefaultNamespace("air"))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/block_shaftless", DnDecor.loc("block/industrial_cogwheel/block_shaftless"))
                            .texture("1_2", DnDecor.loc("block/industrial_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/industrial_cogwheel/item"))
                            .texture("1_2", DnDecor.loc("block/industrial_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                }).item(DnDCogwheelBlockItem::new)
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                        .pattern("DDD").pattern("DCD").pattern("DDD")
                        .define('C', color.getTag()).define('D', DnDecorTags.modItemTag("industrial_cogwheels"))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
                ).tag(DnDecorTags.modItemTag("industrial_cogwheels"), DnDecorTags.modItemTag("dyed_industrial_cogwheels"))
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<DnDCogWheelBlock> DYED_LARGE_INDUSTRIAL_COGWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_large_industrial_cogwheel", p -> new DnDCogWheelBlock(DnDecorPartialModels.DYED_LARGE_GEAR.get(color),true, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(color.getMapColor()))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", ResourceLocation.withDefaultNamespace("air"))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/block_shaftless", DnDecor.loc("block/large_industrial_cogwheel/block_shaftless"))
                            .texture("4", DnDecor.loc("block/large_industrial_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/large_industrial_cogwheel/item"))
                            .texture("4", DnDecor.loc("block/large_industrial_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_girder/" + colorName));
                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                }).item(DnDCogwheelBlockItem::new)
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                        .pattern("DDD").pattern("DCD").pattern("DDD")
                        .define('C', color.getTag()).define('D', DnDecorTags.modItemTag("large_industrial_cogwheel"))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
                ).tag(DnDecorTags.modItemTag("large_industrial_cogwheel"), DnDecorTags.modItemTag("dyed_large_industrial_cogwheel"))
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<DnDCogWheelBlock> DYED_COGWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_cogwheel", p -> new DnDCogWheelBlock(color,false, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()).sound(SoundType.NETHERITE_BLOCK))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block_shaftless", Create.asResource("block/cogwheel_shaftless"))
                            .texture("1_2", DnDecor.loc("block/dark_metal_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/dark_metal_cogwheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/block", ResourceLocation.withDefaultNamespace("air"))
                            .texture("particle", DnDecor.loc("block/dark_metal_cogwheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/cogwheel"))
                            .texture("1_2", DnDecor.loc("block/dark_metal_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/dark_metal_cogwheel/" + colorName));

                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                })
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .item(DnDCogwheelBlockItem::new)
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                        .pattern("DDD").pattern("DCD").pattern("DDD")
                        .define('C', color.getTag()).define('D', DnDecorTags.modItemTag("cogwheel"))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
                ).tag(DnDecorTags.modItemTag("cogwheel"), DnDecorTags.modItemTag("dyed_cogwheel"))
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<DnDCogWheelBlock> DYED_LARGE_COGWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_large_cogwheel", p -> new DnDCogWheelBlock(color,true, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()).sound(SoundType.NETHERITE_BLOCK))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block_shaftless", Create.asResource("block/large_cogwheel_shaftless"))
                            .texture("4", DnDecor.loc("block/large_dark_metal_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_dark_metal_cogwheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/block", ResourceLocation.withDefaultNamespace("air"))
                            .texture("particle", DnDecor.loc("block/large_dark_metal_cogwheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/large_cogwheel"))
                            .texture("4", DnDecor.loc("block/large_dark_metal_cogwheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/large_dark_metal_cogwheel/" + colorName));

                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                })
                .item(DnDCogwheelBlockItem::new)
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                        .pattern("DDD").pattern("DCD").pattern("DDD")
                        .define('C', color.getTag()).define('D', DnDecorTags.modItemTag("large_cogwheel"))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
                ).tag(DnDecorTags.modItemTag("large_cogwheel"), DnDecorTags.modItemTag("dyed_large_cogwheel"))
                .transform(customItemModel())
                .register();
    });

    public static final BlockEntry<OrnateGrateBlock> ORNATE_GRATE = REG.block("ornate_grate", OrnateGrateBlock::new)
            .transform(ornateConnected(() -> omni("ornate_grate")))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_GRAY))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("ISI")
                    .pattern("S S")
                    .pattern("ISI")
                    .define('S', Tags.Items.RODS_WOODEN)
                    .define('I', Tags.Items.INGOTS_IRON)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .lang("Ornate Grate")
            .addLayer(() -> RenderType::cutoutMipped)
            .item()
            .transform(b -> b.model((c, p) -> p.blockItem(() -> c.getEntry().getBlock())).build())
            .register();

    public static final BlockEntry<WindowBlock> ORNATE_IRON_GLASS =
            customWindowBlock("ornate_iron_glass", () -> omni("palettes/ornate_iron_glass"), () -> omni("palettes/ornate_iron_glass_end"), () -> RenderType::cutout, false, () -> MapColor.TERRACOTTA_LIGHT_GRAY)
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.items(AllPaletteBlocks.ORNATE_IRON_WINDOW.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                        p.stonecutting(DataIngredient.items(c), RecipeCategory.BUILDING_BLOCKS, AllPaletteBlocks.ORNATE_IRON_WINDOW, 1);
                    }).register();

    public static final BlockEntry<ConnectedGlassPaneBlock> ORNATE_IRON_GLASS_PANE =
            customWindowPane("ornate_iron_glass", ORNATE_IRON_GLASS, () -> omni("palettes/ornate_iron_glass"), () -> RenderType::cutoutMipped).register();

    public static final StoneTypeBlockList<CrushingWheelBlock> STONE_TYPE_CRUSHING_WHEELS = new StoneTypeBlockList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlocks.CRUSHING_WHEEL;
        return REG.block(id + "_crushing_wheel", p -> new CrushingWheelTypeBlock(block, p))
                .properties(p -> p.mapColor(block.get().defaultMapColor()).sound(block.get().defaultBlockState().getSoundType()))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(pickaxeOnly())
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                                .requires(block.get())
                                .requires(DnDecorTags.commonItemTag("create/crushing_wheels"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/crushing_wheel/block"))
                            .texture("insert", DnDecor.loc("block/crushing_wheels/" + id + "/insert"))
                            .texture("plates", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/crushing_wheel/item"))
                            .texture("insert", DnDecor.loc("block/crushing_wheels/" + id + "/insert"))
                            .texture("plates", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p));
                })
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(DStress.setImpact(8.0))
                .item()
                .tag(DnDecorTags.commonItemTag("create/crushing_wheels"))
                .transform(customItemModel())
                .register();
    });

    public static final StoneTypeBlockList<MillstoneBlock> STONE_TYPE_MILLSTONE = new StoneTypeBlockList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlocks.MILLSTONE;
        return REG.block(id + "_millstone", p -> new MillstoneTypeBlock(id, block, p))
                .properties(p -> p.mapColor(block.get().defaultMapColor()).sound(block.get().defaultBlockState().getSoundType()))
                .initialProperties(SharedProperties::stone)
                .transform(pickaxeOnly())
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                                .requires(block.get())
                                .requires(DnDecorTags.commonItemTag("create/millstones"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/millstone/block"))
                            .texture("5", DnDecor.loc("block/millstones/" + id));
                    p.models().withExistingParent("block/" + c.getName() + "/inner", Create.asResource("block/millstone/inner"))
                            .texture("5", DnDecor.loc("block/millstones/" + id));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/millstone/item"))
                            .texture("5", DnDecor.loc("block/millstones/" + id))
                            .texture("4", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p));
                })
                .transform(DStress.setImpact(4.0))
                .item()
                .tag(DnDecorTags.commonItemTag("create/millstones"))
                .transform(customItemModel())
                .register();
    });

    public static final BlockEntry<Block> INDUSTRIAL_PLATING_BLOCK = REG.block("industrial_plating_block", Block::new)
            .transform(layeredConnected(() -> omni("industrial_plating_block_side"), () -> omni("industrial_plating_block")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(c), RecipeCategory.BUILDING_BLOCKS, AllBlocks.INDUSTRIAL_IRON_BLOCK, 1);
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .simpleItem()
            .lang("Block of Industrial Plating")
            .register();

    public static final BlockEntry<LargeGirderBlock> LARGE_METAL_GIRDER = REG.block("large_metal_girder", LargeGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("CC").pattern("CC")
                    .define('C', AllBlocks.METAL_GIRDER)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .onRegister(CreateRegistrate.connectedTextures(() -> new RotatedPillarCTBehaviour(rectangle("large_girder"), omni("large_girder_top"))))
            .blockstate((c, p) -> p.axisBlock(c.get(), DnDecor.loc("block/large_girder"), DnDecor.loc("block/large_girder_top")))
            .simpleItem()
            .register();

    public static final BlockEntry<BeamBlock> BEAM = REG.block("beam", BeamBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                var beam = s.getValue(BeamBlock.BEAM);
                var axis = s.getValue(BeamBlock.AXIS) == Direction.Axis.X ? "_x" : "_z";
                var modelBoth = p.models().getExistingFile(DnDecor.loc("block/beam/block" + axis));
                var modelTop = p.models().getExistingFile(DnDecor.loc("block/beam/top" + axis));
                var modelBottom = p.models().getExistingFile(DnDecor.loc("block/beam/bottom" + axis));
                var model = switch (beam) {
                    case TOP -> modelTop;
                    case BOTTOM -> modelBottom;
                    case BOTH -> modelBoth;
                };
                return ConfiguredModel.builder().modelFile(model).build();
            }))
            .onRegister(connectedTextures(() -> new BeamCTBehaviour(horizontalKryppers("beam/beam"), vertical("beam/beam_top_z"), horizontalKryppers("beam/beam_top_x"))))
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(LARGE_METAL_GIRDER.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get(), INDUSTRIAL_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .transform(pickaxeOnly())
            .item()
            .model((c, p) -> p.withExistingParent("item/" + c.getName(), DnDecor.loc("block/beam/item")))
            .build()
            .lang("Beam")
            .register();

    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER = REG.block("diagonal_girder", DiagonalGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .addLayer(() -> RenderType::cutout)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                        .pattern(" C")
                        .pattern("C ")
                        .define('C', AllBlocks.METAL_GIRDER)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                        .pattern("C ")
                        .pattern(" C")
                        .define('C', AllBlocks.METAL_GIRDER)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/mirrored_" + c.getName()));
            }).transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .blockstate(new DiagonalGirderGenerator()::generate)
            .lang("Diagonal Girder")
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<Block> ZINC_BRICKS = REG.block("zinc_bricks", Block::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.sound(SoundType.METAL).mapColor(MapColor.GLOW_LICHEN))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("ZZ")
                    .pattern("ZZ")
                    .define('Z', AllBlocks.ZINC_BLOCK)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .lang("Zinc Bricks")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ZINC_CHECKER_TILES = REG.block("zinc_checker_tiles", Block::new)
            .transform(connected(() -> omni("zinc_checker_tiles")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.sound(SoundType.METAL).mapColor(MapColor.GLOW_LICHEN))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("III")
                    .pattern("IZI")
                    .pattern("III")
                    .define('I', commonItemTag("ingots/zinc"))
                    .define('Z', commonItemTag("storage_blocks/zinc"))
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .lang("Zinc Checker Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> STONE_METAL = REG.block("stone_metal", Block::new)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).sound(DnDecorSoundTypes.METAL_HEAVY).strength(1.5f,2f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(omni("stone_metal"))))
            .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, omni("stone_metal"))))
            .transform(pickaxeOnly())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("AS").pattern("SA")
                    .define('S', commonItemTag("plates/iron")).define('A', AllPaletteStoneTypes.ASURINE.baseBlock.get())
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName())))
            .blockstate((c, p) -> {
                var model = p.models().cubeAll(c.getName(), DnDecor.loc("block/stone_metal"));
                p.simpleBlockItem(c.get(), model);
                p.simpleBlock(c.get(), model);
            })
            .item().tag(DnDecorTags.modItemTag("stone_metal_decor")).build()
            .register();

    public static final DyedBlockList<Block> DYED_STONE_METAL = new DyedBlockList<>(color -> {
        var baseID = "stone_metal";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        var ct = omni(baseID + "/" + colorID);
        return REG.block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(DnDecorSoundTypes.METAL_HEAVY).strength(1.5f,2f))
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ct)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct)))
                .transform(pickaxeOnly())
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                            .pattern("ASA").pattern("SDS").pattern("ASA")
                            .define('S', commonItemTag("plates/iron")).define('A', AllPaletteStoneTypes.ASURINE.baseBlock.get()).define('D', color.getTag())
                            .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()));
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                            .pattern("SSS").pattern("SDS").pattern("SSS")
                            .define('S', DnDecorTags.modItemTag("stone_metal_decor")).define('D', color.getTag())
                            .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName() + "_dyed"));
                })
                .blockstate((c, p) -> {
                    var model = p.models().cubeAll(c.getName(), DnDecor.loc("block/" + baseID + "/" + colorID));
                    p.simpleBlockItem(c.get(), model);
                    p.simpleBlock(c.get(), model);
                })
                .item().tag(DnDecorTags.modItemTag("stone_metal_decor"), DnDecorTags.modItemTag("dyed_stone_metal_decor")).build()
                .register();
    });

    public static final BlockEntry<Block> DEEPSLATE_TILES = REG.block("small_deepslate_tiles",Block::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.DEEPSLATE).sound(SoundType.POLISHED_DEEPSLATE))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(omni("small_deepslate_tiles"))))
            .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, omni("small_deepslate_tiles"))))
            .transform(pickaxeOnly())
            .recipe((c, p) -> p.stonecutting(DataIngredient.items(Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, c, 1))
            .item().tag(DnDecorTags.modItemTag("deepslate_tiles_decor")).build()
            .register();

    public static final DyedBlockList<Block> DYED_DEEPSLATE_TILES = new DyedBlockList<>(color -> {
        var baseID = "small_deepslate_tiles";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        var ct = omni(baseID + "/" + colorID);
        return REG.block(blockID, Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(color.getMapColor()).sound(SoundType.POLISHED_DEEPSLATE))
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ct)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct)))
                .transform(pickaxeOnly())
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                        .pattern("SSS").pattern("SDS").pattern("SSS")
                        .define('S', DnDecorTags.modItemTag("deepslate_tiles_decor")).define('D', color.getTag())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName() + "_dyed")))
                .blockstate((c, p) -> {
                    var model = p.models().cubeAll(c.getName(), DnDecor.loc("block/" + baseID + "/" + colorID));
                    p.simpleBlockItem(c.get(), model);
                    p.simpleBlock(c.get(), model);
                })
                .item().tag(DnDecorTags.modItemTag("deepslate_tiles_decor"), DnDecorTags.modItemTag("dyed_deepslate_tiles_decor")).build()
                .register();
    });

    public static final DyedBlockList<VelvetBlock> DYED_VELVET_BLOCKS = new DyedBlockList<>(color -> velvetBlock(color.getSerializedName(), color.getMapColor(), color));

    public static final BlockEntry<ColoredStorageContainerBlock> DYED_STORAGE_CONTAINER = regColoredStorageContainer();
    


    public static TagKey<Block> stairsBlockTag = optionalTag(BuiltInRegistries.BLOCK, ResourceLocation.withDefaultNamespace("stairs"));
    public static TagKey<Item> stairsItemTag = optionalTag(BuiltInRegistries.ITEM, ResourceLocation.withDefaultNamespace("stairs"));
    public static TagKey<Block> slabsBlockTag = optionalTag(BuiltInRegistries.BLOCK, ResourceLocation.withDefaultNamespace("slabs"));
    public static TagKey<Item> slabsItemTag = optionalTag(BuiltInRegistries.ITEM, ResourceLocation.withDefaultNamespace("slabs"));

    public static final BlockEntry<Block> DARK_METAL_BLOCK = REG.block("dark_metal_block", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<Block> DARK_METAL_PLATING = REG.block("dark_metal_plating", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(omni("dark_metal_plating"))))
            .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, omni("dark_metal_plating"))))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(DARK_METAL_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(c.get()), RecipeCategory.BUILDING_BLOCKS, DARK_METAL_BLOCK, 1);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 9)
                        .pattern("CCC")
                        .pattern("CCC")
                        .pattern("CCC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> DARK_METAL_SLAB = REG.block("dark_metal_block_slab", SlabBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.slabBlock(c.get(), DnDecor.loc("block/dark_metal_block"),
                    DnDecor.loc("block/dark_metal_block_slab"), DnDecor.loc("block/dark_metal_block"), DnDecor.loc("block/dark_metal_block")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 2);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("CCC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(slabsItemTag)
            .build()
            .register();

    public static final BlockEntry<StairBlock> DARK_METAL_STAIRS = REG.block("dark_metal_block_stairs", p -> new StairBlock(DARK_METAL_BLOCK.getDefaultState(), p))
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.stairsBlock(c.get(), DnDecor.loc("block/dark_metal_block")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("X  ").pattern("XX ").pattern("XXX")
                        .define('X', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .item()
            .tag(darkMetalDecorTag, stairsItemTag)
            .build()
            .register();

    public static final BlockEntry<Block> DARK_METAL_BRICKS = REG.block("dark_metal_bricks", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> DARK_METAL_BRICK_SLAB = REG.block("dark_metal_brick_slab", SlabBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.slabBlock(c.get(), DnDecor.loc("block/dark_metal_bricks"),
                    DnDecor.loc("block/dark_metal_bricks"), DnDecor.loc("block/dark_metal_bricks"), DnDecor.loc("block/dark_metal_bricks")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 2);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("CCC")
                        .define('C', DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(slabsItemTag)
            .build()
            .register();

    public static final BlockEntry<StairBlock> DARK_METAL_BRICK_STAIRS = REG.block("dark_metal_brick_stairs", p -> new StairBlock(DARK_METAL_BLOCK.getDefaultState(), p))
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.stairsBlock(c.get(), DnDecor.loc("block/dark_metal_bricks")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("X  ").pattern("XX ").pattern("XXX")
                        .define('X', DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .item()
            .tag(darkMetalDecorTag, stairsItemTag)
            .build()
            .register();


    private static BlockEntry<ColoredStorageContainerBlock> regColoredStorageContainer() {
        return REG.block("colored_storage_container", ColoredStorageContainerBlock::new)
                .lang("Storage Container")
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                        .sound(SoundType.NETHERITE_BLOCK)
                        .explosionResistance(1200))
                .transform(pickaxeOnly())
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                    var refModel = Create.asResource("block/item_vault");
                    var refModelItem = Create.asResource("item/item_vault");
                    var color = s.getValue(ColoredStorageContainerBlock.COLOR);
                    var id = color.getSerializedName();
                    var path0 = DnDecor.loc("block/storage_container/" + id + "_storage_container_bottom_small");
                    var path1 = DnDecor.loc("block/storage_container/" + id + "_storage_container_front_small");
                    var path2 = DnDecor.loc("block/storage_container/" + id + "_storage_container_side_small");
                    var path3 = DnDecor.loc("block/storage_container/" + id + "_storage_container_top_small"); //particle

                    ModelFile model = p.models().withExistingParent("block/storage_containers/" + id, refModel)
                            .texture("0", path0).texture("1", path1).texture("2", path2).texture("3", path3).texture("particle", path3);

                    p.models().withExistingParent("item/" + id + "_storage_container", refModelItem).parent(model);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(s.getValue(ItemVaultBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 90 : 0)
                            .build();
                }))
                .onRegister(CreateRegistrate.connectedTextures(ColoredStorageContainerCTBehaviour::new))
                .register();
    }

    public static final BlockEntry<FrontlightBlock> BRASS_FRONTLIGHT = REG.block("brass_frontlight", FrontlightBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.TERRACOTTA_YELLOW).lightLevel(FrontlightBlock::getLight))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                var dir = s.getValue(FrontlightBlock.FACING);
                var lit = s.getValue(FrontlightBlock.LIT);
                var top = s.getValue(FrontlightBlock.ADDITIVE);
                var rot = s.getValue(FrontlightBlock.ROTATED);
                var target = "frontlight";
                if (!lit) target = target + "_off";
                if (top != Frontlight.EMPTY) target = top == Frontlight.TOP ? target + "_top" : target + "_grate" ;
                if (rot) target = target + "_rot";
                var refPath = "block/frontlight/";
                var refModel = DnDecor.loc(refPath + target);
                var texture = DnDecor.loc("block/" + c.getName());
                ModelFile model = p.models().withExistingParent("block/" + c.getName() + "/" + target, refModel).texture("0", texture).texture("particle", texture);

                if (lit && top == Frontlight.TOP && !rot) p.models().withExistingParent("block/" + c.getName(), DnDecor.loc(refPath + "frontlight_item")).texture("0", texture).texture("particle", texture);
                return ConfiguredModel.builder().modelFile(model)
                        .rotationX(dir == Direction.DOWN ? 90 : dir.getAxis().isHorizontal() ? 0 : 270)
                        .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + 180) % 360).build();
            })).recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("GM").pattern("MB")
                    .define('G', Ingredient.of(Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS, Items.BLAZE_ROD))
                    .define('M', DnDecorTags.commonItemTag("ingots/brass"))
                    .define('B', Tags.Items.STONES)
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDecor.loc("crafting/" + c.getName()))).simpleItem()
            .register();

    @SuppressWarnings("all")
    public static final MetalTypeBlockList<FrontlightBlock> METAL_TYPE_FRONTLIGHTS = new MetalTypeBlockList<FrontlightBlock>(type -> {
        var metal = type.get();
        if (metal.equals(AllMetalTypes.BRASS)) return BRASS_FRONTLIGHT;
        if (!DnDecor.LOAD_ALL_METALS && metal.modIDs.equals(MaterialTypeProvider.NA)) return null;

        if (metal.equals(AllMetalTypes.PLATINUM)) return null;
        if (metal.equals(AllMetalTypes.LEAD)) return null;
        if (metal.equals(AllMetalTypes.SILVER)) return null;
        if (metal.equals(AllMetalTypes.TIN)) return null;
        if (metal.equals(AllMetalTypes.URANIUM)) return null;
        if (metal.equals(AllMetalTypes.ALUMINUM)) return null;
        if (metal.equals(AllMetalTypes.NICKEL)) return null;
        if (metal.equals(AllMetalTypes.TUNGSTEN)) return null;
        if (metal.equals(AllMetalTypes.STEEL)) return null;
        if (metal.equals(AllMetalTypes.ELECTRUM)) return null;
        if (metal.equals(AllMetalTypes.BRONZE)) return null;
        if (metal.equals(AllMetalTypes.CAST_IRON)) return null;
        if (metal.equals(AllMetalTypes.INVAR)) return null;
        if (metal.equals(AllMetalTypes.CONSTANTAN)) return null;
        if (metal.equals(AllMetalTypes.WROUGHT_IRON)) return null;
        if (metal.equals(AllMetalTypes.TARNISHED_GOLD)) return null;

        var builder = REG.block(metal.id + "_frontlight", FrontlightBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(metal.color).lightLevel(FrontlightBlock::getLight))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                    var dir = s.getValue(FrontlightBlock.FACING);
                    var lit = s.getValue(FrontlightBlock.LIT);
                    var top = s.getValue(FrontlightBlock.ADDITIVE);
                    var rot = s.getValue(FrontlightBlock.ROTATED);
                    var target = "frontlight";
                    if (!lit) target = target + "_off";
                    if (top != Frontlight.EMPTY) target = top == Frontlight.TOP ? target + "_top" : target + "_grate" ;
                    if (rot) target = target + "_rot";
                    var refPath = "block/frontlight/";
                    var refModel = DnDecor.loc(refPath + target);
                    var texture = DnDecor.loc("block/" + c.getName());
                    ModelFile model = p.models().withExistingParent("block/" + c.getName() + "/" + target, refModel).texture("0", texture).texture("particle", texture);

                    if (lit && top == Frontlight.TOP && !rot) p.models().withExistingParent("block/" + c.getName(), DnDecor.loc(refPath + "frontlight_item")).texture("0", texture).texture("particle", texture);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(dir == Direction.DOWN ? 90 : dir.getAxis().isHorizontal() ? 0 : 270)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + 180) % 360)
                            .build();
                }));

        builder = builder.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("GM")
                        .pattern("MB")
                        .define('G', Ingredient.of(Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS, Items.BLAZE_ROD))
                        .define('M', ingredient.toVanilla())
                        .define('B', Tags.Items.STONES)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName()));
            }
        });
        if (metal.equals(AllMetalTypes.NETHERITE)) builder = builder.item().properties(p -> p.fireResistant()).build();
        else builder = builder.simpleItem();
        return builder.register();
    });

    @SuppressWarnings("all")
    public static final MetalTypeBlockList<Block> METAL_TYPE_FLOORS = new MetalTypeBlockList<Block>(type -> {
        var metal = type.get();
        if (!DnDecor.LOAD_ALL_METALS && metal.modIDs.equals(MaterialTypeProvider.NA)) return null;

        if (metal.equals(AllMetalTypes.PLATINUM)) return null;
        if (metal.equals(AllMetalTypes.LEAD)) return null;
        if (metal.equals(AllMetalTypes.SILVER)) return null;
        if (metal.equals(AllMetalTypes.TIN)) return null;
        if (metal.equals(AllMetalTypes.URANIUM)) return null;
        if (metal.equals(AllMetalTypes.ALUMINUM)) return null;
        if (metal.equals(AllMetalTypes.NICKEL)) return null;
        if (metal.equals(AllMetalTypes.TUNGSTEN)) return null;
        if (metal.equals(AllMetalTypes.STEEL)) return null;
        if (metal.equals(AllMetalTypes.ELECTRUM)) return null;
        if (metal.equals(AllMetalTypes.BRONZE)) return null;
        if (metal.equals(AllMetalTypes.CAST_IRON)) return null;
        if (metal.equals(AllMetalTypes.INVAR)) return null;
        if (metal.equals(AllMetalTypes.CONSTANTAN)) return null;
        if (metal.equals(AllMetalTypes.WROUGHT_IRON)) return null;
        if (metal.equals(AllMetalTypes.TARNISHED_GOLD)) return null;

        var builder = REG.block(metal.id + "_floor", Block::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(metal.sound).mapColor(metal.color))
                .transform(pickaxeOnly());

        builder = builder.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 2);
        });
        if (metal.equals(AllMetalTypes.NETHERITE)) builder = builder.item().properties(p -> p.fireResistant()).build();
        else builder = builder.simpleItem();
        return builder.simpleItem().register();
    });

    @SuppressWarnings("all")
    public static final MetalTypeBlockList<LargeChain> METAL_TYPE_LARGE_CHAINS = new MetalTypeBlockList<LargeChain>(type -> {
        var metal = type.get();
        if (metal.requireMods()) return null;
        if (!DnDecor.LOAD_ALL_METALS && metal.modIDs.equals(MaterialTypeProvider.NA)) return null;

        if (metal.equals(AllMetalTypes.PLATINUM)) return null;
        if (metal.equals(AllMetalTypes.LEAD)) return null;
        if (metal.equals(AllMetalTypes.SILVER)) return null;
        if (metal.equals(AllMetalTypes.TIN)) return null;
        if (metal.equals(AllMetalTypes.URANIUM)) return null;
        if (metal.equals(AllMetalTypes.ALUMINUM)) return null;
        if (metal.equals(AllMetalTypes.NICKEL)) return null;
        if (metal.equals(AllMetalTypes.TUNGSTEN)) return null;
        if (metal.equals(AllMetalTypes.STEEL)) return null;
        if (metal.equals(AllMetalTypes.ELECTRUM)) return null;
        if (metal.equals(AllMetalTypes.BRONZE)) return null;
        if (metal.equals(AllMetalTypes.CAST_IRON)) return null;
        if (metal.equals(AllMetalTypes.INVAR)) return null;
        if (metal.equals(AllMetalTypes.CONSTANTAN)) return null;
        if (metal.equals(AllMetalTypes.WROUGHT_IRON)) return null;
        if (metal.equals(AllMetalTypes.TARNISHED_GOLD)) return null;

        var builder = REG.block("large_" + metal.id + "_chain", LargeChain::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(DnDecorSoundTypes.CHAIN_HEAVY).mapColor(metal.color))
                .addLayer(() -> RenderType::cutout)
                .transform(pickaxeOnly())
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName(), DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    p.models().withExistingParent("block/" + c.getName() + "/block", DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                }).tag(AllTags.AllBlockTags.BRITTLE.tag, BlockTags.CLIMBABLE);
        builder = builder.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });
        if (metal.equals(AllMetalTypes.NETHERITE)) builder = builder.item().properties(p -> p.fireResistant()).build();
        else builder = builder.simpleItem();
        return builder.register();
    });

    public static final MetalTypeBoltBlockList<?> METAL_TYPE_BOLTS = new MetalTypeBoltBlockList<>(type -> {
        var metal = type.get();
        if (!DnDecor.LOAD_ALL_METALS && metal.modIDs.equals(MaterialTypeProvider.NA)) return null;
        return new BoltEntry<>(metal);
    });

    private static CTSpriteShiftEntry horizontal(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry horizontalKryppers(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry vertical(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.VERTICAL, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry rectangle(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.RECTANGLE, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry cross(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.CROSS, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry roof(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry roofStair(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF_STAIR, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry omni(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, blockTextureName, connectedTextureName);
    }

    private static CTSpriteShiftEntry horizontal(String texture) {
        return getCT(AllCTTypes.HORIZONTAL, texture, texture);
    }
    private static CTSpriteShiftEntry horizontalKryppers(String texture) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, texture, texture);
    }
    private static CTSpriteShiftEntry vertical(String texture) {
        return getCT(AllCTTypes.VERTICAL, texture, texture);
    }
    private static CTSpriteShiftEntry rectangle(String texture) {
        return getCT(AllCTTypes.RECTANGLE, texture, texture);
    }
    private static CTSpriteShiftEntry cross(String texture) {
        return getCT(AllCTTypes.CROSS, texture, texture);
    }
    private static CTSpriteShiftEntry roof(String texture) {
        return getCT(AllCTTypes.ROOF, texture, texture);
    }
    private static CTSpriteShiftEntry roofStair(String texture) {
        return getCT(AllCTTypes.ROOF_STAIR, texture, texture);
    }
    private static CTSpriteShiftEntry omni(String texture) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, texture, texture);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DnDecor.loc("block/" + blockTextureName),
                DnDecor.loc("block/" + connectedTextureName + "_connected"));
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static <T extends Block> Function<BlockState, ModelFile> getBlockModel(boolean customItem, DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        return $ -> customItem ? AssetLookup.partialBaseModel(c, p) : AssetLookup.standardModel(c, p);
    }

    public static void register() {
    }
}