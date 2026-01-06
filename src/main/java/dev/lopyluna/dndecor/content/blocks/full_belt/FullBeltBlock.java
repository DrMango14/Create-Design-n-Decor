package dev.lopyluna.dndecor.content.blocks.full_belt;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.*;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class FullBeltBlock extends BeltBlock {
    public FullBeltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        if (player.isShiftKeyDown() || !player.mayBuild())
            return InteractionResult.PASS;
        ItemStack heldItem = player.getItemInHand(handIn);

        boolean isWrench = AllItems.WRENCH.isIn(heldItem);
        boolean isConnector = DnDecorItems.BELT_CONNECTOR.isIn(heldItem);
        boolean isShaft = AllBlocks.SHAFT.isIn(heldItem);
        boolean isDye = heldItem.is(Tags.Items.DYES);
        boolean hasWater = GenericItemEmptying.emptyItem(world, heldItem, true)
                .getFirst()
                .getFluid()
                .isSame(Fluids.WATER);
        boolean isHand = heldItem.isEmpty() && handIn == InteractionHand.MAIN_HAND;

        if (isDye || hasWater)
            return onBlockEntityUse(world, pos,
                    be -> be.applyColor(DyeColor.getColor(heldItem)) ? InteractionResult.SUCCESS : InteractionResult.PASS);

        if (isConnector)
            return FullBeltSlicer.useConnector(state, world, pos, player, handIn, hit, new BeltSlicer.Feedback());
        if (isWrench)
            return FullBeltSlicer.useWrench(state, world, pos, player, handIn, hit, new BeltSlicer.Feedback());

        BeltBlockEntity belt = BeltHelper.getSegmentBE(world, pos);
        if (belt == null)
            return InteractionResult.PASS;

        if (PackageItem.isPackage(heldItem)) {
            ItemStack toInsert = heldItem.copy();
            IItemHandler handler = belt.getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .orElse(null);
            if (handler == null)
                return InteractionResult.PASS;
            ItemStack remainder = handler.insertItem(0, toInsert, false);
            if (remainder.isEmpty()) {
                heldItem.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        if (isHand) {
            BeltBlockEntity controllerBelt = belt.getControllerBE();
            if (controllerBelt == null)
                return InteractionResult.PASS;
            if (world.isClientSide)
                return InteractionResult.SUCCESS;
            MutableBoolean success = new MutableBoolean(false);
            controllerBelt.getInventory()
                    .applyToEachWithin(belt.index + .5f, .55f, (transportedItemStack) -> {
                        player.getInventory()
                                .placeItemBackInInventory(transportedItemStack.stack);
                        success.setTrue();
                        return TransportedItemStackHandlerBehaviour.TransportedResult.removeItem();
                    });
            if (success.isTrue())
                world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                        1f + world.random.nextFloat());
        }

        if (isShaft) {
            if (state.getValue(PART) != BeltPart.MIDDLE)
                return InteractionResult.PASS;
            if (world.isClientSide)
                return InteractionResult.SUCCESS;
            if (!player.isCreative())
                heldItem.shrink(1);
            KineticBlockEntity.switchToBlockState(world, pos, state.setValue(PART, BeltPart.PULLEY));
            return InteractionResult.SUCCESS;
        }

        if (AllBlocks.BRASS_CASING.isIn(heldItem)) {
            withBlockEntityDo(world, pos, be -> be.setCasingType(BeltBlockEntity.CasingType.BRASS));
            updateCoverProperty(world, pos, world.getBlockState(pos));

            SoundType soundType = AllBlocks.BRASS_CASING.getDefaultState()
                    .getSoundType(world, pos, player);
            world.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS,
                    (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);

            return InteractionResult.SUCCESS;
        }

        if (AllBlocks.ANDESITE_CASING.isIn(heldItem)) {
            withBlockEntityDo(world, pos, be -> be.setCasingType(BeltBlockEntity.CasingType.ANDESITE));
            updateCoverProperty(world, pos, world.getBlockState(pos));

            SoundType soundType = AllBlocks.ANDESITE_CASING.getDefaultState()
                    .getSoundType(world, pos, player);
            world.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS,
                    (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity be) {
        List<ItemStack> required = new ArrayList<>();
        if (state.getValue(PART) != BeltPart.MIDDLE) required.add(AllBlocks.SHAFT.asStack());
        if (state.getValue(PART) == BeltPart.START) required.add(DnDecorItems.BELT_CONNECTOR.asStack());
        if (required.isEmpty()) return ItemRequirement.NONE;
        return new ItemRequirement(ItemRequirement.ItemUseType.CONSUME, required);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos,
                                       Player player) {
        return DnDecorItems.BELT_CONNECTOR.asStack();
    }

    @Override
    public BlockEntityType<? extends BeltBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.BELT.get();
    }
}
