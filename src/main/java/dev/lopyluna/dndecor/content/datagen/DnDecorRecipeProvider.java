package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.datagen.recipes.DeployGen;
import dev.lopyluna.dndecor.content.datagen.recipes.WashingGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("NullableProblems")
public class DnDecorRecipeProvider extends RecipeProvider {
    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

    public DnDecorRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new WashingGen(output, registries));
        GENERATORS.add(new DeployGen(output, registries));

        gen.addProvider(true, new DataProvider() {
            @Override public String getName() {
                return DnDecor.NAME + "'s Processing Recipes";
            }
            @Override public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream().map(gen -> gen.run(dc)).toArray(CompletableFuture[]::new));
            }
        });
    }
}