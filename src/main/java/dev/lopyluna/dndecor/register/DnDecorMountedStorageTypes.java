package dev.lopyluna.dndecor.register;

import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.lopyluna.dndecor.content.blocks.container.DyedContainerStorageType;

import java.util.function.Supplier;

import static dev.lopyluna.dndecor.DnDecor.REG;

@SuppressWarnings({"SameParameterValue", "unused"})
public class DnDecorMountedStorageTypes {
    public static final RegistryEntry<MountedItemStorageType<?>, DyedContainerStorageType> CONTAINER = simpleItem("container", DyedContainerStorageType::new);

    private static <T extends MountedItemStorageType<?>> RegistryEntry<MountedItemStorageType<?>, T> simpleItem(String name, Supplier<T> supplier) {
        return REG.mountedItemStorage(name, supplier).register();
    }
    private static <T extends MountedFluidStorageType<?>> RegistryEntry<MountedFluidStorageType<?>, T> simpleFluid(String name, Supplier<T> supplier) {
        return REG.mountedFluidStorage(name, supplier).register();
    }

    public static void register() {}
}
