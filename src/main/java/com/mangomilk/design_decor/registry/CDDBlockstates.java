package com.mangomilk.design_decor.registry;

import com.mangomilk.design_decor.blocks.railings.RailingBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import net.minecraft.world.level.block.Block;

public class CDDBlockstates {

	public static NonNullBiConsumer<DataGenContext<Block, RailingBlock>, RegistrateBlockstateProvider> railing(
		String name) {
		return (c, p) -> {

			ModelFile side = AssetLookup.partialBaseModel(c, p, "railing");


			p.getMultipartBuilder(c.get())
				.part()
				.modelFile(side)
				.addModel()
				.condition(RailingBlock.NORTH, true)
				.end()
				.part()
				.modelFile(side)
				.rotationY(90)
				.addModel()
				.condition(RailingBlock.EAST, true)
				.end()
				.part()
				.modelFile(side)
				.rotationY(180)
				.addModel()
				.condition(RailingBlock.SOUTH, true)
				.end()
				.part()
				.modelFile(side)
				.rotationY(270)
				.addModel()
				.condition(RailingBlock.WEST, true)
				.end()

				//.part()
				//.modelFile(corner)
				//.addModel()
				//.condition(RailingBlock.NORTH, true)
				//.condition(RailingBlock.WEST, true)
				//.end()
				//.part()
				//.modelFile(corner)
				//.rotationY(90)
				//.addModel()
				//.condition(RailingBlock.EAST, true)
				//.condition(RailingBlock.NORTH, true)
				//.end()
				//.part()
				//.modelFile(corner)
				//.rotationY(180)
				//.addModel()
				//.condition(RailingBlock.SOUTH, true)
				//.condition(RailingBlock.EAST, true)
				//.end()
				//.part()
				//.modelFile(corner)
				//.rotationY(270)
				//.addModel()
				//.condition(RailingBlock.WEST, true)
				//.condition(RailingBlock.SOUTH, true)
				//.end()


			;
		};
	}



	}
