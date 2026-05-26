package dev.lopyluna.dndecor.events;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndecor.DnDecorUtils;
import dev.lopyluna.dndecor.content.blocks.flywheel.FreeSpinBlock;
import dev.lopyluna.dndecor.content.blocks.text_plate.TextPlateBlock;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import static dev.lopyluna.dndecor.DnDecor.MOD_ID;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MOD_ID)
public class DnDecorServer {

    @SubscribeEvent
    public static void onItemUseOnBlock(UseItemOnBlockEvent event) {
        var level = event.getLevel();
        var player = event.getPlayer();
        var blockPos = event.getPos();
        var state = level.getBlockState(blockPos);
        var face = event.getFace();
        var hand = event.getHand();
        var stack = event.getItemStack();
        var context = event.getUseOnContext();
        if (event.getUsePhase() == UseItemOnBlockEvent.UsePhase.ITEM_BEFORE_BLOCK) {

            if (stack.is(Tags.Items.DYES) || stack.is(Items.SPONGE) || stack.is(Items.WET_SPONGE) || stack.is(Items.WHITE_WOOL) || stack.is(Items.WATER_BUCKET)) {
                var dye = dyeUseOn(level, player, state, blockPos, face, hand, stack, context);
                if (dye.consumesAction()) event.cancelWithResult(DnDecorUtils.itemResult(dye));
            }

            var item = stack.getItem();
            for (var type : MaterialTypeProvider.stoneTypes) {
                if (item.equals(type.get().asItem())) {
                    var stone = stoneUseOn(type, level, player, state, blockPos, face, hand, stack, context);
                    if (stone.consumesAction()) event.cancelWithResult(DnDecorUtils.itemResult(stone));
                    break;
                }
            }
        }
    }
    public static InteractionResult changeBlock(Level pLevel, BlockPos pPos, BlockState pFrom, BlockState pTo, SoundEvent pSound) {
        return changeBlock(pLevel, pPos, pFrom, pTo, pSound, null, false, null);
    }
    public static InteractionResult changeBlock(Level pLevel, BlockPos pPos, BlockState pFrom, BlockState pTo, SoundEvent pSound, SoundEvent pSound2, boolean pParticles, BlockState pParticleState) {
        var newState = BlockHelper.copyProperties(pFrom, pTo);
        if (!pFrom.equals(newState)) {
            if (pLevel.setBlockAndUpdate(pPos, newState)) {
                var volume = pSound != null && pSound2 != null ? 0.75f : 1.0f;
                if (pSound != null) pLevel.playSound(null, pPos, pSound, SoundSource.BLOCKS, volume, 1.1f - pLevel.random.nextFloat() * .2f);
                if (pSound2 != null) pLevel.playSound(null, pPos, pSound2, SoundSource.BLOCKS, volume, 1.1f - pLevel.random.nextFloat() * .2f);
                if (pParticles && pParticleState != null) pLevel.addDestroyBlockEffect(pPos, pTo);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    public static InteractionResult stoneUseOn(NonNullSupplier<Block> type, Level pLevel, Player pPlayer, BlockState pState, BlockPos pPos, Direction pFace,
                                               InteractionHand pHand, ItemStack pStack, UseOnContext pContext) {
        var typeState = type.get().defaultBlockState();
        var sound = typeState.getSoundType();
        var block = pState.getBlock();
        if (block instanceof CrushingWheelBlock) return changeBlock(pLevel, pPos, pState, DnDecorBlocks.STONE_TYPE_CRUSHING_WHEELS.get(type).getDefaultState(), sound.getBreakSound(), sound.getPlaceSound(), true, typeState);
        if (block instanceof MillstoneBlock) return changeBlock(pLevel, pPos, pState, DnDecorBlocks.STONE_TYPE_MILLSTONE.get(type).getDefaultState(), sound.getBreakSound(), sound.getPlaceSound(), true, typeState);
        return InteractionResult.PASS;
    }


    public static InteractionResult dyeUseOn(Level pLevel, Player pPlayer, BlockState pState, BlockPos pPos, Direction pFace,
                                             InteractionHand pHand, ItemStack pStack, UseOnContext pContext) {
        var block = pState.getBlock();
        if (pStack.getItem() instanceof DyeItem dyeItem) {
            var color = dyeItem.getDyeColor();
            return switch (block) {
                case FlywheelBlock flywheelBlock when !(block instanceof FreeSpinBlock b && b.type == FreeSpinBlock.Type.LARGE_FAN) ->
                        changeBlock(pLevel, pPos, pState, DnDecorBlocks.DYED_FLYWHEELS.get(color).getDefaultState(), SoundEvents.DYE_USE);

                case FreeSpinBlock freeSpinBlock ->
                        changeBlock(pLevel, pPos, pState, DnDecorBlocks.DYED_LARGE_FANS.get(color).getDefaultState(), SoundEvents.DYE_USE);

                case FlapDisplayBlock flapDisplayBlock when pPlayer.isShiftKeyDown() ->
                        changeBlock(pLevel, pPos, pState, DnDecorBlocks.DYED_DISPLAY_BOARDS.get(color).getDefaultState(), SoundEvents.DYE_USE);

                case TextPlateBlock textPlate when pPlayer.isShiftKeyDown() ->
                        textPlate.applyDye(pLevel, pPos, color);

                default -> InteractionResult.PASS;
            };
        } else return switch (block) {
            case FlywheelBlock flywheelBlock when !(block instanceof FreeSpinBlock b && b.type == FreeSpinBlock.Type.LARGE_FAN) ->
                    changeBlock(pLevel, pPos, pState, AllBlocks.FLYWHEEL.getDefaultState(), SoundEvents.SPONGE_ABSORB);

            case FreeSpinBlock freeSpinBlock ->
                    changeBlock(pLevel, pPos, pState, DnDecorBlocks.LARGE_FAN.getDefaultState(), SoundEvents.SPONGE_ABSORB);

            case FlapDisplayBlock flapDisplayBlock ->
                    changeBlock(pLevel, pPos, pState, AllBlocks.DISPLAY_BOARD.getDefaultState(), SoundEvents.SPONGE_ABSORB);

            default -> InteractionResult.PASS;
        };
    }
}
