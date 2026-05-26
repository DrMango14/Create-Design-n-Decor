package dev.lopyluna.dndecor.register.client;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import dev.lopyluna.dndecor.DnDecor;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "SameParameterValue"})
public class DnDecorSpriteShifts {
    public static final Map<DyeColor, SpriteShiftEntry>
            DYED_BELTS = new EnumMap<>(DyeColor.class), DYED_OFFSET_BELTS = new EnumMap<>(DyeColor.class),
            DYED_DIAGONAL_BELTS = new EnumMap<>(DyeColor.class);

    public static final CTSpriteShiftEntry WOOD_SUPPORT = vertical("wood_support_side");
    public static final CTSpriteShiftEntry DIAGONAL_METAL_SUPPORT = omni("diagonal_metal_support_top");
    public static final CTSpriteShiftEntry METAL_SUPPORT = vertical("metal_support_side");

    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>>
            DYED_CONTAINER_TOP = new HashMap<>(), DYED_CONTAINER_FRONT = new HashMap<>(),
            DYED_CONTAINER_SIDE = new HashMap<>(), DYED_CONTAINER_BOTTOM = new HashMap<>();
    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>>
            DYED_SOLID_CONTAINER_TOP = new HashMap<>(), DYED_SOLID_CONTAINER_FRONT = new HashMap<>(),
            DYED_SOLID_CONTAINER_SIDE = new HashMap<>(), DYED_SOLID_CONTAINER_BOTTOM = new HashMap<>();

    private static final Couple<CTSpriteShiftEntry>
            CONTAINER_TOP = container("top", "", null), CONTAINER_FRONT = container("front", "", null),
            CONTAINER_SIDE = container("side", "", null), CONTAINER_BOTTOM = container("bottom", "", null);

    public static final SpriteShiftEntry BELT = get("block/belt", "block/belt_scroll"),
            BELT_OFFSET = get("block/belt_offset", "block/belt_scroll"),
            BELT_DIAGONAL = get("block/belt_diagonal", "block/belt_diagonal_scroll");

    static {
        populateMaps();
    }

    private static void populateMaps() {
        for (DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            DYED_BELTS.put(color, get("block/belt", "block/belt/" + id + "_scroll"));
            DYED_OFFSET_BELTS.put(color, get("block/belt_offset", "block/belt/" + id + "_scroll"));
            DYED_DIAGONAL_BELTS.put(color, get("block/belt_diagonal", "block/belt/" + id + "_diagonal_scroll"));

            DYED_SOLID_CONTAINER_TOP.put(color, container("top", "normal", color));
            DYED_SOLID_CONTAINER_FRONT.put(color, container("front", "normal", color));
            DYED_SOLID_CONTAINER_SIDE.put(color, container("side", "normal", color));
            DYED_SOLID_CONTAINER_BOTTOM.put(color, container("bottom", "normal", color));
            DYED_CONTAINER_TOP.put(color, container("top", "vault", color));
            DYED_CONTAINER_FRONT.put(color, container("front", "vault", color));
            DYED_CONTAINER_SIDE.put(color, container("side", "vault", color));
            DYED_CONTAINER_BOTTOM.put(color, container("bottom", "vault", color));
        }
    }

    public static CTSpriteShiftEntry getColoredStorageTop(DyeColor color, boolean small, boolean alt) {
        if (color == null) return CONTAINER_TOP.get(small);
        return alt ? DYED_SOLID_CONTAINER_TOP.get(color).get(small) : DYED_CONTAINER_TOP.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageFront(DyeColor color, boolean small, boolean alt) {
        if (color == null) return CONTAINER_FRONT.get(small);
        return alt ? DYED_SOLID_CONTAINER_FRONT.get(color).get(small) : DYED_CONTAINER_FRONT.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageSide(DyeColor color, boolean small, boolean alt) {
        if (color == null) return CONTAINER_SIDE.get(small);
        return alt ? DYED_SOLID_CONTAINER_SIDE.get(color).get(small) : DYED_CONTAINER_SIDE.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageBottom(DyeColor color, boolean small, boolean alt) {
        if (color == null) return CONTAINER_BOTTOM.get(small);
        return alt ? DYED_SOLID_CONTAINER_BOTTOM.get(color).get(small) : DYED_CONTAINER_BOTTOM.get(color).get(small);
    }

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DnDecor.loc("block/" + blockTextureName), DnDecor.loc("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    private static Couple<CTSpriteShiftEntry> container(String name, String type, DyeColor color) {
        final String prefixed;
        if (color == null || type.isEmpty()) prefixed = "block/container_" + name;
        else prefixed = "block/containers/" + type + "/" + color.getSerializedName() + "_" + name;
        return Couple.createWithContext(medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, DnDecor.loc(prefixed + "_small"), DnDecor.loc(medium ? prefixed + "_medium" : prefixed + "_large")));
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(DnDecor.loc(originalLocation), DnDecor.loc(targetLocation));
    }

    public static void init() {}
}
