package com.mangomilk.design_decor.blocks;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TagDependentDirectionalBlock extends WrenchableDirectionalBlock {

    private TagKey<Item> tag;
    public TagDependentDirectionalBlock(Properties properties, TagKey<Item> itemTagKey) {
        super(properties);
        this.tag = itemTagKey;
    }


    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (!shouldHide())
            super.fillItemCategory(tab, list);
    }

    public boolean shouldHide() {
        return Registry.ITEM.getTag(tag).isEmpty();
    }
}
