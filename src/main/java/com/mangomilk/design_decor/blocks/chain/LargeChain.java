package com.mangomilk.design_decor.blocks.chain;

//import com.mangomilk.design_decor.registry.CDDSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings({"unused"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LargeChain extends ChainBlock implements SimpleWaterloggedBlock, IWrenchable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final float AABB_MIN = 4;
    protected static final float AABB_MAX = 12;

    protected static final VoxelShape Y_AXIS_AABB = Block.box(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(4, 4, 0, 12, 12, 16);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0, 4, 4, 16, 12, 12);

    public static final int placementHelperId = PlacementHelpers.register(new LargeChain.PlacementHelper());

    public LargeChain(Properties p_55926_) {
        super(p_55926_.noCollission().noOcclusion().isSuffocating(LargeChain::never).requiresCorrectToolForDrops().strength(5.0F, 6.0F)
//                .sound(new SoundType(1f, .95f, CDDSoundEvents.LARGE_CHAIN_BREAK.get(),
//                CDDSoundEvents.LARGE_CHAIN_STEP.get(), CDDSoundEvents.LARGE_CHAIN_PLACE.get(),
//                CDDSoundEvents.LARGE_CHAIN_HIT.get(), CDDSoundEvents.LARGE_CHAIN_FALL.get()))
		);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getShape(BlockState p_51470_, BlockGetter p_51471_, BlockPos p_51472_, CollisionContext p_51473_) {
        switch ((Direction.Axis)p_51470_.getValue(AXIS)) {
            case X:
            default:
                return X_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_51454_) {
        FluidState fluidstate = p_51454_.getLevel().getFluidState(p_51454_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(p_51454_)).setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
        if (p_51461_.getValue(WATERLOGGED)) {
            p_51464_.scheduleTick(p_51465_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51464_));
        }

        return super.updateShape(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51468_) {
        p_51468_.add(WATERLOGGED).add(AXIS);
    }

    @Override
    public FluidState getFluidState(BlockState p_51475_) {
        return p_51475_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51475_);
    }

    @Override
    public boolean isPathfindable(BlockState p_51456_, BlockGetter p_51457_, BlockPos p_51458_, PathComputationType p_51459_) {
        return false;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper extends PoleHelper<Direction.Axis> {

        private PlacementHelper() {
            super(state -> state.getBlock() instanceof LargeChain, state -> state.getValue(AXIS), AXIS);
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem
                    && ((BlockItem) i.getItem()).getBlock() instanceof LargeChain;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof LargeChain;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform()
                        .andThen(s -> pickCorrectLargeChain(s, world, offset.getBlockPos())));
            return offset;
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (pPlayer == null)
            return InteractionResult.PASS;

        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (helper.matchesItem(itemInHand))
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) itemInHand.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }

    public static BlockState pickCorrectLargeChain(BlockState stateForPlacement, Level level, BlockPos pos) {
        return stateForPlacement;
    }

    private static boolean never(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }
}
