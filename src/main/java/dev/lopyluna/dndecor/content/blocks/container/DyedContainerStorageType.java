package dev.lopyluna.dndecor.content.blocks.container;

import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DyedContainerStorageType extends MountedItemStorageType<DyedContainerStorage> {
    public DyedContainerStorageType() {
        super(DyedContainerStorage.CODEC);
    }

    @Override @Nullable
    public DyedContainerStorage mount(Level level, BlockState state, BlockPos pos, @Nullable BlockEntity be) {
        return be instanceof DyedContainerBE container ? DyedContainerStorage.fromContainer(container) : null;
    }
}
