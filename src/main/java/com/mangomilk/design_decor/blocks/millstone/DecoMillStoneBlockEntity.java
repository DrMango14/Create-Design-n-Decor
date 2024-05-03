package com.mangomilk.design_decor.blocks.millstone;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class DecoMillStoneBlockEntity extends MillstoneBlockEntity {
    public DecoMillStoneBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

//    public class DecoMillstoneInventoryHandler extends CombinedInvWrapper {
//
//        public DecoMillstoneInventoryHandler() {
//            super(inputInv, outputInv);
//        }
//
//        @Override
//        public boolean isItemValid(int slot, ItemStack stack) {
//            if (outputInv == getHandlerFromIndex(getIndexForSlot(slot)))
//                return false;
//            return canProcess(stack) && super.isItemValid(slot, stack);
//        }
//
//        @Override
//        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
//            if (outputInv == getHandlerFromIndex(getIndexForSlot(slot)))
//                return stack;
//            if (!isItemValid(slot, stack))
//                return stack;
//            return super.insertItem(slot, stack, simulate);
//        }
//
//        @Override
//        public ItemStack extractItem(int slot, int amount, boolean simulate) {
//            if (inputInv == getHandlerFromIndex(getIndexForSlot(slot)))
//                return ItemStack.EMPTY;
//            return super.extractItem(slot, amount, simulate);
//        }
//
//    }
}
