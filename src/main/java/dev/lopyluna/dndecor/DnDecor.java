package dev.lopyluna.dndecor;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.lopyluna.dndecor.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mod(DnDecor.MOD_ID)
public class DnDecor {
    public static final String NAME = "Design n' Decor";
    public static final String MOD_ID = "dndecor";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final boolean LOAD_ALL_METALS = true;

    public static final CreateRegistrate REG = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));

    static {
        REG.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(create(item))));
    }

    public DnDecor(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        REG.registerEventListeners(modEventBus);

        AllMetalTypes.register();

        DnDecorStoneTypes.register(REG);
        DnDecorLangPartial.init();
        DnDecorTags.init();
        DnDecorItems.register();
        DnDecorBlocks.register();
        DnDecorBETypes.register();
        DnDecorCreativeTabs.register(modEventBus);

        DnDecorConfigs.register(modLoadingContext, modContainer);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(EventPriority.LOWEST, DnDecorCreativeTabs::addCreative);
        modEventBus.addListener(EventPriority.LOWEST, DnDecorDatagen::gatherData);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DnDecorCreativeTabs.BASE_CREATIVE_TAB.getKey())) {
            List<ItemStack> stacks = new ArrayList<>();
            for (RegistryEntry<Item, Item> entry : REG.getAll(Registries.ITEM)) {
                Item item = entry.get();
                if (item instanceof BlockItem) continue;
                if (item instanceof BucketItem) continue;
                var stack = item.getDefaultInstance();
                if (stack.isEmpty()) continue;
                if (stack.is(DnDecorTags.ItemTags.PALETTE_BLOCKS.tag)) {
                    stacks.add(stack);
                    continue;
                }
                event.accept(stack);
            }
            for (RegistryEntry<Block, Block> entry : REG.getAll(Registries.BLOCK)) {
                var block = entry.get();
                var item = block.asItem();
                var stack = item.getDefaultInstance();
                if (stack.isEmpty()) continue;
                if (stack.is(DnDecorTags.ItemTags.PALETTE_BLOCKS.tag)) {
                    stacks.add(stack);
                    continue;
                }
                event.accept(stack);
            }
            for (var stack : stacks) event.accept(stack);
        }
    }

    public static LangBuilder lang() {
        return new LangBuilder(MOD_ID);
    }

    public static ResourceLocation loc(String loc) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, loc);
    }

    public static ResourceLocation emptyLoc() {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "empty");
    }

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof IRotate) return new KineticStats(block);
        }
        return null;
    }
}
