package com.mangomilk.design_decor.blocks.catwalks;

import com.mangomilk.design_decor.blocks.BoilerBlock;
//import com.mangomilk.design_decor.blocks.large_boiler.brass.BrassLargeBoilerBlock;
import com.mangomilk.design_decor.registry.CDDShapes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.copycat.CopycatPanelBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CatwalkBlock extends Block implements IWrenchable, SimpleWaterloggedBlock {


    public static final BooleanProperty DOWN = BooleanProperty.create("turned_on");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    public CatwalkBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(DOWN, false));
    }


    public VoxelShape getShape(BlockState state, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
       if(state.getValue(DOWN))
           return CDDShapes.CATWALK_DOWN;

       return CDDShapes.CATWALK;
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(DOWN,WATERLOGGED);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = IWrenchable.super.onWrenched(state, context);
        if (!onWrenched.consumesAction())
            return onWrenched;
        boolean isOn = state.getValue(DOWN);

        context.getLevel().setBlock(context.getClickedPos(),state.setValue(DOWN,!state.getValue(DOWN)),2);

        playRotateSound(context.getLevel(), context.getClickedPos());


        return onWrenched;
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {

        if (!player.isShiftKeyDown() && player.mayBuild()) {
            ItemStack heldItem = player.getItemInHand(hand);
            IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
            if (placementHelper.matchesItem(heldItem)) {
                placementHelper.getOffset(player, world, state, pos, ray)
                        .placeInWorld(world, (BlockItem) heldItem.getItem(), player, hand, ray);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, ray);
    }
    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem
                    && ((BlockItem) i.getItem()).getBlock() instanceof CatwalkBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof CatwalkBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    Direction.UP
                            .getAxis(),
                    dir -> world.getBlockState(pos.relative(dir))
                            .getMaterial()
                            .isReplaceable());

            boolean down = state.getValue(DOWN);


            if (directions.isEmpty())
                return PlacementOffset.fail();
            else {
                return PlacementOffset.success(pos.relative(directions.get(0)),
                        s -> s.setValue(DOWN,down));
            }
        }
    }
    //////
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
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_58126_) {
        FluidState fluidstate = p_58126_.getLevel().getFluidState(p_58126_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;

        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = p_58126_.getLevel();
        BlockPos blockpos = p_58126_.getClickedPos();
        Direction[] adirection = p_58126_.getNearestLookingDirections();

        for(Direction direction : adirection) {
            Direction direction1 = direction.getOpposite();
            blockstate = blockstate.setValue(WATERLOGGED, flag);
            return blockstate;
        }

        return Objects.requireNonNull(super.getStateForPlacement(p_58126_)).setValue(WATERLOGGED, flag);
    }

}
