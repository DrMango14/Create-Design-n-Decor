package com.mangomilk.design_decor.blocks.industrial_gear;

import com.mangomilk.design_decor.registry.CDDBlocks;
import com.simibubi.create.content.decoration.encasing.EncasableBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IndustrialGearBlock extends ShaftBlock implements ICogWheel, EncasableBlock {
    boolean isLarge;

    VoxelShape largeY = Shapes.join(Block.box(-2, 2, -2, 18, 14, 18),
            Block.box(5, 0, 5, 11, 16, 11), BooleanOp.OR);
    VoxelShape smallY = Shapes.join(Block.box(1, 2, 1, 15, 14, 15),
            Block.box(5, 0, 5, 11, 16, 11), BooleanOp.OR);

    VoxelShape largeX = Shapes.join(Block.box(2, -2, -2, 14, 18, 18),
            Block.box(0, 5, 5, 16, 11, 11), BooleanOp.OR);
    VoxelShape smallX = Shapes.join(Block.box(2, 1, 1, 14, 15, 15),
            Block.box(0, 5, 5, 16, 11, 11), BooleanOp.OR);

    VoxelShape largeZ = Shapes.join(Block.box(-2, -2, 2, 18, 18, 14),
            Block.box(5, 5, 0, 11, 11, 16), BooleanOp.OR);
    VoxelShape smallZ = Shapes.join(Block.box(1, 1, 2, 15, 15, 14),
            Block.box(5, 5, 0, 11, 11, 16), BooleanOp.OR);

    protected IndustrialGearBlock(boolean large, Properties properties) {
        super(properties);
        isLarge = large;
    }

    public static IndustrialGearBlock small(Properties properties) {
        return new IndustrialGearBlock(false, properties);
    }

    public static IndustrialGearBlock large(Properties properties) {
        return new IndustrialGearBlock(true, properties);
    }

    @Override
    public boolean isLargeCog() {
        return isLarge;
    }

    @Override
    public boolean isSmallCog() {
        return !isLarge;
    }

//    @Override
//    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {
//        super.fillItemCategory(pTab, pItems);
//        CDDBlocks.LARGE_COGWHEEL.is(this);
//    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(AXIS) == Direction.Axis.X) {
            return (isLarge ? largeX : smallX);
        }
        if (state.getValue(AXIS) == Direction.Axis.Z) {
            return (isLarge ? largeZ : smallZ);
        }
        state.getValue(AXIS);
        return (isLarge ? largeY : smallY);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return isValidCogwheelPosition(ICogWheel.isLargeCog(state), worldIn, pos, state.getValue(AXIS));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof Player player)
            triggerShiftingGearsAdvancement(worldIn, pos, state, player);
    }

    protected void triggerShiftingGearsAdvancement(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide || player == null)
            return;

        Direction.Axis axis = state.getValue(IndustrialGearBlock.AXIS);
        for (Direction.Axis perpendicular1 : Iterate.axes) {
            if (perpendicular1 == axis)
                continue;

            Direction d1 = Direction.get(Direction.AxisDirection.POSITIVE, perpendicular1);
            for (Direction.Axis perpendicular2 : Iterate.axes) {
                if (perpendicular1 == perpendicular2)
                    continue;
                if (axis == perpendicular2)
                    continue;

                Direction d2 = Direction.get(Direction.AxisDirection.POSITIVE, perpendicular2);
                for (int offset1 : Iterate.positiveAndNegative) {
                    for (int offset2 : Iterate.positiveAndNegative) {
                        BlockPos connectedPos = pos.relative(d1, offset1)
                                .relative(d2, offset2);
                        BlockState blockState = world.getBlockState(connectedPos);
                        if (!(blockState.getBlock() instanceof IndustrialGearBlock))
                            continue;
                        if (blockState.getValue(IndustrialGearBlock.AXIS) != axis)
                            continue;
                        if (ICogWheel.isLargeCog(blockState) == isLarge)
                            continue;

                        AllAdvancements.COGS.awardTo(player);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {
        if (player.isShiftKeyDown() || !player.mayBuild())
            return InteractionResult.PASS;

        ItemStack heldItem = player.getItemInHand(hand);
        InteractionResult result = tryEncase(state, world, pos, heldItem, player, hand, ray);
        if (result.consumesAction())
            return result;

        return InteractionResult.PASS;
    }

    public static boolean isValidCogwheelPosition(boolean large, LevelReader worldIn, BlockPos pos, Direction.Axis cogAxis) {
        for (Direction facing : Iterate.directions) {
            if (facing.getAxis() == cogAxis)
                continue;

            BlockPos offsetPos = pos.relative(facing);
            BlockState blockState = worldIn.getBlockState(offsetPos);
            if (blockState.hasProperty(AXIS) && facing.getAxis() == blockState.getValue(AXIS))
                continue;

            if (ICogWheel.isLargeCog(blockState) || large && ICogWheel.isSmallCog(blockState))
                return false;
        }
        return true;
    }

    protected Direction.Axis getAxisForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown())
            return context.getClickedFace()
                    .getAxis();

        Level world = context.getLevel();
        BlockState stateBelow = world.getBlockState(context.getClickedPos()
                .below());

        BlockPos placedOnPos = context.getClickedPos()
                .relative(context.getClickedFace()
                        .getOpposite());
        BlockState placedAgainst = world.getBlockState(placedOnPos);

        Block block = placedAgainst.getBlock();
        if (ICogWheel.isSmallCog(placedAgainst))
            return ((IRotate) block).getRotationAxis(placedAgainst);

        Direction.Axis preferredAxis = getPreferredAxis(context);
        return preferredAxis != null ? preferredAxis
                : context.getClickedFace()
                .getAxis();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean shouldWaterlog = context.getLevel()
                .getFluidState(context.getClickedPos())
                .getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(AXIS, getAxisForPlacement(context))
                .setValue(BlockStateProperties.WATERLOGGED, shouldWaterlog);
    }

    @Override
    public float getParticleTargetRadius() {
        return isLargeCog() ? 1.125f : .65f;
    }

    @Override
    public float getParticleInitialRadius() {
        return isLargeCog() ? 1f : .75f;
    }

    @Override
    public boolean isDedicatedCogWheel() {
        return true;
    }

}
