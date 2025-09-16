package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider.MetalType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

import static dev.lopyluna.dndecor.register.DnDecorTags.commonItemTag;

@SuppressWarnings("unused")
public class AllMetalTypes {
    public static MetalType BRASS = new MetalType("Brass", SoundType.METAL, MapColor.TERRACOTTA_YELLOW, AllItems.BRASS_INGOT, commonItemTag("ingots/brass"));
    public static MetalType ANDESITE = new MetalType("Andesite", SoundType.STONE, MapColor.STONE, AllItems.ANDESITE_ALLOY);
    public static MetalType ZINC = new MetalType("Zinc", SoundType.METAL, MapColor.GLOW_LICHEN, AllItems.ZINC_INGOT, commonItemTag("ingots/zinc"));
    public static MetalType COPPER = new MetalType("Copper", SoundType.COPPER, MapColor.COLOR_ORANGE, () -> Items.COPPER_INGOT, commonItemTag("ingots/copper"));
    public static MetalType INDUSTRIAL = new MetalType("Industrial", SoundType.NETHERITE_BLOCK, MapColor.COLOR_GRAY, AllBlocks.INDUSTRIAL_IRON_BLOCK);
    public static MetalType GOLD = new MetalType("Gold", SoundType.METAL, MapColor.COLOR_YELLOW, () -> Items.GOLD_INGOT, commonItemTag("ingots/gold"));
    public static MetalType IRON = new MetalType("Iron", SoundType.METAL, MapColor.SNOW, () -> Items.IRON_INGOT, commonItemTag("ingots/iron"));
    public static MetalType NETHERITE = new MetalType("Netherite", SoundType.NETHERITE_BLOCK, MapColor.COLOR_GRAY, () -> Items.NETHERITE_INGOT, commonItemTag("ingots/netherite"));

    public static MetalType PLATINUM = new MetalType("Platinum", SoundType.METAL, MapColor.GLOW_LICHEN, commonItemTag("ingots/platinum"), List.of("unify", "modern_industrialization", "mythicmetals"));
    public static MetalType LEAD = new MetalType("Lead", SoundType.METAL, MapColor.COLOR_PURPLE, commonItemTag("ingots/lead"), List.of("unify", "tfmg", "mekanism", "thermal", "modern_industrialization"));
    public static MetalType SILVER = new MetalType("Silver", SoundType.METAL, MapColor.METAL, commonItemTag("ingots/silver"), List.of("unify", "thermal", "modern_industrialization", "mythicmetals"));
    public static MetalType TIN = new MetalType("Tin", SoundType.METAL, MapColor.METAL, commonItemTag("ingots/tin"), List.of("unify", "create_ironworks", "mekanism", "thermal", "modern_industrialization", "mythicmetals"));
    public static MetalType URANIUM = new MetalType("Uranium", DnDecorSoundTypes.METAL_HEAVY, MapColor.TERRACOTTA_GREEN, commonItemTag("ingots/uranium"), List.of("unify", "mekanism", "bigreactors", "modern_industrialization"));
    public static MetalType ALUMINUM = new MetalType("Aluminum", SoundType.COPPER, MapColor.METAL, commonItemTag("ingots/aluminum"), List.of("unify", "tfmg", "modern_industrialization"));
    public static MetalType NICKEL = new MetalType("Nickel", SoundType.METAL, MapColor.COLOR_YELLOW, commonItemTag("ingots/nickel"), List.of("unify", "tfmg", "thermal", "modern_industrialization"));
    public static MetalType TUNGSTEN = new MetalType("Tungsten", DnDecorSoundTypes.NETHERITE_HEAVY, MapColor.TERRACOTTA_LIGHT_GREEN, commonItemTag("ingots/tungsten"), List.of("unify", "createmetallurgy", "modern_industrialization"));
    public static MetalType STEEL = new MetalType("Steel", DnDecorSoundTypes.NETHERITE_HEAVY, MapColor.COLOR_LIGHT_GRAY, commonItemTag("ingots/steel"), List.of("unify", "createmetallurgy", "create_ironworks", "tfmg", "mekanism", "tconstruct", "modern_industrialization", "ad_astra", "mythicmetals"));
    public static MetalType ELECTRUM = new MetalType("Electrum", DnDecorSoundTypes.COPPER_HEAVY, MapColor.COLOR_YELLOW, commonItemTag("ingots/electrum"), List.of("unify", "thermal", "modern_industrialization"));
    public static MetalType BRONZE = new MetalType("Bronze", DnDecorSoundTypes.NETHERITE_HEAVY, MapColor.COLOR_ORANGE, commonItemTag("ingots/bronze"), List.of("unify", "create_ironworks", "mekanism", "thermal", "modern_industrialization", "mythicmetals"));
    public static MetalType CAST_IRON = new MetalType("Cast Iron", SoundType.NETHERITE_BLOCK, MapColor.COLOR_BLACK, commonItemTag("ingots/cast_iron"), List.of("unify", "tfmg", "createbigcannons"));
    public static MetalType INVAR = new MetalType("Invar", DnDecorSoundTypes.COPPER_HEAVY, MapColor.METAL, commonItemTag("ingots/invar"), List.of("unify", "thermal", "modern_industrialization"));
    public static MetalType CONSTANTAN = new MetalType("Constantan", SoundType.METAL, MapColor.COLOR_ORANGE, commonItemTag("ingots/constantan"), List.of("unify", "thermal"));
    public static MetalType WROUGHT_IRON = new MetalType("Wrought Iron", SoundType.NETHERITE_BLOCK, MapColor.COLOR_GRAY, commonItemTag("ingots/wrought_iron"), List.of("unify"));
    public static MetalType TARNISHED_GOLD = new MetalType("Tarnished Gold", SoundType.METAL, MapColor.TERRACOTTA_YELLOW, commonItemTag("ingots/tarnished_gold"), List.of("unify"));

    public static void register() {
    }
}
