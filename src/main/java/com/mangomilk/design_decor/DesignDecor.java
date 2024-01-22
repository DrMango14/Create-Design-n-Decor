package com.mangomilk.design_decor;


import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mangomilk.design_decor.base.CDDCreativeModeTab;
import com.mangomilk.design_decor.registry.CDDSpriteShifts;
import com.mangomilk.design_decor.registry.CDDSoundEvents;
import com.mangomilk.design_decor.registry.CDDBlockEntities;
import com.mangomilk.design_decor.registry.CDDBlocks;
import com.mangomilk.design_decor.registry.CDDItems;
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


@Mod(DesignDecor.MOD_ID)
public class DesignDecor
{

    public static final String MOD_ID = "design_decor";
    public static final String NAME = "Create: Design n' Decor";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(DesignDecor.MOD_ID).creativeModeTab(()-> CDDCreativeModeTab.BUILDING);
    public static final Logger LOGGER = LogUtils.getLogger();

    public DesignDecor()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(eventBus);


        //

        CDDBlocks.register();
        CDDBlocks.DecoTags.init();
        CDDItems.register();
        CDDSoundEvents.register(eventBus);
        CDDBlockEntities.register();
        CDDSpriteShifts.init();
        CDDPartialModels.init();


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
