package dev.lopyluna.dndecor.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export=true)
@Mixin(value = FlapDisplayBlockEntity.class, remap = false)
public abstract class FlapDisplayBlockEntityMixin extends KineticBlockEntity {
    public FlapDisplayBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @WrapOperation(method = "updateControllerStatus()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState updateControllerStatusGetState(Level instance, BlockPos pos, Operation<BlockState> original) {
        var state = original.call(instance, pos);
        if (state.getBlock() instanceof FlapDisplayBlock) return BlockHelper.copyProperties(state, AllBlocks.DISPLAY_BOARD.getDefaultState());
        return state;
    }

    @ModifyVariable(method = "updateControllerStatus()V", at = @At("STORE"), name = "blockState")
    private BlockState updateControllerStatusState(BlockState state) {
        return state.getBlock() instanceof FlapDisplayBlock ? BlockHelper.copyProperties(state, AllBlocks.DISPLAY_BOARD.getDefaultState()) : state;
    }

    @WrapOperation(method = "getController()Lcom/simibubi/create/content/trains/display/FlapDisplayBlockEntity;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState getController(Level instance, BlockPos pos, Operation<BlockState> original) {
        var state = original.call(instance, pos);
        if (state.getBlock() instanceof FlapDisplayBlock) return BlockHelper.copyProperties(state, AllBlocks.DISPLAY_BOARD.getDefaultState());
        return state;
    }

    @ModifyVariable(method = "getController()Lcom/simibubi/create/content/trains/display/FlapDisplayBlockEntity;", at = @At("STORE"), name = "blockState")
    private BlockState getControllerState(BlockState state) {
        return state.getBlock() instanceof FlapDisplayBlock ? BlockHelper.copyProperties(state, AllBlocks.DISPLAY_BOARD.getDefaultState()) : state;
    }
}
