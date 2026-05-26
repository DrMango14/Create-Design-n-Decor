package dev.lopyluna.dndecor.mixins.belts;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BeltBlockEntity.class, remap = false)
public interface BeltBlockEntityAccessor {
    @Accessor("itemHandler")
    IItemHandler getItemHandler();

    @Invoker("initializeItemHandler")
    void runInitializeItemHandler();
}
