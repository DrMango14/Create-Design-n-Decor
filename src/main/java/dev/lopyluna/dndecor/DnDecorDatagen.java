package dev.lopyluna.dndecor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import dev.lopyluna.dndecor.content.datagen.DatagenTags;
import dev.lopyluna.dndecor.content.datagen.ProcessingDnDecorRecipeGen;
import dev.lopyluna.dndecor.content.datagen.recipes.MechanicalCraftingGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;
import static dev.lopyluna.dndecor.DnDecor.REGISTRATE;

@SuppressWarnings("unused")
public class DnDecorDatagen {
    @SuppressWarnings("all")
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new MechanicalCraftingGen(output, lookupProvider));

        //event.getGenerator().addProvider(true, REGISTRATE.setDataProvider(new RegistrateDataProvider(REGISTRATE, MOD_ID, event)));
        if (event.includeServer()) ProcessingDnDecorRecipeGen.registerAll(generator, output, lookupProvider);
    }

    private static void addExtraRegistrateData() {
        DatagenTags.addGenerators();
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
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
