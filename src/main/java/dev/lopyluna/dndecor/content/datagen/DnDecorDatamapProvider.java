package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.foundation.block.CopperRegistries;
import dev.lopyluna.dndecor.DnDecor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
public class DnDecorDatamapProvider extends DataMapProvider {
    public DnDecorDatamapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final Builder<Oxidizable, Block> oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        CopperRegistries.getWeatheringView().entrySet().stream().filter(e -> e.getKey().getRegisteredName().contains(DnDecor.MOD_ID)).forEach(e -> add(oxidizables, e.getKey(), new Oxidizable(e.getValue().value())));

        final Builder<Waxable, Block> waxables = builder(NeoForgeDataMaps.WAXABLES);
        CopperRegistries.getWaxableView().entrySet().stream().filter(e -> e.getKey().getRegisteredName().contains(DnDecor.MOD_ID)).forEach(e -> add(waxables, e.getKey(), new Waxable(e.getValue().value())));
    }

    public static <T> void add(Builder<T, Block> b, Holder<Block> now, T after) {
        b.add(now, after, false);
    }
}
