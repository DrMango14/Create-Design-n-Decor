package dev.lopyluna.dndecor;

import dev.lopyluna.dndecor.register.client.DnDecorPartialModels;
import dev.lopyluna.dndecor.register.client.DnDecorSpriteShifts;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class DnDecorClient {

    public DnDecorClient(IEventBus modEventBus) {
        modEventBus.addListener(DnDecorClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DnDecorPartialModels.init();
        DnDecorSpriteShifts.init();
    }
}
