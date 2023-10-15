package com.mangomilk.design_decor.registry;


import com.mangomilk.design_decor.base.DecorBuilderTransformer;
import com.mangomilk.design_decor.base.MmbSpriteShifts;
import com.mangomilk.design_decor.blocks.BoilerBlock;
import com.mangomilk.design_decor.blocks.LampBlock;
import com.mangomilk.design_decor.blocks.SignBlock;
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
import com.mangomilk.design_decor.blocks.floodlight.FloodlightGenerator;
import com.mangomilk.design_decor.blocks.gas_tank.GasTankBlock;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mangomilk.design_decor.CreateMMBuilding.REGISTRATE;
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
                    .blockstate(new FloodlightGenerator()::generate)
                    .lang("Brass Headlight")
                    .item()
                    .transform(customItemModel("_", "block"))
                    .register();



    //BOILERS
    public static final BlockEntry<BoilerBlock> BRASS_BOILER = REGISTRATE.block("brass_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Brass Cylinder")
            .register();

    public static final BlockEntry<BoilerBlock> ALUMINUM_BOILER = REGISTRATE.block("aluminum_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Aluminum Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> ALUMINUM_BOILER_SPECIAL = REGISTRATE.block("aluminum_boiler_special", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Aluminum Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> GOLD_BOILER = REGISTRATE.block("gold_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Golden Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> COPPER_BOILER = REGISTRATE.block("copper_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Copper Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> ZINC_BOILER = REGISTRATE.block("zinc_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Zinc Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> INDUSTRIAL_IRON_BOILER = REGISTRATE.block("industrial_iron_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Industrial Iron Cylinder")
            .register();
    public static final BlockEntry<BoilerBlock> CAST_IRON_BOILER = REGISTRATE.block("cast_iron_boiler", BoilerBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .build()
            .lang("Cast Iron Cylinder")
            .register();


    //GAS TANK
    public static final BlockEntry<GasTankBlock> GAS_TANK = REGISTRATE.block("gas_tank", GasTankBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item()
            .build()
            .lang("Gas Tank")
            .register();

    public static final BlockEntry<GasTankBlock> COPPER_GAS_TANK = REGISTRATE.block("copper_gas_tank", GasTankBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item()
            .build()
            .lang("Copper Gas Tank")
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
    public static final BlockEntry<MmbCrushingWheelBlock> GRANITE_CRUSHING_WHEEL =
            REGISTRATE.block("granite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Granite Crushing Wheel")
                    .register();

    public static final BlockEntry<MmbCrushingWheelControllerBlock> MMB_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("granite_crushing_wheel_controller", MmbCrushingWheelControllerBlock::new)
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable()
                            .air())
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(BlockStateGen.mapToAir(p), MmbCrushingWheelControllerBlock.FACING))

                    .register();


    public static final BlockEntry<MmbCrushingWheelBlock> DIORITE_CRUSHING_WHEEL =
            REGISTRATE.block("diorite_crushing_wheel", MmbCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
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
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Asurine Crushing Wheel")
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


    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER =
            REGISTRATE.block("diagonal_girder", DiagonalGirderBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .addLayer(() -> RenderType::cutout)
                    .transform(axeOrPickaxe())
                    .blockstate(new DiagonalGirderGenerator()::generate)
                    .lang("Diagonal Girder")
                    .item()
                    .transform(customItemModel())
                    .register();


    public static void register() {}
}
