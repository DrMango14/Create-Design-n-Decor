package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ParametersAreNonnullByDefault
public class VelvetBlock extends Block {
    protected final DyeColor color;

    public VelvetBlock(Properties pProperties, DyeColor color) {
        super(pProperties);
        this.color = color;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        DyeColor color = DyeColor.getColor(stack);
        if (color != null) {
            if (!level.isClientSide) level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
            applyDye(state, level, pos, color);
            return ItemInteractionResult.SUCCESS;
        } else if (stack.is(Items.SPONGE) || stack.is(Items.WET_SPONGE) || stack.is(Items.WHITE_WOOL) || stack.is(Items.WATER_BUCKET)) {
            if (!level.isClientSide) level.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
            applyDye(state, level, pos, null);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void applyDye(BlockState state, Level world, BlockPos pos, @Nullable DyeColor color) {
        BlockState newState = (color == null ? DnDecorBlocks.DYED_VELVET_BLOCKS.get(DyeColor.WHITE) : DnDecorBlocks.DYED_VELVET_BLOCKS.get(color)).getDefaultState();
        newState = BlockHelper.copyProperties(state, newState);

        if (state != newState) {
            world.setBlockAndUpdate(pos, newState);
            return;
        }

        for (var d : Iterate.directions) {
            BlockPos offset = pos.relative(d);
            BlockState adjacentState = world.getBlockState(offset);
            Block block = adjacentState.getBlock();
            if (!(block instanceof VelvetBlock)) continue;
            if (state == adjacentState) continue;
            world.setBlockAndUpdate(offset, newState);
            return;
        }

        List<BlockPos> frontier = new ArrayList<>();
        frontier.add(pos);
        Set<BlockPos> visited = new HashSet<>();
        int timeout = 100;
        while (!frontier.isEmpty()) {
            if (timeout-- < 0) break;

            BlockPos currentPos = frontier.removeFirst();
            visited.add(currentPos);

            for (var d : Iterate.directions) {
                var offset = currentPos.relative(d);
                if (visited.contains(offset)) continue;
                var adjacentState = world.getBlockState(offset);
                var block = adjacentState.getBlock();
                if (!(block instanceof VelvetBlock)) continue;
                if (state != adjacentState) world.setBlockAndUpdate(offset, newState);
                frontier.add(offset);
                visited.add(offset);
            }
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance * 0.1F);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}