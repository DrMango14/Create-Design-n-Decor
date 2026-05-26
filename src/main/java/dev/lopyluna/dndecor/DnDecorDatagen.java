package dev.lopyluna.dndecor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import dev.lopyluna.dndecor.content.datagen.DatagenTags;
import dev.lopyluna.dndecor.content.datagen.DnDecorDatamapProvider;
import dev.lopyluna.dndecor.content.datagen.DnDecorRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;
import static dev.lopyluna.dndecor.DnDecor.REG;

@SuppressWarnings({"unused", "SameParameterValue"})
public class DnDecorDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        DnDecor.DYE_DEPOT = ModList.get().isLoaded("dye_depot");
        if (event.getMods().contains(MOD_ID)) addExtraRegistrateData();
    }

    @SuppressWarnings("all")
    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(MOD_ID)) return;
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DnDecorDatamapProvider(output, lookupProvider));
        if (event.includeServer()) DnDecorRecipeProvider.registerAllProcessing(generator, output, lookupProvider);
    }

    private static void addExtraRegistrateData() {
        DatagenTags.addGenerators();
        REG.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            provideDefaultLang("tooltips", langConsumer);

        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/dndecor/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
