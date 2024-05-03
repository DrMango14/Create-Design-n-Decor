package com.mangomilk.design_decor.blocks.railings;


import java.util.List;

import com.mangomilk.design_decor.registry.CDDShapes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RailingBlock extends Block {

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;


    public static final VoxelShape SHAPE_WEST = Block.box(0, 0, 0, 2, 16, 16);
    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 2);
    public static final VoxelShape SHAPE_EAST = Block.box(14, 0, 0, 16, 16, 16);
    public static final VoxelShape SHAPE_SOUTH = Block.box(0, 0, 14, 16, 16, 16);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public RailingBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false)
        );
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        ItemStack itemstack = context.getItemInHand();
        Player player = context.getPlayer();

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;



        BlockState state = this.defaultBlockState();

        state = state.setValue(WATERLOGGED, flag);

        Direction direction = context.getHorizontalDirection();

        switch(direction) {
            case NORTH:
                return state.setValue(NORTH,true);
            case SOUTH:
                return state.setValue(SOUTH,true);
            case EAST:
                return state.setValue(EAST,true);
            case WEST:
                return state.setValue(WEST,true);
            default:
                return state.setValue(NORTH,true);
        }
    }








    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(WATERLOGGED,NORTH,SOUTH,WEST,EAST);
    }
    public VoxelShape getShape(BlockState state, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {

        VoxelShape shape = CDDShapes.EMPTY;

        if(state.getValue(NORTH))
            shape = Shapes.or(shape, SHAPE_NORTH);

        if(state.getValue(SOUTH))
            shape = Shapes.or(shape, SHAPE_SOUTH);

        if(state.getValue(WEST))
            shape = Shapes.or(shape, SHAPE_WEST);

        if(state.getValue(EAST))
            shape = Shapes.or(shape, SHAPE_EAST);


        return shape;
    }
        @Override
    public FluidState getFluidState(BlockState p_51475_) {
        return p_51475_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51475_);
    }
    @Override
    public BlockState updateShape(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
        if (p_51461_.getValue(WATERLOGGED)) {
            p_51464_.scheduleTick(p_51465_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51464_));
        }

        return super.updateShape(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {


        int numero = 0;

        if(state.getValue(NORTH))
            numero++;
        if(state.getValue(SOUTH))
            numero++;
        if(state.getValue(EAST))
            numero++;
        if(state.getValue(WEST))
            numero++;
        ResourceLocation resourcelocation = this.getLootTable();
        LootParams lootcontext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
        ServerLevel serverlevel = lootcontext.getLevel();
        LootTable lootTable = serverlevel.getServer().getLootData().getLootTable(resourcelocation);


        ItemStack stack = lootTable.getRandomItems(lootcontext).get(0);
        stack.setCount(numero);


        return List.of(stack);


    }

}
