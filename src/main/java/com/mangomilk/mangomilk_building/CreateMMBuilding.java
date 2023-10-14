package com.mangomilk.mangomilk_building;


import com.mangomilk.mangomilk_building.base.MmbCreativeModeTab;
import com.mangomilk.mangomilk_building.base.MmbSpriteShifts;
import com.mangomilk.mangomilk_building.registry.MmbBlockEntities;
import com.mangomilk.mangomilk_building.registry.MmbBlocks;
import com.mangomilk.mangomilk_building.registry.MmbItems;
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

    public static final String MOD_ID = "mangomilk_building";
    public static final String NAME = "Create: MangoMilk Building";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateMMBuilding.MOD_ID).creativeModeTab(()-> MmbCreativeModeTab.BUILDING);
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateMMBuilding()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());


        //

        MmbBlocks.register();
        MmbItems.register();
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
