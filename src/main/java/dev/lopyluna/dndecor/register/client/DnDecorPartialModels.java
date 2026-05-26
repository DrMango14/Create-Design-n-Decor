package dev.lopyluna.dndecor.register.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.createmod.catnip.data.Iterate;
import net.minecraft.world.item.DyeColor;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DnDecorPartialModels {
    public static final PartialModel
            BELT_START = block("belt/start"), BELT_MIDDLE = block("belt/middle"),
            BELT_END = block("belt/end"), BELT_START_BOTTOM = block("belt/start_bottom"),
            BELT_MIDDLE_BOTTOM = block("belt/middle_bottom"), BELT_END_BOTTOM = block("belt/end_bottom"),
            BELT_DIAGONAL_START = block("belt/diagonal_start"), BELT_DIAGONAL_MIDDLE = block("belt/diagonal_middle"),
            BELT_DIAGONAL_END = block("belt/diagonal_end"),
            DARK_METAL_COGWHEEL = block("dark_metal_cogwheel/block_shaftless"),
            LARGE_DARK_METAL_COGWHEEL = block("large_dark_metal_cogwheel/block_shaftless"),
            INDUSTRIAL_COGWHEEL = block("industrial_cogwheel/block_shaftless"),
            LARGE_INDUSTRIAL_COGWHEEL = block("large_industrial_cogwheel/block_shaftless"),
            STEPPED_LEVER_HANDLE = block("stepped_lever/lever"),
            LARGE_FAN = block("large_fan/block");

    public static final Map<DyeColor, PartialModel> DYED_FLYWHEELS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_LARGE_FANS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_COGWHEEL = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_LARGE_COGWHEEL = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_GEAR = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_LARGE_GEAR = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_TEXT_PLATE = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, PartialModel> DYED_TEXT_PLATE_DOUBLE = new EnumMap<>(DyeColor.class);
    public static final Map<MaterialTypeProvider.MetalType, PartialModel> METAL_TEXT_PLATE = new HashMap<>();
    public static final Map<MaterialTypeProvider.MetalType, PartialModel> METAL_TEXT_PLATE_DOUBLE = new HashMap<>();

    static {
        for (var color : DyeColor.values()) DYED_FLYWHEELS.put(color, block(color.getSerializedName() + "_flywheel/block"));
        for (var color : DyeColor.values()) DYED_LARGE_FANS.put(color, block(color.getSerializedName() + "_large_fan/block"));

        for (var color : DyeColor.values()) DYED_COGWHEEL.put(color, block(color.getSerializedName() + "_cogwheel/block_shaftless"));
        for (var color : DyeColor.values()) DYED_LARGE_COGWHEEL.put(color, block(color.getSerializedName() + "_large_cogwheel/block_shaftless"));

        for (var color : DyeColor.values()) DYED_GEAR.put(color, block(color.getSerializedName() + "_industrial_cogwheel/block_shaftless"));
        for (var color : DyeColor.values()) DYED_LARGE_GEAR.put(color, block(color.getSerializedName() + "_large_industrial_cogwheel/block_shaftless"));

        for (var color : DyeColor.values()) for (var extended : Iterate.falseAndTrue) (extended ? DYED_TEXT_PLATE_DOUBLE : DYED_TEXT_PLATE).put(color, block("text_plate/colors/"+color+(extended ? "_double" : "")));
        for (var type : MaterialTypeProvider.metalTypes) for (var extended : Iterate.falseAndTrue) (extended ? METAL_TEXT_PLATE_DOUBLE : METAL_TEXT_PLATE).put(type.get(), block("text_plate/metals/"+type.get().id+(extended ? "_double" : "")));
    }

    public static PartialModel getTextPlateModel(@Nullable MaterialTypeProvider.MetalType type, @Nullable DyeColor color, boolean extended) {
        if (type != null) return extended ? METAL_TEXT_PLATE_DOUBLE.get(type) : METAL_TEXT_PLATE.get(type);
        color = color == null ? DyeColor.WHITE : color;
        return extended ? DYED_TEXT_PLATE_DOUBLE.get(color) : DYED_TEXT_PLATE.get(color);
    }

    private static PartialModel block(String path) {
        return PartialModel.of(DnDecor.loc("block/" + path));
    }

    public static void init() {}
}
