package dev.lopyluna.dndecor.content.datagen.recipes;

import com.simibubi.create.api.data.recipe.DeployingRecipeGen;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;

@SuppressWarnings("unused")
public class DeployGen extends DeployingRecipeGen {
    GeneratedRecipe COPPER_FLOORS = copperChain(DnDecorBlocks.COPPER_FLOORS);

    public DeployGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }
}
