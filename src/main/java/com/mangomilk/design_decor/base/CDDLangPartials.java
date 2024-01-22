package com.mangomilk.design_decor.base;

import com.google.common.base.Supplier;
import com.google.gson.JsonElement;
import com.mangomilk.design_decor.DesignDecor;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.simibubi.create.foundation.utility.Lang;

public enum CDDLangPartials implements LangPartial {
    INTERFACE("UI & Messages");

    ;

    private final String display;
    private final Supplier<JsonElement> provider;

    private CDDLangPartials(String display) {
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
        String filepath = "assets/" + DesignDecor.MOD_ID + "/lang/default/" + fileName + ".json";
        JsonElement element = FilesHelper.loadJsonResource(filepath);
        return element;
    }

}