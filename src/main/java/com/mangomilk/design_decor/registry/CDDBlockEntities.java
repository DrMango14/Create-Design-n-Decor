package com.mangomilk.design_decor.registry;

import com.mangomilk.design_decor.blocks.ceiling_fan.CeilingFanBlock;
import com.mangomilk.design_decor.blocks.ceiling_fan.CeilingFanBlockEntity;
import com.mangomilk.design_decor.blocks.ceiling_fan.CeilingFanInstance;
import com.mangomilk.design_decor.blocks.ceiling_fan.CeilingFanRenderer;
//import com.mangomilk.design_decor.blocks.containers.blue.BlueContainerBlockEntity;
//import com.mangomilk.design_decor.blocks.containers.green.GreenContainerBlockEntity;
//import com.mangomilk.design_decor.blocks.containers.red.RedContainerBlockEntity;
import com.mangomilk.design_decor.blocks.crushing_wheels.MmbCrushingWheelBlockEntity;
import com.mangomilk.design_decor.blocks.crushing_wheels.MmbCrushingWheelControllerBlockEntity;
import com.mangomilk.design_decor.blocks.gas_tank.GasTankBlockEntity;
import com.mangomilk.design_decor.blocks.horizontal_tanks.HorizontalFluidTankBlockEntity;
import com.mangomilk.design_decor.blocks.industrial_gear.IndustrialGearInstance;
import com.mangomilk.design_decor.blocks.industrial_gear.IndustrialGearRenderer;
import com.mangomilk.design_decor.blocks.millstone.DecoMillStoneBlockEntity;
import com.mangomilk.design_decor.blocks.millstone.instance.*;
import com.mangomilk.design_decor.blocks.millstone.renderer.*;
import com.mangomilk.design_decor.blocks.stepped_lever.SteppedLeverBlockEntity;
import com.mangomilk.design_decor.blocks.stepped_lever.SteppedLeverInstance;
import com.mangomilk.design_decor.blocks.stepped_lever.SteppedLeverRenderer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelInstance;
import com.simibubi.create.content.kinetics.flywheel.FlywheelRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.mangomilk.design_decor.DesignDecor.REGISTRATE;

public class CDDBlockEntities {

    public static final BlockEntityEntry<BracketedKineticBlockEntity> BRACKETED_KINETIC = REGISTRATE
            .blockEntity("simple_kinetic", BracketedKineticBlockEntity::new)
            .instance(() -> IndustrialGearInstance::new, false)
            .validBlocks(CDDBlocks.COGWHEEL, CDDBlocks.LARGE_COGWHEEL)
            .renderer(() -> IndustrialGearRenderer::new)
            .register();

    public static final BlockEntityEntry<GasTankBlockEntity> GAS_TANK = REGISTRATE
            .blockEntity("gas_tank", GasTankBlockEntity::new)
            .validBlocks(CDDBlocks.GAS_TANK, CDDBlocks.COPPER_GAS_TANK)
            .register();
//    public static final BlockEntityEntry<RedContainerBlockEntity> RED_CONTAINER = REGISTRATE
//            .blockEntity("red_container", RedContainerBlockEntity::new)
//            .validBlocks(CDDBlocks.RED_CONTAINER)
//            .register();
//
//    public static final BlockEntityEntry<GreenContainerBlockEntity> GREEN_CONTAINER = REGISTRATE
//            .blockEntity("green_container", GreenContainerBlockEntity::new)
//            .validBlocks(CDDBlocks.RED_CONTAINER)
//            .register();
//
//    public static final BlockEntityEntry<BlueContainerBlockEntity> BLUE_CONTAINER = REGISTRATE
//            .blockEntity("blue_container", BlueContainerBlockEntity::new)
//            .validBlocks(CDDBlocks.RED_CONTAINER)
//            .register();

