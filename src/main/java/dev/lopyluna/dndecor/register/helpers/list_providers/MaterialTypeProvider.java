package dev.lopyluna.dndecor.register.helpers.list_providers;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndecor.register.DnDecorStoneTypes;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class MaterialTypeProvider {

    public static Map<NonNullSupplier<Block>, String> stoneTypesRegister = new HashMap<>();
    public static List<NonNullSupplier<Block>> stoneTypes = new ArrayList<>();

    public static List<NonNullSupplier<MetalType>> metalTypes = new ArrayList<>();

    public static void checkStoneTypeList() {
        for (var stone : DnDecorStoneTypes.values()) {
            if (stone == null) continue;
            if (stone.baseBlock == null) continue;
            if (!MaterialTypeProvider.stoneTypes.contains(stone.baseBlock)) {
                MaterialTypeProvider.stoneTypesRegister.putIfAbsent(stone.baseBlock, Lang.asId(stone.name()).replace("_block", ""));
                MaterialTypeProvider.stoneTypes.add(stone.baseBlock);
            }
        }
        for (var stone : AllPaletteStoneTypes.values()) {
            if (stone == null) continue;
            if (stone.baseBlock == null) continue;
            if (!MaterialTypeProvider.stoneTypes.contains(stone.baseBlock)) {
                MaterialTypeProvider.stoneTypesRegister.putIfAbsent(stone.baseBlock, Lang.asId(stone.name()).replace("_block", ""));
                MaterialTypeProvider.stoneTypes.add(stone.baseBlock);
            }
        }
    }

    public static Item getResolvedItem(ResourceLocation loc) {
        var item = BuiltInRegistries.ITEM.get(loc);
        if (item == Items.AIR) System.out.println("⚠ Item not yet registered: " + loc + " is " + item);
        return item;
    }

    public static List<String> NA = new ArrayList<>(List.of("NA"));

    public static class MetalType {
        public final String id;
        public final String type;
        public final SoundType sound;
        public final MapColor color;
        public final TagKey<Item> tag;

        public final List<Object> itemEntries = new ArrayList<>();
        public final List<String> modIDs;

        public MetalType(String type, SoundType sound, MapColor color, TagKey<Item> tag, List<String> modIDs) {
            this(type, sound, color, null, tag, modIDs);
        }

        public MetalType(String type, SoundType sound, MapColor color, NonNullSupplier<ItemLike> item, TagKey<Item> tag) {
            this(type, sound, color, item != null ? List.of(item) : List.of(), tag, new ArrayList<>());
        }

        public MetalType(String type, SoundType sound, MapColor color, ItemProviderEntry<?> item, TagKey<Item> tag) {
            this(type, sound, color, item != null ? List.of(item) : List.of(), tag, new ArrayList<>());
        }

        public MetalType(String type, SoundType sound, MapColor color, NonNullSupplier<ItemLike> item) {
            this(type, sound, color, item != null ? List.of(item) : List.of(), null, new ArrayList<>());
        }

        public MetalType(String type, SoundType sound, MapColor color, ItemProviderEntry< ?> item) {
            this(type, sound, color, item != null ? List.of(item) : List.of(), null, new ArrayList<>());
        }

        public MetalType(String type, SoundType sound, MapColor color, List<Object> itemEntries, TagKey<Item> tag, List<String> modIDs) {
            this.id = type.toLowerCase().replace(" ", "_");
            this.type = type;
            this.sound = sound;
            this.color = color;
            this.tag = tag;
            this.modIDs = modIDs;
            if (itemEntries != null) this.itemEntries.addAll(itemEntries);
            if (!this.modIDs.contains("NA")) metalTypes.add(() -> this);
        }

        public boolean requireMods() {
            return !this.modIDs.isEmpty();
        }

        public MetalType entries(Object... entries) {
            this.itemEntries.addAll(List.of(entries));
            return this;
        }

        public DataIngredient getIngredient() {
            if (tag != null) return DataIngredient.tag(tag);

            List<ItemStack> stacks = new ArrayList<>();
            for (var entry : itemEntries) {
                var itemLike = resolveItemLike(entry);
                if (itemLike == null || itemLike.asItem() == Items.AIR) continue;
                ItemStack stack = itemLike.asItem().getDefaultInstance();
                if (!stack.isEmpty()) stacks.add(stack);
            }

            if (!stacks.isEmpty()) return DataIngredient.ingredient(Ingredient.of(stacks.stream()), stacks.get(0).getItem());
            return null;
        }

        private ItemLike resolveItemLike(Object entry) {
            if (entry instanceof ItemProviderEntry<?> provider) return provider.get();
            if (entry instanceof Supplier<?> supplier) return supplier.get() instanceof ItemLike il ? il : null;
            return null;
        }
    }
}
