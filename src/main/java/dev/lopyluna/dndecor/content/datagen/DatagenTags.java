package dev.lopyluna.dndecor.content.datagen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.lopyluna.dndecor.register.DnDecorTags;
import dev.lopyluna.dndecor.register.helpers.MetalTypeHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static dev.lopyluna.dndecor.DnDecor.REG;

@SuppressWarnings("deprecation")
public class DatagenTags {
    public static void addGenerators() {
        REG.addDataGenerator(ProviderType.BLOCK_TAGS, DatagenTags::genBlockTags);
        REG.addDataGenerator(ProviderType.ITEM_TAGS, DatagenTags::genItemTags);

    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
        var mineablePickaxe = prov.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var mineableAxe = prov.tag(BlockTags.MINEABLE_WITH_AXE);
        for (var tag : MetalTypeHelper.mineableBlocksTag) {
            var pickaxe = tag.get("pickaxe");
            if (pickaxe != null) mineablePickaxe.addOptionalTag(pickaxe);
            var axe = tag.get("axe");
            if (axe != null) mineableAxe.addOptionalTag(axe);
        }


        for (DnDecorTags.BlockTags tag : DnDecorTags.BlockTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);
        prov.tag(DnDecorTags.commonItemTag("create/crushing_wheels")).add(AllBlocks.CRUSHING_WHEEL.asItem());
        prov.tag(DnDecorTags.commonItemTag("create/millstones")).add(AllBlocks.MILLSTONE.asItem());
        prov.tag(DnDecorTags.commonItemTag("create/flywheels")).add(AllBlocks.FLYWHEEL.asItem());
        prov.tag(DnDecorTags.commonItemTag("create/display_boards")).add(AllBlocks.DISPLAY_BOARD.asItem());

        for (DnDecorTags.ItemTags tag : DnDecorTags.ItemTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }
}
