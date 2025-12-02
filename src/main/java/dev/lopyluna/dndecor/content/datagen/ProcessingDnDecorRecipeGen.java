package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe.Serializer;
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
import net.minecraft.world.item.crafting.Ingredient;
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
                CompletableFuture<?>[] futures = GENERATORS.stream().map(gen -> gen.run(dc)).toArray(CompletableFuture[]::new);
                return CompletableFuture.allOf(futures);
            }
        });
    }

    public ProcessingDnDecorRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries);
    }

    protected <T extends StandardProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(String namespace, Supplier<ItemLike> singleIngredient, UnaryOperator<StandardProcessingRecipe.Builder<T>> transform) {
        Serializer<T> serializer = getSerializer();
        DnDecorRecipeProvider.GeneratedRecipe generatedRecipe = c -> {
            var itemLike = singleIngredient.get();
            StandardProcessingRecipe.Builder<T> builder = new StandardProcessingRecipe.Builder<>(serializer.factory(), ResourceLocation.fromNamespaceAndPath(namespace, RegisteredObjectsHelper.getKeyOrThrow(itemLike.asItem()).getPath()));
            transform.apply(builder.withItemIngredients(Ingredient.of(itemLike))).build(c);
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected  <T extends StandardProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(Supplier<ItemLike> singleIngredient, UnaryOperator<StandardProcessingRecipe.Builder<T>> transform) {
        return create(DnDecor.MOD_ID, singleIngredient, transform);
    }

    protected <T extends StandardProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name, UnaryOperator<StandardProcessingRecipe.Builder<T>> transform) {
        Serializer<T> serializer = getSerializer();
        DnDecorRecipeProvider.GeneratedRecipe generatedRecipe = 
            c -> {
                StandardProcessingRecipe.Builder<T> builder = new StandardProcessingRecipe.Builder<>(serializer.factory(), name.get());
                transform.apply(builder).build(c);
            };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected <T extends StandardProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(ResourceLocation name, UnaryOperator<StandardProcessingRecipe.Builder<T>> transform) {
        return createWithDeferredId(() -> name, transform);
    }
    protected <T extends StandardProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(String name, UnaryOperator<StandardProcessingRecipe.Builder<T>> transform) {
        return create(DnDecor.loc(name), transform);
    }
    protected abstract IRecipeTypeInfo getRecipeType();

    protected <T extends StandardProcessingRecipe<?>> Serializer<T> getSerializer() {
        return getRecipeType().getSerializer();
    }

    protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> DnDecor.loc(RegisteredObjectsHelper.getKeyOrThrow(item.get().asItem()).getPath() + suffix);
    }
}
