package com.mangomilk.mangomilk_building.base;

import com.mangomilk.mangomilk_building.CreateMMBuilding;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.utility.Couple;

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

    private static Couple<CTSpriteShiftEntry> vault(String color,String name) {
        final String prefixed = "block/"+color+"_container/container_" + name;
        return Couple.createWithContext(
                medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, CreateMMBuilding.asResource(prefixed + "_small"),
                        CreateMMBuilding.asResource(medium ? prefixed + "_medium" : prefixed + "_large")));
    }


    public static void init(){}

}
