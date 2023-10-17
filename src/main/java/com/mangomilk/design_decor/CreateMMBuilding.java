package com.mangomilk.design_decor;


import com.mangomilk.design_decor.base.MmbCreativeModeTab;
import com.mangomilk.design_decor.base.MmbSpriteShifts;
import com.mangomilk.design_decor.registry.DecoSoundEvents;
import com.mangomilk.design_decor.registry.MmbBlockEntities;
import com.mangomilk.design_decor.registry.MmbBlocks;
import com.mangomilk.design_decor.registry.MmbItems;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(CreateMMBuilding.MOD_ID)
public class CreateMMBuilding
{

    public static final String MOD_ID = "design_decor";
    public static final String NAME = "Create: Design n' Decor";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateMMBuilding.MOD_ID).creativeModeTab(()-> MmbCreativeModeTab.BUILDING);
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMMBuilding()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(eventBus);


        //

        MmbBlocks.register();
        MmbItems.register();
        DecoSoundEvents.register(eventBus);
        MmbBlockEntities.register();
        MmbSpriteShifts.init();


        //

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info(":3");
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
