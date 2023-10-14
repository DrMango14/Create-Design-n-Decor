package com.mangomilk.mangomilk_building.base;

import com.google.common.base.Supplier;
import com.google.gson.JsonElement;
import com.mangomilk.mangomilk_building.CreateMMBuilding;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.simibubi.create.foundation.utility.Lang;

public enum MmbLangPartials implements LangPartial {
    INTERFACE("UI & Messages");

    ;

    private final String display;
    private final Supplier<JsonElement> provider;

    private MmbLangPartials(String display) {
        this.display = display;
        this.provider = this::fromResource;
    }


    public String getDisplayName() {
        return display;
    }

    public JsonElement provide() {
        return provider.get();
    }

    private JsonElement fromResource() {
        String fileName = Lang.asId(name());
        String filepath = "assets/" + CreateMMBuilding.MOD_ID + "/lang/default/" + fileName + ".json";
        JsonElement element = FilesHelper.loadJsonResource(filepath);
        return element;
    }

}