package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelRenderer;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import com.simibubi.create.content.trains.display.FlapDisplayRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.blocks.MillstoneTypeRenderer;
import dev.lopyluna.dndecor.content.blocks.cogs.DnDCogwheelRenderer;
import dev.lopyluna.dndecor.content.blocks.cogs.DnDCogwheelVisual;
import dev.lopyluna.dndecor.content.blocks.flywheel.FlywheelTypeBlock;
import dev.lopyluna.dndecor.content.blocks.flywheel.FlywheelTypeVisual;

import dev.lopyluna.dndecor.content.blocks.stepped_lever.SteppedLeverBlockEntity;
import dev.lopyluna.dndecor.content.blocks.stepped_lever.SteppedLeverRenderer;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerBlockEntity;
import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import dev.lopyluna.dndecor.register.helpers.list_providers.StoneTypeBEList;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import static dev.lopyluna.dndecor.DnDecor.REGISTRATE;

public class DnDecorBETypes {

    public static BlockEntityEntry<ColoredStorageContainerBlockEntity> COLORED_STORAGE_CONTAINER =
            REGISTRATE.blockEntity("colored_storage_container", ColoredStorageContainerBlockEntity::new)
                    ///// .validBlock(DnDecorBlocks.DYED_STORAGE_CONTAINER)
                    .register();

    public static final BlockEntityEntry<SteppedLeverBlockEntity> STEPPED_LEVER = REGISTRATE
            .blockEntity("stepped_lever", SteppedLeverBlockEntity::new)
            .validBlocks(DnDecorBlocks.STEPPED_LEVER)
            .renderer(() -> SteppedLeverRenderer::new)
            .register();



    public static final BlockEntityEntry<FlapDisplayBlockEntity> FLAP_DISPLAYS = REGISTRATE
            .blockEntity("flap_display", FlapDisplayBlockEntity::new)
            .visual(() -> SingleAxisRotatingVisual.of(AllPartialModels.SHAFTLESS_COGWHEEL))
            .renderer(() -> FlapDisplayRenderer::new)
            .validBlocks(DnDecorBlocks.DYED_DISPLAY_BOARDS.toArray())
            .register();



    public static final BlockEntityEntry<FlywheelBlockEntity> COLORED_FLYWHEELS = REGISTRATE.blockEntity("flywheel", FlywheelBlockEntity::new)
            .visual(() -> (c, b, p) -> new FlywheelTypeVisual(DnDecorPartialModels.DYED_FLYWHEELS.get(((FlywheelTypeBlock) b.getBlockState().getBlock()).color), c, b, p), false)
            .validBlocks(DnDecorBlocks.DYED_FLYWHEELS.toArray())
            .renderer(() -> FlywheelRenderer::new)
            .register();


    public static final BlockEntityEntry<BracketedKineticBlockEntity> COLORED_COGWHEELS = REGISTRATE.blockEntity("cogwheel", BracketedKineticBlockEntity::new)
            .visual(() -> DnDCogwheelVisual::create, false)
            .validBlocks(DnDecorBlocks.DYED_COGWHEELS.toArray())
            .validBlocks(DnDecorBlocks.DYED_LARGE_COGWHEELS.toArray())
            .validBlocks(DnDecorBlocks.DARK_METAL_COGWHEEL,DnDecorBlocks.LARGE_DARK_METAL_COGWHEEL,DnDecorBlocks.INDUSTRIAL_COGWHEEL,DnDecorBlocks.LARGE_INDUSTRIAL_COGWHEEL)
            .renderer(() -> DnDCogwheelRenderer::new)
            .register();

    public static final StoneTypeBEList<CrushingWheelBlockEntity> STONE_TYPE_CRUSHING_WHEELS = new StoneTypeBEList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlockEntityTypes.CRUSHING_WHEEL;
        return REGISTRATE.blockEntity(id + "_crushing_wheel", CrushingWheelBlockEntity::new)
                .visual(() -> SingleAxisRotatingVisual.of(PartialModel.of(DnDecor.asResource("block/" + id + "_crushing_wheel/block"))), false)
                .validBlock(DnDecorBlocks.STONE_TYPE_CRUSHING_WHEELS.get(block))
                .renderer(() -> KineticBlockEntityRenderer::new)
                .register();
    });

    public static final StoneTypeBEList<MillstoneBlockEntity> STONE_TYPE_MILLSTONES = new StoneTypeBEList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlockEntityTypes.MILLSTONE;
        return REGISTRATE.blockEntity(id + "_millstone", MillstoneBlockEntity::new)
                .visual(() -> SingleAxisRotatingVisual.of(PartialModel.of(DnDecor.asResource("block/" + id + "_millstone/inner"))), false)
                .validBlock(DnDecorBlocks.STONE_TYPE_MILLSTONE.get(block))
                .renderer(() -> MillstoneTypeRenderer::new)
                .register();
    });

    public static void register() {}
}
