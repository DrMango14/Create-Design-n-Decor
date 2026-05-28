package dev.lopyluna.dndecor.mixins.belts;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
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
@Mixin(value = BeltBlock.class, remap = false)
public abstract class BeltBlockMixin {

    @WrapOperation(method = "updateEntityAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean updateEntityAfterFallOn(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "canTransportObjects(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean canTransportObjects(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "initBelt(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean initBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "onRemove(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean onRemove(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "getBeltChain(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean getBeltChain(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return instance == AllBlocks.BELT ? state.getBlock() instanceof BeltBlock : original.call(instance, state);
    }

    @WrapOperation(method = "useItemOn(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/ItemInteractionResult;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean useItemOnIsIn(ItemEntry<?> instance, ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ? state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR, stack) : original.call(instance, stack) : original.call(instance, stack);
    }

    @WrapOperation(method = "getRequiredItems(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;)Lcom/simibubi/create/content/schematics/requirement/ItemRequirement;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;asStack()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack getRequiredItemsAsStack(ItemEntry<?> instance, Operation<ItemStack> original, @Local(argsOnly = true) BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ? state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR) : original.call(instance) : original.call(instance);
    }

    @WrapOperation(method = "getCloneItemStack(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/HitResult;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;asStack()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack getCloneItemStackAsStack(ItemEntry<?> instance, Operation<ItemStack> original, @Local(argsOnly = true) BlockState state) {
        return instance == AllItems.BELT_CONNECTOR ? state.getBlock() instanceof FullBeltBlock ? original.call(DnDecorItems.BELT_CONNECTOR) : original.call(instance) : original.call(instance);
    }
}
