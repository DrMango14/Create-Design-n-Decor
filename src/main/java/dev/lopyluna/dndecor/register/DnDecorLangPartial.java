package dev.lopyluna.dndecor.register;

import dev.lopyluna.dndecor.DnDecor;
import net.minecraft.network.chat.MutableComponent;

import static dev.lopyluna.dndecor.DnDecor.REGISTRATE;

public class DnDecorLangPartial {

    public static MutableComponent TITLE = add("itemGroup", "base", DnDecor.NAME);

    public static MutableComponent add(String type, String key, String lang) {
        return REGISTRATE.addLang(type, DnDecor.asResource(key), lang);
    }

    public static void init() {}
}
