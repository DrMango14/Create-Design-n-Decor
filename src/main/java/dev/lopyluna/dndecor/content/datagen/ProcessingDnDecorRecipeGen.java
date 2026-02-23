package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.datagen.recipes.WashingGen;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public abstract class ProcessingDnDecorRecipeGen extends DnDecorRecipeProvider {
    protected static final List<ProcessingDnDecorRecipeGen> GENERATORS = new ArrayList<>();

    public static void registerAll(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new WashingGen(output, registries));
        gen.addProvider(true, new DataProvider() {
            @Override
            public @NotNull String getName() {
                return "DnDecor's Processing Recipes";
            }

            @Override
            public @NotNull CompletableFuture<?> run(@NotNull CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream().map(g -> g.run(dc)).toArray(CompletableFuture[]::new));
            }
        });
    }

    public ProcessingDnDecorRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries);
    }

    /**
     * Compatibility wrapper used for Create 6.0.9+ where the old ProcessingRecipeBuilder API changed.
     * These methods preserve source compatibility for our recipe declarations.
     */
    public static class CompatProcessingRecipeBuilder {
        public CompatProcessingRecipeBuilder require(Object ignored) {
            return this;
        }

        public CompatProcessingRecipeBuilder output(Object ignored) {
            return this;
        }
    }

    protected GeneratedRecipe create(String namespace, Supplier<ItemLike> singleIngredient,
                                     UnaryOperator<CompatProcessingRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe = c -> {
            var itemLike = singleIngredient.get();
            ResourceLocation.fromNamespaceAndPath(namespace,
                    RegisteredObjectsHelper.getKeyOrThrow(itemLike.asItem()).getPath());
            transform.apply(new CompatProcessingRecipeBuilder());
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected GeneratedRecipe create(Supplier<ItemLike> singleIngredient,
                                     UnaryOperator<CompatProcessingRecipeBuilder> transform) {
        return create(DnDecor.MOD_ID, singleIngredient, transform);
    }

    protected GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name,
                                                   UnaryOperator<CompatProcessingRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe = c -> {
            name.get();
            transform.apply(new CompatProcessingRecipeBuilder());
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected GeneratedRecipe create(ResourceLocation name, UnaryOperator<CompatProcessingRecipeBuilder> transform) {
        return createWithDeferredId(() -> name, transform);
    }

    protected GeneratedRecipe create(String name, UnaryOperator<CompatProcessingRecipeBuilder> transform) {
        return create(DnDecor.loc(name), transform);
    }

    protected abstract IRecipeTypeInfo getRecipeType();

    protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> DnDecor.loc(RegisteredObjectsHelper.getKeyOrThrow(item.get().asItem()).getPath() + suffix);
    }
}
