package com.mangomilk.mangomilk_building.base;

import javax.annotation.Nonnull;

import com.mangomilk.mangomilk_building.CreateMMBuilding;
import com.mangomilk.mangomilk_building.registry.MmbBlocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
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


        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.GRANITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.LIMESTONE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.SCORIA_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.SCORCHIA_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.TUFF_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.OCHRUM_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.VERIDIUM_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DRIPSTONE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DIORITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.DEEPSLATE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.CALCITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.ASURINE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(MmbBlocks.CRIMSITE_CRUSHING_WHEEL.get()), type));
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return CreateMMBuilding.asResource("jei_plugin");
    }


}