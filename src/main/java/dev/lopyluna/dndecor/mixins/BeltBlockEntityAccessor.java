package dev.lopyluna.dndecor.mixins;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.BeltInventory;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BeltBlockEntity.class, remap = false)
public interface BeltBlockEntityAccessor {
    @Accessor("inventory")
    BeltInventory getRawInventory();
    @Accessor("inventory")
    void setRawInventory(BeltInventory inventory);

  // @Accessor("itemHandler")
  // IItemHandler getItemHandler();

    @Invoker("initializeItemHandler")
    void runInitializeItemHandler();
}
