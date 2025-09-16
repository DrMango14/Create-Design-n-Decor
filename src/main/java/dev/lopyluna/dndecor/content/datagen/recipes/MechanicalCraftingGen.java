package dev.lopyluna.dndecor.content.datagen.recipes;

import com.google.common.base.Supplier;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.datagen.DnDecorRecipeProvider;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class MechanicalCraftingGen extends DnDecorRecipeProvider {

    
    public MechanicalCraftingGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(result);
    }

    class GeneratedRecipeBuilder {

        private String suffix;
        private final Supplier<ItemLike> result;
        private int amount;

        public GeneratedRecipeBuilder(Supplier<ItemLike> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
            return register(output -> {
                MechanicalCraftingRecipeBuilder b =
                        builder.apply(MechanicalCraftingRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = DnDecor.loc("mechanical_crafting/" + RegisteredObjectsHelper.getKeyOrThrow(result.get().asItem()).getPath() + suffix);
                b.build(output, location);
            });
        }
    }
}
