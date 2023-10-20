package com.mangomilk.design_decor.base;

import javax.annotation.Nonnull;

import com.mangomilk.design_decor.CreateMMBuilding;
import com.mangomilk.design_decor.registry.MmbBlocks;

import com.simibubi.create.Create;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
@SuppressWarnings({"unused","removal"})
public class MmbBuildingJEI implements IModPlugin {



    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {


       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.GRANITE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.LIMESTONE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.SCORIA_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.SCORCHIA_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.TUFF_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.OCHRUM_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.VERIDIUM_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DRIPSTONE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DIORITE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DEEPSLATE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.CALCITE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.AZURINE_CRUSHING_WHEEL.get()
       // registration.addRecipeCatalyst(new ItemStack(MmbBlocks.CRIMSITE_CRUSHING_WHEEL.get()

        registration.addRecipeCatalyst(MmbBlocks.GRANITE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.LIMESTONE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.SCORIA_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.SCORCHIA_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.TUFF_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.OCHRUM_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.VERIDIUM_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.DRIPSTONE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.DIORITE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.DEEPSLATE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.CALCITE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.ASURINE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));
        registration.addRecipeCatalyst(MmbBlocks.CRIMSITE_CRUSHING_WHEEL.asStack(), new ResourceLocation(Create.ID, "crushing"));

        registration.addRecipeCatalyst(MmbBlocks.GRANITE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.LIMESTONE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.SCORIA_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.SCORCHIA_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.TUFF_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.OCHRUM_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.VERIDIUM_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.DRIPSTONE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.DIORITE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.DEEPSLATE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.CALCITE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.ASURINE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));
        registration.addRecipeCatalyst(MmbBlocks.CRIMSITE_MILLSTONE.asStack(), new ResourceLocation(Create.ID, "milling"));

    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return CreateMMBuilding.asResource("jei_plugin");
    }


}