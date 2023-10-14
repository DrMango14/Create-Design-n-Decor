package com.mangomilk.mangomilk_building.registry;

import com.mangomilk.mangomilk_building.blocks.containers.blue.BlueContainerBlockEntity;
import com.mangomilk.mangomilk_building.blocks.containers.green.GreenContainerBlockEntity;
import com.mangomilk.mangomilk_building.blocks.containers.red.RedContainerBlockEntity;
import com.mangomilk.mangomilk_building.blocks.crushing_wheels.MmbCrushingWheelBlockEntity;
import com.mangomilk.mangomilk_building.blocks.crushing_wheels.MmbCrushingWheelControllerBlockEntity;
import com.mangomilk.mangomilk_building.blocks.gas_tank.GasTankBlockEntity;
import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.mangomilk.mangomilk_building.CreateMMBuilding.REGISTRATE;

public class MmbBlockEntities {

    public static final BlockEntityEntry<GasTankBlockEntity> GAS_TANK = REGISTRATE
            .blockEntity("gas_tank", GasTankBlockEntity::new)
            .validBlocks(MmbBlocks.GAS_TANK,MmbBlocks.COPPER_GAS_TANK)
            .register();
    public static final BlockEntityEntry<RedContainerBlockEntity> RED_CONTAINER = REGISTRATE
            .blockEntity("red_container", RedContainerBlockEntity::new)
            .validBlocks(MmbBlocks.RED_CONTAINER)
            .register();

    public static final BlockEntityEntry<GreenContainerBlockEntity> GREEN_CONTAINER = REGISTRATE
            .blockEntity("green_container", GreenContainerBlockEntity::new)
            .validBlocks(MmbBlocks.RED_CONTAINER)
            .register();

    public static final BlockEntityEntry<BlueContainerBlockEntity> BLUE_CONTAINER = REGISTRATE
            .blockEntity("blue_container", BlueContainerBlockEntity::new)
            .validBlocks(MmbBlocks.RED_CONTAINER)
            .register();

    public static final BlockEntityEntry<MmbCrushingWheelBlockEntity> MMB_CRUSHING_WHEEL = REGISTRATE
            .blockEntity("mmb_crushing_wheel", MmbCrushingWheelBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new, false)
            .validBlocks(
                    MmbBlocks.GRANITE_CRUSHING_WHEEL,
                    MmbBlocks.DIORITE_CRUSHING_WHEEL,

                    MmbBlocks.LIMESTONE_CRUSHING_WHEEL,
                    MmbBlocks.SCORCHIA_CRUSHING_WHEEL,
                    MmbBlocks.SCORIA_CRUSHING_WHEEL,
                    MmbBlocks.TUFF_CRUSHING_WHEEL,
                    MmbBlocks.VERIDIUM_CRUSHING_WHEEL,
                    MmbBlocks.DRIPSTONE_CRUSHING_WHEEL,
                    MmbBlocks.DEEPSLATE_CRUSHING_WHEEL,
                    MmbBlocks.CRIMSITE_CRUSHING_WHEEL,
                    MmbBlocks.CALCITE_CRUSHING_WHEEL,
                    MmbBlocks.ASURINE_CRUSHING_WHEEL,
                    MmbBlocks.OCHRUM_CRUSHING_WHEEL
            )
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<MmbCrushingWheelControllerBlockEntity> MMB_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE
                    .blockEntity("mmb_crushing_wheel_controller", MmbCrushingWheelControllerBlockEntity::new)
                    .validBlocks(
                            MmbBlocks.MMB_CRUSHING_WHEEL_CONTROLLER

                    )
                    .register();

    public static void register() {}
}
