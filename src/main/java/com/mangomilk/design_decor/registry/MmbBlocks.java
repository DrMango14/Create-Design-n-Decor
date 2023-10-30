package com.mangomilk.design_decor.registry;

import com.mangomilk.design_decor.base.DecorBuilderTransformer;
import com.mangomilk.design_decor.base.MmbSpriteShifts;
import com.mangomilk.design_decor.blocks.*;
import com.mangomilk.design_decor.blocks.SignBlock;
import com.mangomilk.design_decor.blocks.chain.LargeChain;
import com.mangomilk.design_decor.blocks.chain.TagDependentLargeChain;
import com.mangomilk.design_decor.blocks.containers.red.RedContainerBlock;
import com.mangomilk.design_decor.blocks.containers.red.RedContainerCTBehaviour;
import com.mangomilk.design_decor.blocks.containers.red.RedContainerItem;
import com.mangomilk.design_decor.blocks.containers.blue.BlueContainerBlock;
import com.mangomilk.design_decor.blocks.containers.blue.BlueContainerCTBehaviour;
import com.mangomilk.design_decor.blocks.containers.blue.BlueContainerItem;
import com.mangomilk.design_decor.blocks.containers.green.GreenContainerBlock;
import com.mangomilk.design_decor.blocks.containers.green.GreenContainerCTBehaviour;
import com.mangomilk.design_decor.blocks.containers.green.GreenContainerItem;
import com.mangomilk.design_decor.blocks.crushing_wheels.MmbCrushingWheelBlock;
import com.mangomilk.design_decor.blocks.crushing_wheels.MmbCrushingWheelControllerBlock;
import com.mangomilk.design_decor.blocks.diagonal_girder.DiagonalGirderBlock;
import com.mangomilk.design_decor.blocks.diagonal_girder.DiagonalGirderGenerator;
import com.mangomilk.design_decor.blocks.floodlight.FloodlightBlock;
import com.mangomilk.design_decor.blocks.gas_tank.GasTankBlock;
import com.mangomilk.design_decor.blocks.glass.ConnectedTintedGlassBlock;
import com.mangomilk.design_decor.blocks.industrial_gear.IndustrialGearBlock;
import com.mangomilk.design_decor.blocks.industrial_gear.IndustrialGearBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.aluminum.AluminumBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.aluminum.AluminumLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.aluminum.AluminumLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.andesite.AndesiteBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.andesite.AndesiteLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.andesite.AndesiteLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.brass.BrassBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.brass.BrassLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.brass.BrassLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.capitalism.CapitalismBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.capitalism.CapitalismLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.capitalism.CapitalismLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.cast_iron.CastIronBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.cast_iron.CastIronLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.cast_iron.CastIronLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.copper.CopperBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.copper.CopperLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.copper.CopperLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.gold.GoldBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.gold.GoldLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.gold.GoldLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.industrial_iron.IndustrialIronBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.industrial_iron.IndustrialIronLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.industrial_iron.IndustrialIronLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.large_boiler.zinc.ZincBoilerStructure;
import com.mangomilk.design_decor.blocks.large_boiler.zinc.ZincLargeBoilerBlock;
import com.mangomilk.design_decor.blocks.large_boiler.zinc.ZincLargeBoilerBlockItem;
import com.mangomilk.design_decor.blocks.millstone.block.*;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Collections;

import static com.mangomilk.design_decor.CreateMMBuilding.REGISTRATE;
import static com.mangomilk.design_decor.base.DecorBuilderTransformer.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

