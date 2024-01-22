package com.mangomilk.design_decor.registry;


import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mangomilk.design_decor.DesignDecor.REGISTRATE;

@SuppressWarnings({"unused"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CDDItems {
    public static final ItemEntry<Item>
            CARDBOARD_SHEET = REGISTRATE.item("cardboard_sheet", Item::new).register();

    public static void register() {}
}
