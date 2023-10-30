package com.mangomilk.design_decor.base;

import com.mangomilk.design_decor.CreateMMBuilding;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.utility.Couple;

@SuppressWarnings({"unused"})
public class MmbSpriteShifts {
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
            TINTED_HORIZONTAL_FRAMED_GLASS = horizontal("palettes/horizontal_tinted_framed_glass"),
            TINTED_VERTICAL_FRAMED_GLASS = vertical("palettes/vertical_tinted_framed_glass");

    private static Couple<CTSpriteShiftEntry> vault(String color,String name) {
        final String prefixed = "block/"+color+"_container/container_" + name;
        return Couple.createWithContext(
                medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, CreateMMBuilding.asResource(prefixed + "_small"),
                        CreateMMBuilding.asResource(medium ? prefixed + "_medium" : prefixed + "_large")));
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
        return CTSpriteShifter.getCT(type, CreateMMBuilding.asResource("block/" + blockTextureName),
                CreateMMBuilding.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    public static void init(){}

}