@SuppressWarnings({"unused", "removal"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MmbBlocks {
    //LAMPS
    public static final BlockEntry<LampBlock> BRASS_LAMP = REGISTRATE.block("brass_lamp", LampBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p->p.lightLevel(s -> 15))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .item()
            .build()
            .lang("Brass Lamp")
            .register();

    public static final BlockEntry<LampBlock> COPPER_LAMP = REGISTRATE.block("copper_lamp", LampBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p->p.lightLevel(s -> 15))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .item()
            .build()
            .lang("Copper Lamp")
            .register();
    public static final BlockEntry<LampBlock> ZINC_LAMP = REGISTRATE.block("zinc_lamp", LampBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p->p.lightLevel(s -> 15))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .item()
            .build()
            .lang("Zinc Lamp")
            .register();

    public static final BlockEntry<Block> BRASS_LIGHT =
            REGISTRATE.block("brass_light", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .properties(p->p.lightLevel(s -> 15))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Brass Light")
                    .register();
    public static final BlockEntry<Block> COPPER_LIGHT =
            REGISTRATE.block("copper_light", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .properties(p->p.lightLevel(s -> 15))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Copper Light")
                    .register();
    public static final BlockEntry<Block> ZINC_LIGHT =
            REGISTRATE.block("zinc_light", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .properties(p->p.lightLevel(s -> 15))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Zinc Light")
                    .register();
    //FLOODLIGHTS
    public static final BlockEntry<FloodlightBlock> BRASS_FLOODLIGHT =
            REGISTRATE.block("brass_floodlight", FloodlightBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.lightLevel(s -> s.getValue(FloodlightBlock.TURNED_ON) ? 15 : 0))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .lang("Brass Floodlight")
                    .item()
                    .transform(customItemModel("_", "block"))
                    .register();

    public static final BlockEntry<FloodlightBlock> ANDESITE_FLOODLIGHT =
            REGISTRATE.block("andesite_floodlight", FloodlightBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.lightLevel(s -> s.getValue(FloodlightBlock.WATERLOGGED) == s.getValue(FloodlightBlock.TURNED_ON) ? 0 : !s.getValue(FloodlightBlock.WATERLOGGED) ? 12 : 8 ))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .lang("Andesite Floodlight")
                    .item()
                    .transform(customItemModel("_", "block"))
                    .register();

    public static final BlockEntry<FloodlightBlock> COPPER_FLOODLIGHT =
            REGISTRATE.block("copper_floodlight", FloodlightBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.lightLevel(s -> s.getValue(FloodlightBlock.WATERLOGGED) == s.getValue(FloodlightBlock.TURNED_ON) ? 12 : 0 ))
                    .properties(p -> p.lightLevel(s -> !s.getValue(FloodlightBlock.WATERLOGGED) == s.getValue(FloodlightBlock.TURNED_ON) ? 6 : 0 ))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .lang("Copper Floodlight")
                    .item()
                    .transform(customItemModel("_", "block"))
                    .register();

    //COGWHEELS
    public static final BlockEntry<IndustrialGearBlock> COGWHEEL =
            REGISTRATE.block("industrial_gear", IndustrialGearBlock::small)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_GRAY))
                    .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .transform(BlockStressDefaults.setNoImpact())
                    .blockstate(BlockStateGen.axisBlockProvider(false))
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .item(IndustrialGearBlockItem::new)
                    .build()
                    .lang("Industrial Gear")
                    .register();

    public static final BlockEntry<IndustrialGearBlock> LARGE_COGWHEEL =
            REGISTRATE.block("industrial_gear_large", IndustrialGearBlock::large)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_GRAY))
                    .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .transform(BlockStressDefaults.setNoImpact())
                    .blockstate(BlockStateGen.axisBlockProvider(false))
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .item(IndustrialGearBlockItem::new)
                    .build()
                    .lang("Large Industrial Gear")
                    .register();

    //BOILERS
    public static final BlockEntry<BoilerBlock> BRASS_BOILER = REGISTRATE.block("brass_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Brass Boiler")
            .register();

    public static final BlockEntry<BrassLargeBoilerBlock> LARGE_BRASS_BOILER = REGISTRATE.block("brass_boiler_large", BrassLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .properties(p -> p.isViewBlocking(MmbBlocks::never))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(BrassLargeBoilerBlockItem::new)
            .build()
            .lang("Large Brass Boiler")
            .register();

    public static final BlockEntry<BrassBoilerStructure> BRASS_BOILER_STRUCTURAL = REGISTRATE.block("brass_boiler_structure", BrassBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), BrassBoilerStructure.FACING))
            .lang("Large Brass Boiler")
            .register();

    public static final BlockEntry<TagBoilerBlock> ALUMINUM_BOILER = REGISTRATE.block("aluminium_boiler",
                    p -> new TagBoilerBlock(p, AllTags.forgeItemTag("ingots/aluminium")))
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Aluminium Boiler")
            .register();
    public static final BlockEntry<AluminumLargeBoilerBlock> LARGE_ALUMINUM_BOILER = REGISTRATE.block("aluminium_boiler_large",
                    p -> new AluminumLargeBoilerBlock(p, AllTags.forgeItemTag("ingots/aluminium")))
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(AluminumLargeBoilerBlockItem::new)
            .build()
            .lang("Large Aluminium Boiler")
            .register();

    public static final BlockEntry<AluminumBoilerStructure> ALUMINUM_BOILER_STRUCTURAL = REGISTRATE.block("aluminium_boiler_structure", AluminumBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), AluminumBoilerStructure.FACING))
            .lang("Large Aluminium Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> GOLD_BOILER = REGISTRATE.block("gold_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Gold Boiler")
            .register();
    public static final BlockEntry<GoldLargeBoilerBlock> LARGE_GOLD_BOILER = REGISTRATE.block("gold_boiler_large", GoldLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(GoldLargeBoilerBlockItem::new)
            .build()
            .lang("Large Gold Boiler")
            .register();

    public static final BlockEntry<GoldBoilerStructure> GOLD_BOILER_STRUCTURAL = REGISTRATE.block("gold_boiler_structure", GoldBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), GoldBoilerStructure.FACING))
            .lang("Large Gold Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> COPPER_BOILER = REGISTRATE.block("copper_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Copper Boiler")
            .register();
    public static final BlockEntry<CopperLargeBoilerBlock> LARGE_COPPER_BOILER = REGISTRATE.block("copper_boiler_large", CopperLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(CopperLargeBoilerBlockItem::new)
            .build()
            .lang("Large Copper Boiler")
            .register();

    public static final BlockEntry<CopperBoilerStructure> COPPER_BOILER_STRUCTURAL = REGISTRATE.block("copper_boiler_structure", CopperBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), CopperBoilerStructure.FACING))
            .lang("Large Copper Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> ZINC_BOILER = REGISTRATE.block("zinc_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Zinc Boiler")
            .register();
    public static final BlockEntry<ZincLargeBoilerBlock> LARGE_ZINC_BOILER = REGISTRATE.block("zinc_boiler_large", ZincLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(ZincLargeBoilerBlockItem::new)
            .build()
            .lang("Large Zinc Boiler")
            .register();

    public static final BlockEntry<ZincBoilerStructure> ZINC_BOILER_STRUCTURAL = REGISTRATE.block("zinc_boiler_structure", ZincBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), ZincBoilerStructure.FACING))
            .lang("Large Zinc Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> INDUSTRIAL_IRON_BOILER = REGISTRATE.block("industrial_iron_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Industrial Iron Boiler")
            .register();
    public static final BlockEntry<IndustrialIronLargeBoilerBlock> LARGE_INDUSTRIAL_IRON_BOILER = REGISTRATE.block("industrial_iron_boiler_large", IndustrialIronLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(IndustrialIronLargeBoilerBlockItem::new)
            .build()
            .lang("Large Industrial Iron Boiler")
            .register();

    public static final BlockEntry<IndustrialIronBoilerStructure> INDUSTRIAL_IRON_BOILER_STRUCTURAL = REGISTRATE.block("industrial_iron_boiler_structure", IndustrialIronBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), IndustrialIronBoilerStructure.FACING))
            .lang("Large Industrial Iron Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> ANDESITE_BOILER = REGISTRATE.block("andesite_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Andesite Boiler")
            .register();
    public static final BlockEntry<AndesiteLargeBoilerBlock> LARGE_ANDESITE_BOILER = REGISTRATE.block("andesite_boiler_large", AndesiteLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(AndesiteLargeBoilerBlockItem::new)
            .build()
            .lang("Large Andesite Boiler")
            .register();

    public static final BlockEntry<AndesiteBoilerStructure> ANDESITE_BOILER_STRUCTURAL = REGISTRATE.block("andesite_boiler_structure", AndesiteBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), AndesiteBoilerStructure.FACING))
            .lang("Large Andesite Boiler")
            .register();


    public static final BlockEntry<TagBoilerBlock> CAST_IRON_BOILER = REGISTRATE.block("cast_iron_boiler",
                    p -> new TagBoilerBlock(p, AllTags.forgeItemTag("ingots/cast_iron")))
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Cast Iron Boiler")
            .register();
    public static final BlockEntry<CastIronLargeBoilerBlock> LARGE_CAST_IRON_BOILER = REGISTRATE.block("cast_iron_boiler_large",
                    p -> new CastIronLargeBoilerBlock(p, AllTags.forgeItemTag("ingots/cast_iron")))
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(CastIronLargeBoilerBlockItem::new)
            .build()
            .lang("Large Cast Iron Boiler")
            .register();

    public static final BlockEntry<CastIronBoilerStructure> CAST_IRON_BOILER_STRUCTURAL = REGISTRATE.block("cast_iron_boiler_structure", CastIronBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), CastIronBoilerStructure.FACING))
            .lang("Large Cast Iron Boiler")
            .register();


    public static final BlockEntry<BoilerBlock> CAPITALISM_BOILER = REGISTRATE.block("capitalism_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Capitalism Boiler")
            .register();
    public static final BlockEntry<CapitalismLargeBoilerBlock> LARGE_CAPITALISM_BOILER = REGISTRATE.block("capitalism_boiler_large", CapitalismLargeBoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.hasPostProcess((p_61036_, p_61037_, p_61038_) -> true))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutout)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item(CapitalismLargeBoilerBlockItem::new)
            .build()
            .lang("Large Capitalism Boiler")
            .register();

    public static final BlockEntry<CapitalismBoilerStructure> CAPITALISM_BOILER_STRUCTURAL = REGISTRATE.block("capitalism_boiler_structure", CapitalismBoilerStructure::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStatesExcept(BlockStateGen.mapToAir(p), CapitalismBoilerStructure.FACING))
            .lang("Large Capitalism Boiler")
            .register();




    //GAS TANK
    public static final BlockEntry<GasTankBlock> GAS_TANK = REGISTRATE.block("gas_tank", GasTankBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.sound(SoundType.COPPER))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item()
            .build()
            .lang("Compact Iron Fluid Tank")
            .register();

    public static final BlockEntry<GasTankBlock> COPPER_GAS_TANK = REGISTRATE.block("copper_gas_tank", GasTankBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.sound(SoundType.COPPER))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item()
            .build()
            .lang("Compact Fluid Tank")
            .register();

    //CONTAINERS
    public static final BlockEntry<RedContainerBlock> RED_CONTAINER = REGISTRATE.block("red_container", RedContainerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK)
                    .explosionResistance(1200))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStates(s -> ConfiguredModel.builder()
                            .modelFile(AssetLookup.standardModel(c, p))
                            .rotationY(s.getValue(RedContainerBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 90 : 0)
                            .build()))
            .onRegister(connectedTextures(RedContainerCTBehaviour::new))
            .item(RedContainerItem::new)
            .build()
            .lang("Red Container")
            .register();

    public static final BlockEntry<BlueContainerBlock> BLUE_CONTAINER = REGISTRATE.block("blue_container",BlueContainerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.COLOR_BLUE))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK)
                    .explosionResistance(1200))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStates(s -> ConfiguredModel.builder()
                            .modelFile(AssetLookup.standardModel(c, p))
                            .rotationY(s.getValue(RedContainerBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 90 : 0)
                            .build()))
            .onRegister(connectedTextures(BlueContainerCTBehaviour::new))
            .item(BlueContainerItem::new)
            .build()
            .lang("Blue Container")
            .register();

    public static final BlockEntry<GreenContainerBlock> GREEN_CONTAINER = REGISTRATE.block("green_container",GreenContainerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.COLOR_GREEN))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK)
                    .explosionResistance(1200))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.getVariantBuilder(c.get())
                    .forAllStates(s -> ConfiguredModel.builder()
                            .modelFile(AssetLookup.standardModel(c, p))
                            .rotationY(s.getValue(RedContainerBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 90 : 0)
                            .build()))
            .onRegister(connectedTextures(GreenContainerCTBehaviour::new))
            .item(GreenContainerItem::new)
            .build()
            .lang("Green Container")
            .register();



    //SIGNS
    public static final BlockEntry<SignBlock> MOYAI_SIGN = REGISTRATE.block("moyai_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Moyai Sign")
            .register();

    public static final BlockEntry<SignBlock> WARNING_SIGN = REGISTRATE.block("warning_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Warning Sign")
            .register();
    public static final BlockEntry<SignBlock> ARROW_UP_SIGN = REGISTRATE.block("arrow_up_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Arrow Up Sign")
            .register();
    public static final BlockEntry<SignBlock> TAP_SIGN = REGISTRATE.block("tap_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Tap Sign")
            .register();
    public static final BlockEntry<SignBlock> STOP_SIGN = REGISTRATE.block("stop_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Stop Sign")
            .register();
    public static final BlockEntry<SignBlock> ARROW_RIGHT_SIGN = REGISTRATE.block("arrow_right_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Arrow Right Sign")
            .register();
    public static final BlockEntry<SignBlock> ARROW_LEFT_SIGN = REGISTRATE.block("arrow_left_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Arrow Left Sign")
            .register();
    public static final BlockEntry<SignBlock> GLITCH_WARNING_SIGN = REGISTRATE.block("glitch_warning_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Glitch Warning Sign")
            .register();
    public static final BlockEntry<SignBlock> BROKEN_WRENCH_SIGN = REGISTRATE.block("broken_wrench_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Broken Wrench Sign")
            .register();
    public static final BlockEntry<SignBlock> BIOHAZARD_SIGN = REGISTRATE.block("biohazard_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Biohazard Sign")
            .register();
    public static final BlockEntry<SignBlock> CAPITALISM_WARNING_SIGN = REGISTRATE.block("capitalism_warning_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Capitalism Warning Sign")
            .register();
    public static final BlockEntry<SignBlock> ARROW_DOWN_SIGN = REGISTRATE.block("arrow_down_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Arrow Down Sign")
            .register();
    public static final BlockEntry<SignBlock> GEAR_SIGN = REGISTRATE.block("gear_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Gear Sign")
            .register();

    public static final BlockEntry<SignBlock> CREEPER_SIGN = REGISTRATE.block("creeper_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Creeper Sign")
            .register();

    public static final BlockEntry<SignBlock> BUN_SIGN = REGISTRATE.block("bun_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Bun Sign")
            .register();

    public static final BlockEntry<SignBlock> SILLY_SIGN = REGISTRATE.block("silly_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Silly Sign")
            .register();

    public static final BlockEntry<SignBlock> OIL_SIGN = REGISTRATE.block("american_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Oil Sign")
            .register();

    public static final BlockEntry<SignBlock> MAGNET_SIGN = REGISTRATE.block("magnet_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Magnet Sign")
            .register();

    public static final BlockEntry<SignBlock> BLANK_SIGN = REGISTRATE.block("blank_sign", SignBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(BlockBehaviour.Properties::instabreak)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .lang("Blank Sign")
            .register();

    //CRUSHING WHEELS
    public static final BlockEntry<MmbCrushingWheelControllerBlock> MMB_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("crushing_wheel_controller", MmbCrushingWheelControllerBlock::new)
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable()
                            .air())
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(BlockStateGen.mapToAir(p), MmbCrushingWheelControllerBlock.FACING))
                    .register();

    public static final BlockEntry<MmbCrushingWheelBlock> GRANITE_CRUSHING_WHEEL =
            REGISTRATE.block("granite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.GRANITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Granite Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> DIORITE_CRUSHING_WHEEL =
            REGISTRATE.block("diorite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.DIORITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Diorite Crushing Wheel")
                    .register();
    
    public static final BlockEntry<MmbCrushingWheelBlock> LIMESTONE_CRUSHING_WHEEL =
            REGISTRATE.block("limestone_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.SANDSTONE)
                    .properties(p -> p.color(MaterialColor.SAND))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Limestone Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> OCHRUM_CRUSHING_WHEEL =
            REGISTRATE.block("ochrum_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.CALCITE)
                    .properties(p -> p.color(MaterialColor.COLOR_YELLOW))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Ochrum Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> SCORCHIA_CRUSHING_WHEEL =
            REGISTRATE.block("scorchia_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.BLACKSTONE)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Scorchia Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> SCORIA_CRUSHING_WHEEL =
            REGISTRATE.block("scoria_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.BLACKSTONE)
                    .properties(p -> p.color(MaterialColor.COLOR_BROWN))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Scoria Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> TUFF_CRUSHING_WHEEL =
            REGISTRATE.block("tuff_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.TUFF)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Tuff Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> VERIDIUM_CRUSHING_WHEEL =
            REGISTRATE.block("veridium_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.TUFF)
                    .properties(p -> p.color(MaterialColor.WARPED_NYLIUM))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Veridium Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> DRIPSTONE_CRUSHING_WHEEL =
            REGISTRATE.block("dripstone_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.DRIPSTONE_BLOCK)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Dripstone Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> DEEPSLATE_CRUSHING_WHEEL =
            REGISTRATE.block("deepslate_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Deepslate Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> CRIMSITE_CRUSHING_WHEEL =
            REGISTRATE.block("crimsite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.COLOR_RED))
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Crimsite Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> CALCITE_CRUSHING_WHEEL =
            REGISTRATE.block("calcite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.CALCITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Calcite Crushing Wheel")
                    .register();
    public static final BlockEntry<MmbCrushingWheelBlock> ASURINE_CRUSHING_WHEEL =
            REGISTRATE.block("asurine_crushing_wheel", MmbCrushingWheelBlock::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.color(MaterialColor.COLOR_BLUE))
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Asurine Crushing Wheel")
                    .register();

    public static final BlockEntry<GraniteDecoMillStoneBlock> GRANITE_MILLSTONE =
            REGISTRATE.block("granite_millstone", GraniteDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.GRANITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Granite Millstone")
                    .register();
    public static final BlockEntry<DioriteDecoMillStoneBlock> DIORITE_MILLSTONE =
            REGISTRATE.block("diorite_millstone", DioriteDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.DIORITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Diorite Millstone")
                    .register();
    public static final BlockEntry<LimestoneDecoMillStoneBlock> LIMESTONE_MILLSTONE =
            REGISTRATE.block("limestone_millstone", LimestoneDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.SANDSTONE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.SAND))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Limestone Millstone")
                    .register();
    public static final BlockEntry<OchrumDecoMillStoneBlock> OCHRUM_MILLSTONE =
            REGISTRATE.block("ochrum_millstone", OchrumDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.CALCITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Ochrum Millstone")
                    .register();
    public static final BlockEntry<ScorchiaDecoMillStoneBlock> SCORCHIA_MILLSTONE =
            REGISTRATE.block("scorchia_millstone", ScorchiaDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.BLACKSTONE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Scorchia Millstone")
                    .register();
    public static final BlockEntry<ScoriaDecoMillStoneBlock> SCORIA_MILLSTONE =
            REGISTRATE.block("scoria_millstone", ScoriaDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.BLACKSTONE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.COLOR_BROWN))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Scoria Millstone")
                    .register();
    public static final BlockEntry<TuffDecoMillStoneBlock> TUFF_MILLSTONE =
            REGISTRATE.block("tuff_millstone", TuffDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.TUFF)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Tuff Millstone")
                    .register();
    public static final BlockEntry<VeridiumDecoMillStoneBlock> VERIDIUM_MILLSTONE =
            REGISTRATE.block("veridium_millstone", VeridiumDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.TUFF)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.WARPED_NYLIUM))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Veridium Millstone")
                    .register();
    public static final BlockEntry<DripstoneDecoMillStoneBlock> DRIPSTONE_MILLSTONE =
            REGISTRATE.block("dripstone_millstone", DripstoneDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.DRIPSTONE_BLOCK)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Dripstone Millstone")
                    .register();
    public static final BlockEntry<DeepslateDecoMillStoneBlock> DEEPSLATE_MILLSTONE =
            REGISTRATE.block("deepslate_millstone", DeepslateDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Deepslate Millstone")
                    .register();
    public static final BlockEntry<CrimsiteDecoMillStoneBlock> CRIMSITE_MILLSTONE =
            REGISTRATE.block("crimsite_millstone", CrimsiteDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.COLOR_RED))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Crimsite Millstone")
                    .register();
    public static final BlockEntry<CalciteDecoMillStoneBlock> CALCITE_MILLSTONE =
            REGISTRATE.block("calcite_millstone", CalciteDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.CALCITE)
                    .properties(p -> p.destroyTime(1.25f))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Calcite Millstone")
                    .register();
    public static final BlockEntry<AsurineDecoMillStoneBlock> ASURINE_MILLSTONE =
            REGISTRATE.block("asurine_millstone", AsurineDecoMillStoneBlock::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(1.25f))
                    .properties(p -> p.color(MaterialColor.COLOR_BLUE))
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Asurine Millstone")
                    .register();



    //MISC
    public static final BlockEntry<Block> CAPITALISM_BLOCK =
            REGISTRATE.block("capitalism_block", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Block of Capitalism")
                    .register();
    public static final BlockEntry<Block> INDUSTRIAL_GOLD_BLOCK =
            REGISTRATE.block("industrial_gold_block", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Block of Industrial Gold")
                    .register();
    public static final BlockEntry<Block> ZINC_FLOOR =
            REGISTRATE.block("zinc_floor", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Zinc Floor")
                    .register();
    public static final BlockEntry<Block> BRASS_FLOOR =
            REGISTRATE.block("brass_floor", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Brass Floor")
                    .register();
    public static final BlockEntry<Block> COPPER_FLOOR =
            REGISTRATE.block("copper_floor", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Copper Floor")
                    .register();
    public static final BlockEntry<Block> INDUSTRIAL_IRON_FLOOR =
            REGISTRATE.block("industrial_iron_floor", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Industrial Iron Floor")
                    .register();
    public static final BlockEntry<Block> INDUSTRIAL_GOLD_FLOOR =
            REGISTRATE.block("industrial_gold_floor", Block::new)
                    .properties(p -> p.color(MaterialColor.GOLD))
                    .initialProperties(SharedProperties::copperMetal)
                    .transform(pickaxeOnly())
                    .item()
                    .build()
                    .lang("Industrial Gold Floor")
                    .register();

    public static final BlockEntry<Block> INDUSTRIAL_PLATING_BLOCK =
            REGISTRATE.block("industrial_plating_block", Block::new)
                    .transform(DecorBuilderTransformer.layeredConnected(() -> MmbSpriteShifts.INDUSTRIAL_PLATING_BLOCK_SIDE, () -> MmbSpriteShifts.INDUSTRIAL_PLATING_BLOCK))
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_GRAY))
                    .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
                    .simpleItem()
                    .lang("Block of Industrial Plating")
                    .register();

    public static final BlockEntry<OrnateGrateBlock> ORNATE_GRATE =
            REGISTRATE.block("ornate_grate", OrnateGrateBlock::new)
                    .transform(DecorBuilderTransformer.ornateconnected(() -> MmbSpriteShifts.ORNATE_GRATE))
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
                    .properties(p -> p.sound(SoundType.WOOD))
                    .transform(axeOrPickaxe())
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .lang("Ornate Grate")
                    .addLayer(() -> RenderType::cutoutMipped)
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<ConnectedTintedGlassBlock>
            TINTED_FRAMED_GLASS = tintedframedGlass(
            () -> new SimpleCTBehaviour(MmbSpriteShifts.TINTED_FRAMED_GLASS)),
            TINTED_HORIZONTAL_FRAMED_GLASS = tintedframedGlass("horizontal","Horizontal",
                    () -> new HorizontalCTBehaviour(MmbSpriteShifts.TINTED_HORIZONTAL_FRAMED_GLASS, MmbSpriteShifts.TINTED_FRAMED_GLASS)),
            TINTED_VERTICAL_FRAMED_GLASS = tintedframedGlass("vertical","Vertical",
                    () -> new HorizontalCTBehaviour(MmbSpriteShifts.TINTED_VERTICAL_FRAMED_GLASS));


    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER =
            REGISTRATE.block("diagonal_girder", DiagonalGirderBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_GRAY))
                    .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
                    .blockstate(new DiagonalGirderGenerator()::generate)
                    .lang("Diagonal Girder")
                    .item()
                    .transform(customItemModel())
                    .register();

    //Chains

    public static final BlockEntry<TagDependentLargeChain> LARGE_ALUMINIUM_CHAIN =
            REGISTRATE.block("aluminium_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/aluminium")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Aluminium Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_ANDESITE_CHAIN =
            REGISTRATE.block("andesite_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Andesite Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_BRASS_CHAIN =
            REGISTRATE.block("brass_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Brass Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_STRONG_BRONZE_CHAIN =
            REGISTRATE.block("strong_bronze_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/strong_bronze")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Bronze Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_BRONZE_CHAIN =
            REGISTRATE.block("bronze_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/bronze")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Bronze Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_CAST_IRON_CHAIN =
            REGISTRATE.block("cast_iron_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/cast_iron")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Cast Iron Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_COPPER_CHAIN =
            REGISTRATE.block("copper_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Copper Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_ELECTRUM_CHAIN =
            REGISTRATE.block("electrum_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/electrum")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Electrum Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_GOLD_CHAIN =
            REGISTRATE.block("gold_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Gold Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_INDUSTRIAL_IRON_CHAIN =
            REGISTRATE.block("industrial_iron_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Industrial Iron Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_IRON_CHAIN =
            REGISTRATE.block("iron_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Iron Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_LEAD_CHAIN =
            REGISTRATE.block("lead_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/lead")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Lead Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_MITHRIL_CHAIN =
            REGISTRATE.block("mithril_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/mithril")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Mithril Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_NETHERITE_CHAIN =
            REGISTRATE.block("netherite_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Netherite Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_NETHERSTEEL_CHAIN =
            REGISTRATE.block("nethersteel_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/nethersteel")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Nethersteel Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<LargeChain> LARGE_ZINC_CHAIN =
            REGISTRATE.block("zinc_large_chain", LargeChain::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Zinc Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_TIN_CHAIN =
            REGISTRATE.block("tin_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/tin")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Tin Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_STEEL_CHAIN =
            REGISTRATE.block("steel_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/steel")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Steel Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_SILVER_CHAIN =
            REGISTRATE.block("silver_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/silver")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Silver Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_INVAR_CHAIN =
            REGISTRATE.block("invar_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/invar")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Invar Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_NICKEL_CHAIN =
            REGISTRATE.block("nickel_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/nickel")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Nickel Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_ROSE_GOLD_CHAIN =
            REGISTRATE.block("rose_gold_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/rose_gold")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Rose Gold Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_COBALT_CHAIN =
            REGISTRATE.block("cobalt_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/cobalt")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Cobalt Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_MANYULLYN_CHAIN =
            REGISTRATE.block("manyullyn_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/manyullyn")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Manyulln Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_HEPATIZON_CHAIN =
            REGISTRATE.block("hepatizon_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/hepatizon")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Hepatizon Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_PIG_IRON_CHAIN =
            REGISTRATE.block("pig_iron_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/pig_iron")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Pig Iron Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_KNIGHTSLIME_CHAIN =
            REGISTRATE.block("knightslime_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/knightslime")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Knightslime Chain")
                    .item()
                    .build()
                    .register();
    public static final BlockEntry<TagDependentLargeChain> LARGE_QUEEN_SLIME_CHAIN =
            REGISTRATE.block("queen_slime_large_chain", p -> new TagDependentLargeChain(p, AllTags.forgeItemTag("ingots/queen_slime")))
                    .properties(p -> p.color(MaterialColor.METAL))
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .tag(AllTags.AllBlockTags.BRITTLE.tag)
                    .tag(BlockTags.CLIMBABLE)
                    .lang("Large Queen Slime Chain")
                    .item()
                    .build()
                    .register();


    //STONE PALETTE BLOCKS

    public static final BlockEntry<Block>
            CASTEL_BRICKS_GRANITE = CastelBricks("granite", "Granite", Blocks.GRANITE),
            CASTEL_BRICKS_DIORITE = CastelBricks("diorite", "Diorite", Blocks.DIORITE),
            CASTEL_BRICKS_ANDESITE = CastelBricks("andesite", "Andesite", Blocks.ANDESITE),
            CASTEL_BRICKS_CALCITE = CastelBricks("calcite", "Calcite", Blocks.CALCITE),
            CASTEL_BRICKS_DRIPSTONE = CastelBricks("dripstone", "Dripstone", Blocks.DRIPSTONE_BLOCK),
            CASTEL_BRICKS_DEEPSLATE = CastelBricks("deepslate", "Deepslate", Blocks.DEEPSLATE),
            CASTEL_BRICKS_TUFF = CastelBricks("tuff", "Tuff", Blocks.TUFF),
            CASTEL_BRICKS_ASURINE = CastelBricks("asurine", "Asurine", MaterialColor.COLOR_BLUE, Blocks.DEEPSLATE),
            CASTEL_BRICKS_CRIMSITE = CastelBricks("crimsite", "Crimsite", MaterialColor.COLOR_RED, Blocks.DEEPSLATE),
            CASTEL_BRICKS_LIMESTONE = CastelBricks("limestone", "Limestone", MaterialColor.SAND, Blocks.SANDSTONE),
            CASTEL_BRICKS_OCHRUM = CastelBricks("ochrum", "Ochrum", MaterialColor.TERRACOTTA_YELLOW, Blocks.CALCITE),
            CASTEL_BRICKS_SCORIA = CastelBricks("scoria", "Scoria", MaterialColor.COLOR_BROWN, Blocks.BLACKSTONE),
            CASTEL_BRICKS_SCORCHIA = CastelBricks("scorchia", "Scorchia", MaterialColor.TERRACOTTA_GRAY, Blocks.BLACKSTONE),
            CASTEL_BRICKS_VERIDIUM = CastelBricks("veridium", "Veridium", MaterialColor.WARPED_NYLIUM, Blocks.TUFF),

            CASTEL_TILE_GRANITE = CastelTiles("granite", "Granite", Blocks.GRANITE),
            CASTEL_TILE_DIORITE = CastelTiles("diorite", "Diorite", Blocks.DIORITE),
            CASTEL_TILE_ANDESITE = CastelTiles("andesite", "Andesite", Blocks.ANDESITE),
            CASTEL_TILE_CALCITE = CastelTiles("calcite", "Calcite", Blocks.CALCITE),
            CASTEL_TILE_DRIPSTONE = CastelTiles("dripstone", "Dripstone", Blocks.DRIPSTONE_BLOCK),
            CASTEL_TILE_DEEPSLATE = CastelTiles("deepslate", "Deepslate", Blocks.DEEPSLATE),
            CASTEL_TILE_TUFF = CastelTiles("tuff", "Tuff", Blocks.TUFF),
            CASTEL_TILE_ASURINE = CastelTiles("asurine", "Asurine", MaterialColor.COLOR_BLUE, Blocks.DEEPSLATE),
            CASTEL_TILE_CRIMSITE = CastelTiles("crimsite", "Crimsite", MaterialColor.COLOR_RED, Blocks.DEEPSLATE),
            CASTEL_TILE_LIMESTONE = CastelTiles("limestone", "Limestone", MaterialColor.SAND, Blocks.SANDSTONE),
            CASTEL_TILE_OCHRUM = CastelTiles("ochrum", "Ochrum", MaterialColor.TERRACOTTA_YELLOW, Blocks.CALCITE),
            CASTEL_TILE_SCORIA = CastelTiles("scoria", "Scoria", MaterialColor.COLOR_BROWN, Blocks.BLACKSTONE),
            CASTEL_TILE_SCORCHIA = CastelTiles("scorchia", "Scorchia", MaterialColor.TERRACOTTA_GRAY, Blocks.BLACKSTONE),
            CASTEL_TILE_VERIDIUM = CastelTiles("veridium", "Veridium", MaterialColor.WARPED_NYLIUM, Blocks.TUFF)
            ;

    public static class DecoTags {
        public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                                ResourceLocation id) {
            return registry.tags()
                    .createOptionalTagKey(id, Collections.emptySet());
        }
        public static <T> TagKey<T> CreateTag(IForgeRegistry<T> registry, String path) {
            return optionalTag(registry, new ResourceLocation("create", path));
        }
        public static TagKey<Item> CreateItemTag(String path) {
            return CreateTag(ForgeRegistries.ITEMS, path);
        }
        public static TagKey<Block> CreateBlockTag(String path) {
            return CreateTag(ForgeRegistries.BLOCKS, path);
        }
        public static <T> TagKey<T> MCTag(IForgeRegistry<T> registry, String path) {
            return optionalTag(registry, new ResourceLocation("minecraft", path));
        }
        public static TagKey<Item> MCItemTag(String path) {
            return MCTag(ForgeRegistries.ITEMS, path);
        }
        public static TagKey<Block> MCBlockTag(String path) {
            return MCTag(ForgeRegistries.BLOCKS, path);
        }
        public static void init() {
        }
    }

    private static boolean never(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }
    public static void register() {}
}
