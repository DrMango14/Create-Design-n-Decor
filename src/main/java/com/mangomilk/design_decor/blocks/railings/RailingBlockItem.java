package com.mangomilk.design_decor.blocks.railings;

import com.mangomilk.design_decor.registry.CDDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.mangomilk.design_decor.blocks.railings.RailingBlock.*;

public class RailingBlockItem extends BlockItem {
    public RailingBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().above();

        Direction direction = context.getHorizontalDirection();

        if(context.getClickedFace() == Direction.UP)
            if(level.getBlockState(pos).getBlock() instanceof RailingBlock){
                BlockState state = level.getBlockState(pos);

                boolean north = state.getValue(NORTH);
                boolean south = state.getValue(RailingBlock.SOUTH);
                boolean west = state.getValue(RailingBlock.WEST);
                boolean east = state.getValue(RailingBlock.EAST);

                boolean canPlace = false;

                if(direction == Direction.NORTH&&!north){
                    state = state.setValue(NORTH,true);
                    canPlace = true;
                }
                if(direction == Direction.SOUTH&&!south){
                    state = state.setValue(SOUTH,true);
                    canPlace = true;
                }
                if(direction == Direction.WEST&&!west){
                    state = state.setValue(WEST,true);
                    canPlace = true;
                }
                if(direction == Direction.EAST&&!east){
                    state = state.setValue(EAST,true);
                    canPlace = true;
                }

                if(canPlace){

                    level.setBlock(pos,state,3);

                    context.getItemInHand().shrink(1);
                    level.playSound((Player)null, pos, getPlaceSound(state), SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        return super.useOn(context);
    }
}
