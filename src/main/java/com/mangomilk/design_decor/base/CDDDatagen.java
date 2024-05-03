package com.mangomilk.design_decor.base;

import com.mangomilk.design_decor.DesignDecor;

import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.tterrag.registrate.providers.ProviderType;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static com.mangomilk.design_decor.DesignDecor.REGISTRATE;

public class CDDDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		Path CDDResources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
		ExistingFileHelper helper = new ExistingFileHelper(
				Set.of(CDDResources), Set.of("create"), false, null, null
		);
		REGISTRATE.setupDatagen(gen.createPack(), helper);
	}

	public static void gatherData(DataGenerator gen) {
//		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CRTagGen::generateBlockTags);
//		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CRTagGen::generateItemTags);
//		REGISTRATE.addDataGenerator(ProviderType.LANG, CRLangGen::generate);
//		PonderLocalization.provideRegistrateLang(REGISTRATE);
	}
}
