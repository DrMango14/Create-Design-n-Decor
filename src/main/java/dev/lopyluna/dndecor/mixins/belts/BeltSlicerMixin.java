package dev.lopyluna.dndecor.mixins.belts;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlicer;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.lopyluna.dndecor.content.blocks.full_belt.FullBeltBlock;
import dev.lopyluna.dndecor.register.DnDecorItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export=true)
@Mixin(value = BeltSlicer.class, remap = false)
public class BeltSlicerMixin {

    @WrapOperation(method = "useWrench(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean useWrench(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }
    @WrapOperation(method = "useConnector(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean useConnector(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "useWrench(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
    private static boolean useWrenchIsIn(ItemEntry<?> instance, ItemStack stack, Operation<Boolean> original, BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ?
                state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR, stack) : original.call(instance, stack) : original.call(instance, stack);
    }
    @WrapOperation(method = "useWrench(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;asStack()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack useWrenchStack(ItemEntry<?> instance, Operation<ItemStack> original, BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ?
                state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR) : original.call(instance) : original.call(instance);
    }
    @WrapOperation(method = "useConnector(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;Lcom/simibubi/create/content/kinetics/belt/BeltSlicer$Feedback;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;asStack()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack useConnectorStack(ItemEntry<?> instance, Operation<ItemStack> original, BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ?
                state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR) : original.call(instance) : original.call(instance);
    }
}
