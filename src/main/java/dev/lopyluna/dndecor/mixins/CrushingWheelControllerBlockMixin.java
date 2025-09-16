package dev.lopyluna.dndecor.mixins;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Debug(export=true)
@Mixin(value = CrushingWheelControllerBlock.class, remap = false)
public abstract class CrushingWheelControllerBlockMixin extends DirectionalBlock implements IBE<CrushingWheelControllerBlockEntity> {
    protected CrushingWheelControllerBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author _
     * @reason WrapOperation doesnt want to work with me ;-;
     */
    @Overwrite
    public void updateSpeed(BlockState state, LevelAccessor world, BlockPos pos) {
        withBlockEntityDo(world, pos, be -> {
            if (!state.getValue(CrushingWheelControllerBlock.VALID)) {
                if (be.crushingspeed != 0) {
                    be.crushingspeed = 0;
                    be.sendData();
                }
                return;
            }

            for (Direction d : Iterate.directions) {
                BlockState neighbour = world.getBlockState(pos.relative(d));
                if (!(neighbour.getBlock() instanceof CrushingWheelBlock)) continue;
                if (neighbour.getValue(BlockStateProperties.AXIS) == d.getAxis()) continue;
                if (!(world.getBlockEntity(pos.relative(d)) instanceof CrushingWheelBlockEntity wheelBE)) continue;
                be.crushingspeed = Math.abs(wheelBE.getSpeed() / 50f);
                be.sendData();

                wheelBE.award(AllAdvancements.CRUSHING_WHEEL);
                if (wheelBE.getSpeed() > 255) wheelBE.award(AllAdvancements.CRUSHER_MAXED);
                break;
            }
        });
    }

    //@WrapOperation(method = "updateSpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V",
    //        at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    //public boolean updateSpeedCheckInstance(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
    //    return instance.get() instanceof CrushingWheelBlock ? !(state.getBlock() instanceof CrushingWheelBlock) : original.call(instance, state);
    //}
}
