package com.mangomilk.design_decor.base;

import javax.annotation.Nonnull;

import com.mangomilk.design_decor.DesignDecor;
import com.mangomilk.design_decor.registry.CDDBlocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class CDDBuildingJEI implements IModPlugin {



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


        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.GRANITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.LIMESTONE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.SCORIA_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.SCORCHIA_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.TUFF_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.OCHRUM_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.VERIDIUM_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DRIPSTONE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DIORITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DEEPSLATE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.CALCITE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.ASURINE_CRUSHING_WHEEL.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "crushing")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.CRIMSITE_CRUSHING_WHEEL.get()), type));


        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.GRANITE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.LIMESTONE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.SCORIA_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.SCORCHIA_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.TUFF_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.OCHRUM_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.VERIDIUM_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DRIPSTONE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DIORITE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.DEEPSLATE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.CALCITE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.ASURINE_MILLSTONE.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "milling")).ifPresent(type -> registration.addRecipeCatalyst(new ItemStack(CDDBlocks.CRIMSITE_MILLSTONE.get()), type));

    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return DesignDecor.asResource("jei_plugin");
    }


}