package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class VelvetBlock extends Block {
    protected final DyeColor color;

    public VelvetBlock(Properties pProperties, DyeColor color) {
        super(pProperties);
        this.color = color;
    }


    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        DyeColor color = DyeColor.getColor(stack);
        if (color != null) {
            if (!level.isClientSide)
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
            applyDye(state, level, pos, hitResult.getLocation(), hitResult.getDirection(), color);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void applyDye(BlockState state, Level world, BlockPos pos, Vec3 hit, Direction direction, @Nullable DyeColor color) {
        BlockState newState = (color == null ? DnDecorBlocks.DYED_VELVET_BLOCKS.get(DyeColor.WHITE) : DnDecorBlocks.DYED_VELVET_BLOCKS.get(color)).getDefaultState();
        newState = BlockHelper.copyProperties(state, newState);

        if (state != newState) {
            world.setBlockAndUpdate(pos, newState);
            return;
        }

        var directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, hit, direction.getAxis());
        for (var d : directions) {
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

            BlockPos currentPos = frontier.remove(0);
            visited.add(currentPos);

            for (Direction d : Iterate.directions) {
                if (d.getAxis() == direction.getAxis()) continue;
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

}