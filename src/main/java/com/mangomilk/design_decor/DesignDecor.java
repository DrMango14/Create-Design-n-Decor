package com.mangomilk.design_decor;

import com.mangomilk.design_decor.base.CDDCreativeModeTabs;
import com.mangomilk.design_decor.registry.CDDBlockEntities;
import com.mangomilk.design_decor.registry.CDDBlocks;
import com.mangomilk.design_decor.registry.CDDItems;
import com.mangomilk.design_decor.registry.CDDPartialModels;
//import com.mangomilk.design_decor.registry.CDDSoundEvents;
import com.mangomilk.design_decor.registry.CDDSpriteShifts;
import com.mojang.logging.LogUtils;
import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;

public class DesignDecor implements ModInitializer {
	public static final String MOD_ID = "design_decor";
	public static final String NAME = "Create: Design n' Decor";
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(DesignDecor.MOD_ID);
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);
		CDDCreativeModeTabs.register();
		REGISTRATE.setCreativeTab(CDDCreativeModeTabs.BUILDING.key());
		CDDBlocks.register();
		CDDBlocks.DecoTags.init();
		CDDItems.register();
//		CDDSoundEvents.register(eventBus);
		CDDBlockEntities.register();
		CDDSpriteShifts.init();
		CDDPartialModels.init();
		REGISTRATE.register();

	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

}
