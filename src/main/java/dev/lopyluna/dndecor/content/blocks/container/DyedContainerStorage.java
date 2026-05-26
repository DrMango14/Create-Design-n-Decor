package dev.lopyluna.dndecor.content.blocks.container;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.simibubi.create.api.contraption.storage.item.WrapperMountedItemStorage;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.foundation.codec.CreateCodecs;
import dev.lopyluna.dndecor.register.DnDecorMountedStorageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class DyedContainerStorage extends WrapperMountedItemStorage<ItemStackHandler> {
    public static final MapCodec<DyedContainerStorage> CODEC = CreateCodecs.ITEM_STACK_HANDLER.xmap(
            DyedContainerStorage::new, storage -> storage.wrapped
    ).fieldOf("value");

    protected DyedContainerStorage(MountedItemStorageType<?> type, ItemStackHandler handler) {
        super(type, handler);
    }

    protected DyedContainerStorage(ItemStackHandler handler) {
        this(DnDecorMountedStorageTypes.CONTAINER.get(), handler);
    }

    @Override
    public void unmount(Level level, BlockState state, BlockPos pos, @Nullable BlockEntity be) {
        if (be instanceof DyedContainerBE container) container.applyInventoryToBlock(this.wrapped);
    }

    @Override
    public boolean handleInteraction(ServerPlayer player, Contraption contraption, StructureTemplate.StructureBlockInfo info) {
        return false;
    }

    public static DyedContainerStorage fromContainer(DyedContainerBE be) {
        return new DyedContainerStorage(copyToItemStackHandler(be.getInventoryOfBlock()));
    }
}
