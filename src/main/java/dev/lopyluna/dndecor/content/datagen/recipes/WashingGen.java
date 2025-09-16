package dev.lopyluna.dndecor.content.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.lopyluna.dndecor.content.datagen.ProcessingDnDecorRecipeGen;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import dev.lopyluna.dndecor.register.DnDecorStoneTypes;
import dev.lopyluna.dndecor.register.DnDecorTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.DyeColor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WashingGen extends ProcessingDnDecorRecipeGen {
    GeneratedRecipe FLYWHEEL = create("flywheel", b -> b
            .require(DnDecorTags.commonItemTag("assets/create/dyed_flywheels"))
            .output(AllBlocks.FLYWHEEL));
    GeneratedRecipe DISPLAY_BOARD = create("display_board", b -> b
            .require(DnDecorTags.commonItemTag("assets/create/dyed_display_boards"))
            .output(AllBlocks.DISPLAY_BOARD));
    GeneratedRecipe STONE_METAL = create("stone_metal", b -> b
            .require(DnDecorTags.modItemTag("dyed_stone_metal_decor"))
            .output(DnDecorBlocks.STONE_METAL));
    GeneratedRecipe VELVET_BLOCK = create("velvet_block", b -> b
            .require(DnDecorTags.modItemTag("dyed_velvet_block"))
            .output(DnDecorBlocks.DYED_VELVET_BLOCKS.get(DyeColor.WHITE)));
    GeneratedRecipe WEATHERED_LIMESTONE = create("weathered_limestone", b -> b
            .require(AllPaletteStoneTypes.LIMESTONE.baseBlock.get())
            .output(DnDecorStoneTypes.WEATHERED_LIMESTONE.baseBlock.get()));

    public WashingGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SPLASHING;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

    }
}
