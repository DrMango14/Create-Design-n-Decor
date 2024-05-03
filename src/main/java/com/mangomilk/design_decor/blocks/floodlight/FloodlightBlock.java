package com.mangomilk.design_decor.blocks.floodlight;

//import com.mangomilk.design_decor.registry.CDDSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@SuppressWarnings({"unused","deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FloodlightBlock extends DirectionalBlock implements SimpleWaterloggedBlock, IWrenchable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TURNED_ON = BooleanProperty.create("turned_on");
    public static final BooleanProperty WRENCHED = BooleanProperty.create("wrenched");

    public static final VoxelShape SHAPE_DOWN = Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    public static final VoxelShape SHAPE_UP = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);

    public static final VoxelShape SHAPE_EAST = Block.box(0.0D, 3.0D, 3.0D, 8.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_WEST = Block.box(8.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
    public static final VoxelShape SHAPE_NORTH = Block.box(3.0D, 3.0D, 8.0D, 13.0D, 13.0D, 16.0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 8.0D);
    public FloodlightBlock(Properties p_52591_) {
        super(p_52591_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.UP).setValue(TURNED_ON, false).setValue(WRENCHED, false));
    }

    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        return switch (p_54561_.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            case UP -> SHAPE_UP;
            case DOWN -> SHAPE_DOWN;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos,
                                boolean pIsMoving) {
        if (pLevel.isClientSide)
            return;
        boolean beenWrenched = pState.getValue(WRENCHED);

        if (!beenWrenched && pLevel.hasNeighborSignal(pPos)) {
            pLevel.setBlock(pPos,pState.setValue(TURNED_ON,!pState.getValue(TURNED_ON)), 2);
        }
    }

    public void tick(BlockState p_221937_, ServerLevel p_221938_, BlockPos p_221939_, RandomSource p_221940_) {
        if (p_221937_.getValue(TURNED_ON) && p_221938_.hasNeighborSignal(p_221939_) != p_221937_.getValue(WRENCHED)) {
            p_221938_.setBlock(p_221939_, p_221937_.setValue(TURNED_ON, false), 2);
        }
    }
    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        InteractionResult onWrenched = IWrenchable.super.onWrenched(state, context);
        if (!onWrenched.consumesAction())
            return onWrenched;
        boolean isOn = state.getValue(TURNED_ON);

        context.getLevel().setBlock(context.getClickedPos(),state.setValue(TURNED_ON,!state.getValue(TURNED_ON)).setValue(WRENCHED,!state.getValue(WRENCHED)),2);

        playRotateSound(context.getLevel(), context.getClickedPos());

//        if (!isOn) {
//            context.getLevel().playLocalSound(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(),
//                    CDDSoundEvents.FLOODLIGHT_ON.get(), SoundSource.BLOCKS, 0.25F, Create.RANDOM.nextFloat() * 0.2F + 1.6F, false);
//        }
//        if (isOn) {
//            context.getLevel().playLocalSound(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(),
//                    CDDSoundEvents.FLOODLIGHT_OFF.get(), SoundSource.BLOCKS, 0.25F, Create.RANDOM.nextFloat() * 0.2F + 0.8F, false);
//        }
        return onWrenched;
    }


    @Override
    public FluidState getFluidState(BlockState p_51475_) {
        return p_51475_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51475_);
    }

    @Override
    public boolean isPathfindable(BlockState p_51456_, BlockGetter p_51457_, BlockPos p_51458_, PathComputationType p_51459_) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
        if (p_51461_.getValue(WATERLOGGED)) {
            p_51464_.scheduleTick(p_51465_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51464_));
        }

        return super.updateShape(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.add(WATERLOGGED,FACING,TURNED_ON,WRENCHED);
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
            blockstate = blockstate.setValue(FACING, direction1).setValue(WATERLOGGED, flag);
            return blockstate;
        }

        return Objects.requireNonNull(super.getStateForPlacement(p_58126_)).setValue(WATERLOGGED, flag);
    }


    public void FloodlightSoundOff(Level p_49713_, BlockPos p_49714_, @Nullable Direction p_49715_) {
        this.FloodlightSoundOff((Entity) null, p_49713_, p_49714_, p_49715_);
    }

    public boolean FloodlightSoundOff(@Nullable Entity p_152189_, Level p_152190_, BlockPos p_152191_, @Nullable Direction p_152192_) {
        BlockEntity blockentity = p_152190_.getBlockEntity(p_152191_);
        if (!p_152190_.isClientSide) {

//            p_152190_.playSound((Player)null, p_152191_, CDDSoundEvents.FLOODLIGHT_OFF.get(), SoundSource.BLOCKS, 0.25F, Create.RANDOM.nextFloat() * 0.2F + 0.8F);
            p_152190_.gameEvent(p_152189_, GameEvent.BLOCK_CHANGE, p_152191_);
            return true;
        } else {
            return false;
        }
    }

    public void FloodlightSoundOn(Level p_49713_, BlockPos p_49714_, @Nullable Direction p_49715_) {
        this.FloodlightSoundOn((Entity) null, p_49713_, p_49714_, p_49715_);
    }

    public boolean FloodlightSoundOn(@Nullable Entity p_152189_, Level p_152190_, BlockPos p_152191_, @Nullable Direction p_152192_) {
        BlockEntity blockentity = p_152190_.getBlockEntity(p_152191_);
        if (!p_152190_.isClientSide) {

//            p_152190_.playSound((Player)null, p_152191_, CDDSoundEvents.FLOODLIGHT_ON.get(), SoundSource.BLOCKS, 0.25F, Create.RANDOM.nextFloat() * 0.2F + 1.6F);
            p_152190_.gameEvent(p_152189_, GameEvent.BLOCK_CHANGE, p_152191_);
            return true;
        } else {
            return false;
        }
    }
}
