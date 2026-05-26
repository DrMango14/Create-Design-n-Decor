package dev.lopyluna.dndecor.mixins.belts;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.impl.contraption.BlockMovementChecksImpl;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockMovementChecksImpl.class, remap = false)
public class BlockMovementChecksImplMixin {

    @WrapOperation(method = "isMovementAllowedFallback(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean isMovementAllowedFallback(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }
}
