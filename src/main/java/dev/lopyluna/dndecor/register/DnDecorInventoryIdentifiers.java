package dev.lopyluna.dndecor.register;

import com.simibubi.create.api.packager.InventoryIdentifier;
import dev.lopyluna.dndecor.content.blocks.container.DyedContainerBE;

public class DnDecorInventoryIdentifiers {
    public static void registerDefaults() {
        InventoryIdentifier.REGISTRY.register(DnDecorBlocks.CONTAINER.get(), (level, state, face) ->
                level.getBlockEntity(face.getPos()) instanceof DyedContainerBE be ? be.getInvId() : null);
        DnDecorBlocks.DYED_CONTAINERS.forEach(entry -> InventoryIdentifier.REGISTRY.register(entry.get(), (level, state, face) ->
                level.getBlockEntity(face.getPos()) instanceof DyedContainerBE be ? be.getInvId() : null));
        DnDecorBlocks.DYED_SOLID_CONTAINERS.forEach(entry -> InventoryIdentifier.REGISTRY.register(entry.get(), (level, state, face) ->
                level.getBlockEntity(face.getPos()) instanceof DyedContainerBE be ? be.getInvId() : null));
    }
}
