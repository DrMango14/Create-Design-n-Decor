package com.mangomilk.design_decor.blocks;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class TagDependentDirectionalBlock extends WrenchableDirectionalBlock {

    private TagKey<Item> tag;
    public TagDependentDirectionalBlock(Properties properties, TagKey<Item> itemTagKey) {
        super(properties);
        this.tag = itemTagKey;
    }

    public boolean shouldHide() {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        return !tagManager.isKnownTagName(tag) || tagManager.getTag(tag).isEmpty();
    }
}
