package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.transport.BeltMovementHandler;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BeltMovementHandler.class, remap = false)
public abstract class BeltMovementHandlerMixin {
    @WrapOperation(method = "transportEntity(Lcom/simibubi/create/content/kinetics/belt/BeltBlockEntity;Lnet/minecraft/world/entity/Entity;Lcom/simibubi/create/content/kinetics/belt/transport/BeltMovementHandler$TransportedEntityInfo;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean transportEntity(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }
}
