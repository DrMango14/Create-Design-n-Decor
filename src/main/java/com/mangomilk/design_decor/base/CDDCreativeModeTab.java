package com.mangomilk.design_decor.base;

import com.mangomilk.design_decor.DesignDecor;
import com.mangomilk.design_decor.registry.CDDBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;

import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;

import java.util.Collection;

public class CDDCreativeModeTab extends CreativeModeTab {
	public CDDCreativeModeTab() {
		super(ItemGroupUtil.expandArrayAndGetId(), DesignDecor.MOD_ID);
	}

	public static final CreativeModeTab BUILDING = new CDDCreativeModeTab();

	@Override
	public ItemStack makeIcon() {
		return CDDBlocks.MOYAI_SIGN.asStack();
	}

	@Override
	public void fillItemList(NonNullList<ItemStack> items) {
		addItems(items, true);
		addBlocks(items);
		addItems(items, false);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Create: Design n' Decor");
	}

	protected Collection<RegistryEntry<Item>> registeredItems() {
		return DesignDecor.REGISTRATE.getAll(Registry.ITEM.key());
	}

	public void addBlocks(NonNullList<ItemStack> items) {
		for (RegistryEntry<Item> entry : registeredItems())
			if (entry.get() instanceof BlockItem blockItem)
				blockItem.fillItemCategory(this, items);
	}

	public void addItems(NonNullList<ItemStack> items, boolean specialItems) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		for (RegistryEntry<Item> entry : registeredItems()) {
			Item item = entry.get();
			if (!(item instanceof BlockItem)) {
				ItemStack stack = new ItemStack(item);
				BakedModel model = itemRenderer.getModel(stack, null, null, 0);
				if (model.isGui3d() == specialItems)
					item.fillItemCategory(this, items);
			}
		}
	}
}
