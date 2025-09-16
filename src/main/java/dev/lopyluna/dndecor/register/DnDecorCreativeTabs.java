package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;

@SuppressWarnings("all")
public class DnDecorCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> REGI = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_CREATIVE_TAB = REGI.register("base", () -> CreativeModeTab.builder()
            .title(DnDecorLangPartial.TITLE)
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
            .icon(DnDecorBlocks.BRASS_FRONTLIGHT::asStack).build());

    public static void register(IEventBus modEventBus) {
        REGI.register(modEventBus);
    }


    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(BASE_CREATIVE_TAB.getKey())) return;
        var holders = event.getParameters().holders();

        for (var supplier : MaterialTypeProvider.metalTypes) {
            var metal = supplier.get();
            if (tagHasAny(holders, metal.tag) || !metal.itemEntries.isEmpty()) continue;
            var bolts = DnDecorBlocks.METAL_TYPE_BOLTS.get(supplier);
            if (bolts != null) {
                removeEverywhere(event, bolts.CROSS);
                removeEverywhere(event, bolts.DASH);
                removeEverywhere(event, bolts.DOT);
                removeEverywhere(event, bolts.FLAT);
            }
            removeEverywhere(event, DnDecorBlocks.METAL_TYPE_FLOORS.get(supplier));
            removeEverywhere(event, DnDecorBlocks.METAL_TYPE_FRONTLIGHTS.get(supplier));
            removeEverywhere(event, DnDecorBlocks.METAL_TYPE_LARGE_CHAINS.get(supplier));
        }
    }


    private static void removeEverywhere(BuildCreativeModeTabContentsEvent event, BlockEntry<? extends Block> like) {
        if (like == null) return;
        var item = like.asItem();
        event.remove(item.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static boolean tagHasAny(HolderLookup.Provider holders, TagKey<Item> tag) {
        return holders.lookupOrThrow(Registries.ITEM).get(tag)
                .map(set -> set.iterator().hasNext())
                .orElse(false);
    }
}
