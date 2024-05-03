package com.mangomilk.design_decor.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.mangomilk.design_decor.DesignDecor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CDDPartialModels {

    public static final PartialModel
            EMPTY = block("empty"),

            SHAFTLESS_LARGE_COGWHEEL = block("industrial_gear_large"),

            GRANITE_MILLSTONE_COG = block("granite_millstone/inner"),
            DIORITE_MILLSTONE_COG = block("diorite_millstone/inner"),
            LIMESTONE_MILLSTONE_COG = block("limestone_millstone/inner"),
            SCORCHIA_MILLSTONE_COG = block("scorchia_millstone/inner"),
            SCORIA_MILLSTONE_COG = block("scoria_millstone/inner"),
            TUFF_MILLSTONE_COG = block("tuff_millstone/inner"),
            VERIDIUM_MILLSTONE_COG = block("veridium_millstone/inner"),
            DRIPSTONE_MILLSTONE_COG = block("dripstone_millstone/inner"),
            DEEPSLATE_MILLSTONE_COG = block("deepslate_millstone/inner"),
            CRIMSITE_MILLSTONE_COG = block("crimsite_millstone/inner"),
            CALCITE_MILLSTONE_COG = block("calcite_millstone/inner"),
            ASURINE_MILLSTONE_COG = block("asurine_millstone/inner"),
            OCHRUM_MILLSTONE_COG = block("ochrum_millstone/inner"),
            STEPPED_LEVER_HANDLE = block("stepped_lever/lever"),

            CEILING_FAN = block("ceiling_fan/fan")


    ;




    private static PartialModel block(String path) {
        return new PartialModel(DesignDecor.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(DesignDecor.asResource("entity/" + path));
    }

    public static void init() {
    }
}
