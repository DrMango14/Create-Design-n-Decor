package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export=true)
@Mixin(value = CrushingWheelBlock.class, remap = false)
public abstract class CrushingWheelBlockMixin extends RotatedPillarKineticBlock {
    public CrushingWheelBlockMixin(Properties properties) {
        super(properties);
    }

    @WrapOperation(method = "updateControllers(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean updateControllersCheckInstance(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.CRUSHING_WHEEL) ? state.getBlock() instanceof CrushingWheelBlock : original.call(instance, state);
    }

    @WrapOperation(method = "canSurvive(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean canSurviveCheckInstance(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance.equals(AllBlocks.CRUSHING_WHEEL) ? (state.getBlock() instanceof CrushingWheelBlock) : original.call(instance, state);
    }
}
