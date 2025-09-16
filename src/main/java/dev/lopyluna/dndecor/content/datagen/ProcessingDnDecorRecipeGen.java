package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
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
                return CompletableFuture.allOf(GENERATORS.stream().map(gen -> gen.run(dc)).toArray(CompletableFuture[]::new));
            }
        });
    }

    public ProcessingDnDecorRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries);
    }

    protected <T extends ProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(String namespace, Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        DnDecorRecipeProvider.GeneratedRecipe generatedRecipe = c -> {
            var itemLike = singleIngredient.get();
            transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), ResourceLocation.fromNamespaceAndPath(namespace, RegisteredObjectsHelper.getKeyOrThrow(itemLike.asItem()).getPath())).withItemIngredients(Ingredient.of(itemLike))).build(c);
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected  <T extends ProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(DnDecor.MOD_ID, singleIngredient, transform);
    }

    protected <T extends ProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        DnDecorRecipeProvider.GeneratedRecipe generatedRecipe = 
                c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get())).build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected <T extends ProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(ResourceLocation name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return createWithDeferredId(() -> name, transform);
    }
    protected <T extends ProcessingRecipe<?>> DnDecorRecipeProvider.GeneratedRecipe create(String name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(DnDecor.loc(name), transform);
    }
    protected abstract IRecipeTypeInfo getRecipeType();

    protected <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer() {
        return getRecipeType().getSerializer();
    }

    protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> DnDecor.loc(RegisteredObjectsHelper.getKeyOrThrow(item.get().asItem()).getPath() + suffix);
    }
}
