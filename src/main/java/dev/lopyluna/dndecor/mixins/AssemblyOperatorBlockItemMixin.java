package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AssemblyOperatorBlockItem.class, remap = false)
public abstract class AssemblyOperatorBlockItemMixin {
    @WrapOperation(method = "operatesOn(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean transportEntity(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.BELT) ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }
}
