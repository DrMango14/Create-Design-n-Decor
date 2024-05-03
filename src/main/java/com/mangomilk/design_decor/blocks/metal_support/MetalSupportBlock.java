package com.mangomilk.design_decor.blocks.metal_support;

import com.mangomilk.design_decor.registry.CDDBlocks;
import com.mangomilk.design_decor.registry.CDDShapes;
import com.simibubi.create.content.kinetics.base.HorizontalAxisKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MetalSupportBlock extends Block {

    public static final Property<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public MetalSupportBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP,HORIZONTAL_AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {


        boolean isAtTop = !(context.getLevel().getBlockState(context.getClickedPos().above()).is(CDDBlocks.METAL_SUPPORT.get()));

        return this.defaultBlockState().setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getClockWise().getAxis()).setValue(TOP,isAtTop);
    }



    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {



        if(level.getBlockState(pos.above()).getBlock() instanceof MetalSupportBlock){
            level.setBlock(pos,state.setValue(TOP,false),3);
        } else   level.setBlock(pos,state.setValue(TOP,true),3);


        super.neighborChanged(state, level, pos, p_60512_, p_60513_, p_60514_);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {

        if(p_60555_.getValue(TOP))
            return CDDShapes.METAL_SUPPORT.get(p_60555_.getValue(HORIZONTAL_AXIS));


        return CDDShapes.WOODEN_SUPPORT;

    }
}