    public static final BlockEntityEntry<MmbCrushingWheelBlockEntity> MMB_CRUSHING_WHEEL = REGISTRATE
            .blockEntity("mmb_crushing_wheel", MmbCrushingWheelBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new, false)
            .validBlocks(
                    CDDBlocks.GRANITE_CRUSHING_WHEEL,
                    CDDBlocks.DIORITE_CRUSHING_WHEEL,
                    CDDBlocks.LIMESTONE_CRUSHING_WHEEL,
                    CDDBlocks.SCORCHIA_CRUSHING_WHEEL,
                    CDDBlocks.SCORIA_CRUSHING_WHEEL,
                    CDDBlocks.TUFF_CRUSHING_WHEEL,
                    CDDBlocks.VERIDIUM_CRUSHING_WHEEL,
                    CDDBlocks.DRIPSTONE_CRUSHING_WHEEL,
                    CDDBlocks.DEEPSLATE_CRUSHING_WHEEL,
                    CDDBlocks.CRIMSITE_CRUSHING_WHEEL,
                    CDDBlocks.CALCITE_CRUSHING_WHEEL,
                    CDDBlocks.ASURINE_CRUSHING_WHEEL,
                    CDDBlocks.OCHRUM_CRUSHING_WHEEL,
                    AllBlocks.CRUSHING_WHEEL
            )
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> GRANITE_MILLSTONE =
            REGISTRATE.blockEntity("granite_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> GraniteMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.GRANITE_MILLSTONE)
                    .renderer(() -> GraniteMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> DIORITE_MILLSTONE =
            REGISTRATE.blockEntity("diorite_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> DioriteMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.DIORITE_MILLSTONE)
                    .renderer(() -> DioriteMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> LIMESTONE_MILLSTONE =
            REGISTRATE.blockEntity("limestone_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> LimestoneMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.LIMESTONE_MILLSTONE)
                    .renderer(() -> LimestoneMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> SCORCHIA_MILLSTONE =
            REGISTRATE.blockEntity("scorchia_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> ScorchiaMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.SCORCHIA_MILLSTONE)
                    .renderer(() -> ScorchiaMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> SCORIA_MILLSTONE =
            REGISTRATE.blockEntity("scoria_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> ScoriaMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.SCORIA_MILLSTONE)
                    .renderer(() -> ScoriaMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> TUFF_MILLSTONE =
            REGISTRATE.blockEntity("tuff_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> TuffMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.TUFF_MILLSTONE)
                    .renderer(() -> TuffMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> VERIDIUM_MILLSTONE =
            REGISTRATE.blockEntity("veridium_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> VeridiumMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.VERIDIUM_MILLSTONE)
                    .renderer(() -> VeridiumMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> DRIPSTONE_MILLSTONE =
            REGISTRATE.blockEntity("dripstone_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> DripstoneMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.DRIPSTONE_MILLSTONE)
                    .renderer(() -> DripstoneMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> DEEPSLATE_MILLSTONE =
            REGISTRATE.blockEntity("deepslate_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> DeepslateMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.DEEPSLATE_MILLSTONE)
                    .renderer(() -> DeepslateMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> CRIMSITE_MILLSTONE =
            REGISTRATE.blockEntity("crimsite_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> CrimsiteMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.CRIMSITE_MILLSTONE)
                    .renderer(() -> CrimsiteMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> CALCITE_MILLSTONE =
            REGISTRATE.blockEntity("calcite_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> CalciteMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.CALCITE_MILLSTONE)
                    .renderer(() -> CalciteMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> ASURINE_MILLSTONE =
            REGISTRATE.blockEntity("asurine_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> AsurineMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.ASURINE_MILLSTONE)
                    .renderer(() -> AsurineMillStoneRenderer::new)
                    .register();

    public static final BlockEntityEntry<DecoMillStoneBlockEntity> OCHRUM_MILLSTONE =
            REGISTRATE.blockEntity("ochrum_millstone", DecoMillStoneBlockEntity::new)
                    .instance(() -> OchrumMillStoneCogInstance::new, false)
                    .validBlocks(CDDBlocks.OCHRUM_MILLSTONE)
                    .renderer(() -> OchrumMillStoneRenderer::new)
                    .register();


    public static final BlockEntityEntry<MmbCrushingWheelControllerBlockEntity> MMB_CRUSHING_WHEEL_CONTROLLER = REGISTRATE
            .blockEntity("mmb_crushing_wheel_controller", MmbCrushingWheelControllerBlockEntity::new)
            .validBlocks(
                    CDDBlocks.MMB_CRUSHING_WHEEL_CONTROLLER
            )
            .register();

    public static final BlockEntityEntry<HorizontalFluidTankBlockEntity> HORIZONTAL_FLUID_TANK = REGISTRATE
            .blockEntity("horizontal_fluid_tank", HorizontalFluidTankBlockEntity::new)
           // .validBlocks(CDDBlocks.HORIZONTAL_FLUID_TANK)
            .register();

    public static final BlockEntityEntry<SteppedLeverBlockEntity> STEPPED_LEVER = REGISTRATE
            .blockEntity("stepped_lever", SteppedLeverBlockEntity::new)
            .instance(() -> SteppedLeverInstance::new, false)
            .validBlocks(CDDBlocks.STEPPED_LEVER)
            .renderer(() -> SteppedLeverRenderer::new)
            .register();

    public static final BlockEntityEntry<CeilingFanBlockEntity> CEILING_FAN = REGISTRATE
            .blockEntity("ceiling_fan", CeilingFanBlockEntity::new)
            .instance(() -> CeilingFanInstance::new, false)
            .validBlocks(CDDBlocks.CEILING_FAN)
            .renderer(() -> CeilingFanRenderer::new)
            .register();


    public static void register() {}
}
