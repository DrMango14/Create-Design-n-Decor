package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export=true)
@Mixin(value = BeltBlock.class, remap = false)
public abstract class BeltBlockMixin {

    @WrapOperation(method = "updateEntityAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean updateEntityAfterFallOn(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "canTransportObjects(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean canTransportObjects(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "initBelt(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean initBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "onRemove(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean onRemove(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "getBeltChain(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean getBeltChain(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }
}
