package com.mangomilk.design_decor.blocks.large_boiler.cast_iron;

import com.mangomilk.design_decor.blocks.TagDependentDirectionalBlock;
import com.mangomilk.design_decor.registry.CDDBlocks;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CastIronLargeBoilerBlock extends TagDependentDirectionalBlock {

    public static final BooleanProperty EXTENSION = BooleanProperty.create("extension");

    public static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    public CastIronLargeBoilerBlock(Properties properties, TagKey<Item> itemTagKey) {
        super(properties, itemTagKey);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(EXTENSION, false));
    }

    public static final VoxelShape SHAPE = Block.box(-8.0D, -8.0D, -8.0D, 24.0D, 24.0D, 24.0D);

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection != Direction.fromAxisAndDirection(pState.getValue(FACING).getAxis(), Direction.AxisDirection.NEGATIVE))
            return pState;
        return pState.setValue(EXTENSION, pNeighborState.is(this));
    }

    public Direction.Axis getAxisForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).getValue(FACING).getAxis();
    }
    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
      return SHAPE;
  }

    @Override
    public RenderShape getRenderShape(BlockState p_54559_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_54582_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(EXTENSION));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {

        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (helper.matchesItem(itemInHand))
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) itemInHand.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        BlockPos pos = context.getClickedPos();
        assert stateForPlacement != null;
        Direction.Axis axis = stateForPlacement.getValue(FACING).getAxis();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (axis.choose(x, y, z) != 0)
                        continue;
                    BlockPos offset = new BlockPos(x, y, z);
                    if (offset.equals(BlockPos.ZERO))
                        continue;
                    BlockState occupiedState = context.getLevel()
                            .getBlockState(pos.offset(offset));
                    if (!occupiedState.getMaterial()
                            .isReplaceable())
                        return null;
                }
            }
        }

        if (context.getLevel()
                .getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)))
                .is(this))
            stateForPlacement = stateForPlacement.setValue(EXTENSION, true);

        return stateForPlacement;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.getBlockTicks()
                .hasScheduledTick(pos, this))
            level.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction.Axis axis = pState.getValue(FACING).getAxis();
        for (Direction side : Iterate.directions) {
            if (side.getAxis() == axis)
                continue;
            for (boolean secondary : Iterate.falseAndTrue) {
                Direction targetSide = secondary ? side.getClockWise(axis) : side;
                BlockPos structurePos = (secondary ? pPos.relative(side) : pPos).relative(targetSide);
                BlockState occupiedState = pLevel.getBlockState(structurePos);
                BlockState requiredStructure = CDDBlocks.CAST_IRON_BOILER_STRUCTURAL.getDefaultState()
                        .setValue(CastIronBoilerStructure.FACING, targetSide.getOpposite());
                if (occupiedState == requiredStructure)
                    continue;
                if (!occupiedState.getMaterial()
                        .isReplaceable()) {
                    pLevel.destroyBlock(pPos, false);
                    return;
                }
                pLevel.setBlockAndUpdate(structurePos, requiredStructure);
            }
        }
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper extends PoleHelper<Direction> {


        private PlacementHelper() {
            super(state -> state.getBlock() instanceof CastIronLargeBoilerBlock, state -> state.getValue(FACING).getAxis(), FACING);
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem
                    && ((BlockItem) i.getItem()).getBlock() instanceof CastIronLargeBoilerBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof CastIronLargeBoilerBlock || s.getBlock() instanceof CastIronLargeBoilerBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform()
                        .andThen(s -> pickCorrectBoiler(s, world, offset.getBlockPos())));
            return offset;
        }

    }

    @SuppressWarnings("unused")
    public static BlockState pickCorrectBoiler(BlockState stateForPlacement, Level level, BlockPos pos) {
        return stateForPlacement;
    }


}
