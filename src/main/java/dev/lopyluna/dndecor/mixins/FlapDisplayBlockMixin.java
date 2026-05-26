package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;

@Debug(export=true)
@Mixin(value = FlapDisplayBlock.class, remap = false)
public abstract class FlapDisplayBlockMixin {
    @WrapOperation(method = "updateColumn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState updateControllerStatus(Level instance, BlockPos pos, Operation<BlockState> original) {
        var state = original.call(instance, pos);
        if (state.getBlock() instanceof FlapDisplayBlock) return BlockHelper.copyProperties(state, AllBlocks.DISPLAY_BOARD.getDefaultState());
        return state;
    }

    @Inject(method = "canConnect", at = @At("HEAD"), cancellable = true)
    private void canConnect(BlockState state, BlockState other, CallbackInfoReturnable<Boolean> cir) {
        if (other.getBlock() instanceof FlapDisplayBlock) cir.setReturnValue(state.getValue(HORIZONTAL_FACING) == other.getValue(HORIZONTAL_FACING));
    }
}
