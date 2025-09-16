package dev.lopyluna.dndecor.content.blocks.cogs;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.core.Direction.Axis;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DnDCogWheelBlock extends CogWheelBlock {
    boolean isLarge;
    public DyeColor color;
    public PartialModel customModel;

    public DnDCogWheelBlock(DyeColor color, boolean large, Properties properties) {
        super(large, properties);
        this.color = color;
        this.isLarge = large;
    }

    public DnDCogWheelBlock(PartialModel model, boolean large, Properties properties) {
        super(large, properties);
        this.customModel = model;
        this.isLarge = large;
    }

    @Override
    public boolean isLargeCog() {
        return isLarge;
    }

    @Override
    public boolean isSmallCog() {
        return !isLarge;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return (isLarge ? AllShapes.LARGE_GEAR : AllShapes.SMALL_GEAR).get(state.getValue(AXIS));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return isValidCogwheelPosition(ICogWheel.isLargeCog(state), worldIn, pos, state.getValue(AXIS));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof Player player) triggerShiftingGearsAdvancement(worldIn, pos, state, player);
    }

    protected void triggerShiftingGearsAdvancement(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide) return;

        Axis axis = state.getValue(DnDCogWheelBlock.AXIS);
        for (Axis perpendicular1 : Iterate.axes) {
            if (perpendicular1 == axis) continue;

            Direction d1 = Direction.get(Direction.AxisDirection.POSITIVE, perpendicular1);
            for (Axis perpendicular2 : Iterate.axes) {
                if (perpendicular1 == perpendicular2) continue;
                if (axis == perpendicular2) continue;

                Direction d2 = Direction.get(Direction.AxisDirection.POSITIVE, perpendicular2);
                for (int offset1 : Iterate.positiveAndNegative) for (int offset2 : Iterate.positiveAndNegative) {
                    BlockPos connectedPos = pos.relative(d1, offset1).relative(d2, offset2);
                    BlockState blockState = world.getBlockState(connectedPos);
                    if (!(blockState.getBlock() instanceof DnDCogWheelBlock)) continue;
                    if (blockState.getValue(DnDCogWheelBlock.AXIS) != axis) continue;
                    if (ICogWheel.isLargeCog(blockState) == isLarge) continue;

                    AllAdvancements.COGS.awardTo(player);
                }
            }
        }
    }

    public static boolean isValidCogwheelPosition(boolean large, LevelReader worldIn, BlockPos pos, Axis cogAxis) {
        for (Direction facing : Iterate.directions) {
            if (facing.getAxis() == cogAxis) continue;

            BlockPos offsetPos = pos.relative(facing);
            BlockState blockState = worldIn.getBlockState(offsetPos);
            if (blockState.hasProperty(AXIS) && facing.getAxis() == blockState.getValue(AXIS)) continue;
            if (ICogWheel.isLargeCog(blockState) || large && ICogWheel.isSmallCog(blockState)) return false;
        }
        return true;
    }

    protected Axis getAxisForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) return context.getClickedFace().getAxis();
        Level level = context.getLevel();
        BlockState stateBelow = level.getBlockState(context.getClickedPos().below());

        if (AllBlocks.ROTATION_SPEED_CONTROLLER.has(stateBelow) && isLargeCog())
            return stateBelow.getValue(SpeedControllerBlock.HORIZONTAL_AXIS) == Axis.X ? Axis.Z : Axis.X;

        BlockPos placedOnPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        BlockState placedAgainst = level.getBlockState(placedOnPos);

        Block block = placedAgainst.getBlock();
        if (ICogWheel.isSmallCog(placedAgainst)) return ((IRotate) block).getRotationAxis(placedAgainst);

        Axis preferredAxis = getPreferredAxis(context);
        return preferredAxis != null ? preferredAxis : context.getClickedFace().getAxis();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean shouldWaterlog = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(AXIS, getAxisForPlacement(context)).setValue(BlockStateProperties.WATERLOGGED, shouldWaterlog);
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
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.COLORED_COGWHEELS.get();
    }
}