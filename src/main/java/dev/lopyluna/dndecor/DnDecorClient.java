package dev.lopyluna.dndecor;

import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


import static dev.lopyluna.dndecor.DnDecor.MOD_ID;


public class DnDecorClient {
    @SuppressWarnings("removal")
    public DnDecorClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(DnDecorClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DnDecorPartialModels.init();
        DnDecorSpriteShifts.init();
    }
}
