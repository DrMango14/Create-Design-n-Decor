package com.mangomilk.design_decor.registry;

import com.mangomilk.design_decor.DesignDecor;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.utility.Couple;

@SuppressWarnings({"unused"})
public class CDDSpriteShifts {
    public static final Couple<CTSpriteShiftEntry>
            RED_CONTAINER_TOP = vault("red","top"),
            RED_CONTAINER_FRONT = vault("red","front"),
            RED_CONTAINER_SIDE = vault("red","side"),
            RED_CONTAINER_BOTTOM = vault("red","bottom");
    public static final Couple<CTSpriteShiftEntry>
            BLUE_CONTAINER_TOP = vault("blue","top"),
            BLUE_CONTAINER_FRONT = vault("blue","front"),
            BLUE_CONTAINER_SIDE = vault("blue","side"),
            BLUE_CONTAINER_BOTTOM = vault("blue","bottom");
    public static final Couple<CTSpriteShiftEntry>
            GREEN_CONTAINER_TOP = vault("green","top"),
            GREEN_CONTAINER_FRONT = vault("green","front"),
            GREEN_CONTAINER_SIDE = vault("green","side"),
            GREEN_CONTAINER_BOTTOM = vault("green","bottom");

    public static final CTSpriteShiftEntry
            ORNATE_GRATE = omni("ornate_grate"),
            INDUSTRIAL_PLATING_BLOCK = omni("industrial_plating_block"),
            INDUSTRIAL_PLATING_BLOCK_SIDE = omni("industrial_plating_block_side"),
            TINTED_FRAMED_GLASS = omni("palettes/tinted_framed_glass"),
            TINTED_HORIZONTAL_FRAMED_GLASS = omni("palettes/horizontal_tinted_framed_glass"),
            TINTED_VERTICAL_FRAMED_GLASS = omni("palettes/vertical_tinted_framed_glass"),
            STONE_TILES = omni("stone_tiles"),
            RED_STONE_TILES = omni("red_stone_tiles"),

            ZINC_CHECKER_TILES = omni("zinc_checker_tiles")

                    ;

    public static final CTSpriteShiftEntry
            ZINC_CATWALK = omni("zinc_catwalk"),
            COPPER_CATWALK = omni("copper_catwalk"),
            BRASS_CATWALK = omni("brass_catwalk"),
            IRON_CATWALK = omni("iron_catwalk")

    ;

    public static final CTSpriteShiftEntry
            WOOD_SUPPORT = vertical("wood_support_side"),

            DIAGONAL_METAL_SUPPORT = omni("diagonal_metal_support_top"),
            METAL_SUPPORT = vertical("metal_support_side");


    public static final Couple<CTSpriteShiftEntry>
            HORIZONTAL_TANK_FRONT = horizontalTank("front"),
            HORIZONTAL_TANK_SIDE = horizontalTank("side");

    private static Couple<CTSpriteShiftEntry> vault(String color,String name) {
        final String prefixed = "block/"+color+"_container/container_" + name;
        return Couple.createWithContext(
                medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, DesignDecor.asResource(prefixed + "_small"),
                        DesignDecor.asResource(medium ? prefixed + "_medium" : prefixed + "_large")));
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
        return CTSpriteShifter.getCT(type, DesignDecor.asResource("block/" + blockTextureName),
                DesignDecor.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
    private static Couple<CTSpriteShiftEntry> horizontalTank(String name) {
        final String prefixed = "block/horizontal_tank/tank_" + name;
        return Couple.createWithContext(
                medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, DesignDecor.asResource(prefixed + "_small"),
                        DesignDecor.asResource(medium ? prefixed + "_medium" : prefixed + "_large")));
    }
    public static void init(){}

}
