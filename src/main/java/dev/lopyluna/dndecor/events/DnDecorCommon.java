package dev.lopyluna.dndecor.events;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import dev.lopyluna.dndecor.content.blocks.container.DyedContainerBE;
import dev.lopyluna.dndecor.mixins.belts.BeltBlockEntityAccessor;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class DnDecorCommon {

    @SubscribeEvent
    public static void registerCapabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event) {
        DyedContainerBE.registerCapabilities(event);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DnDecorBETypes.BELT.get(),
                (be, context) -> {
                    if (!BeltBlock.canTransportObjects(be.getBlockState())) return null;
                    if (!be.isRemoved() && ((BeltBlockEntityAccessor) be).getItemHandler() == null) ((BeltBlockEntityAccessor) be).runInitializeItemHandler();
                    return ((BeltBlockEntityAccessor) be).getItemHandler();
                }
        );

        for (var entry : DnDecorBETypes.STONE_TYPE_MILLSTONES.toArray()) event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, entry.get(), (be, context) -> be.capability);
    }
}
