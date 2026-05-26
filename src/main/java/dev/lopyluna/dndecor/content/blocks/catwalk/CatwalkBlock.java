package dev.lopyluna.dndecor.content.blocks.catwalk;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("NullableProblems")
public class CatwalkBlock extends Block implements SimpleWaterloggedBlock, IWrenchable {
    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 1, 8);
    private static final VoxelShape[] SHAPES = new VoxelShape[8];
    static {
        for (int i = 0; i < 8; i++) SHAPES[i] = Block.box(0, (i + 1) * 2 - 3, 0, 16, (i + 1) * 2, 16);
    }

    public CatwalkBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(LAYER, 8));
    }
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (!player.isShiftKeyDown() && player.mayBuild() && placementHelper.matchesItem(stack)) {
            placementHelper.getOffset(player, level, state, pos, hitResult).placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(LAYER) - 1];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(LAYER, WATERLOGGED));
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        return IWrenchable.super.updateAfterWrenched(newState, context).setValue(LAYER, ((newState.getValue(LAYER)) % 8) + 1);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        var list = new ArrayList<VoxelShape>();
        super.getOcclusionShape(state, level, pos).forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                list.add(Shapes.box(Math.max(minX, 0.0), Math.max(minY, 0.0), Math.max(minZ, 0.0), Math.min(maxX, 1.0), Math.min(maxY, 1.0), Math.min(maxZ, 1.0))));
        return list.stream().reduce((a, b) -> Shapes.join(a, b, BooleanOp.OR)).orElse(Shapes.empty()).optimize();
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var level = ctx.getLevel();
        var pos = ctx.getClickedPos();
        FluidState fluidstate = level.getFluidState(pos);
        boolean flag = fluidstate.getType() == Fluids.WATER;
        var state = defaultBlockState().setValue(WATERLOGGED, flag);

        var face = ctx.getClickedFace();
        if (face == Direction.DOWN) return state;
        if (face == Direction.UP) return state.setValue(LAYER, 1);
        var adjState = level.getBlockState(pos.relative(face.getOpposite()));
        if (adjState.hasProperty(LAYER)) return state.setValue(LAYER, adjState.getValue(LAYER));
        return state.setValue(LAYER, Mth.clamp(Math.round(((float) ctx.getClickLocation().y - pos.getY())*8f + 0.5f), 1, 8));
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState otherState, Direction side) {
        var self = otherState.is(this);
        return (self && state.getValue(LAYER).equals(otherState.getValue(LAYER)) && side.getAxis().isHorizontal()) ||
                (self && side == Direction.DOWN && state.getValue(LAYER) == 1 && otherState.getValue(LAYER) == 8) ||
                super.skipRendering(state, otherState, side);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem b && b.getBlock() instanceof CatwalkBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof CatwalkBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    Direction.Axis.Y, dir -> world.getBlockState(pos.relative(dir)).canBeReplaced());
            if (directions.isEmpty()) return PlacementOffset.fail();
            return PlacementOffset.success(pos.relative(directions.getFirst()), s -> s.setValue(LAYER, state.getValue(LAYER)));
        }
    }
}
