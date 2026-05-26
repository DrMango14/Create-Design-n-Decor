package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export=true)
@Mixin(value = CrushingWheelControllerBlock.class, remap = false)
public class CrushingWheelControllerBlockMixin {

    @WrapOperation(method = "lambda$updateSpeed$1",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean updateSpeed(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.CRUSHING_WHEEL ? (state.getBlock() instanceof CrushingWheelBlock) : original.call(instance, state);
    }
}
