package dev.lopyluna.dndecor.content.blocks.stepped_lever;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

import static dev.lopyluna.dndecor.register.DnDecorShapes.shape;

@ParametersAreNonnullByDefault
public class SteppedLeverBlock extends FaceAttachedHorizontalDirectionalBlock implements IBE<SteppedLeverBlockEntity> {
    public SteppedLeverBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    public static final MapCodec<AnalogLeverBlock> CODEC = simpleCodec(AnalogLeverBlock::new);

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            addParticles(state, level, pos, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return onBlockEntityUse(level, pos, be -> {
            boolean sneak = player.isShiftKeyDown();
            be.changeState(sneak);
            float f = .25f + ((be.state + 5) / 15f) * .5f;
            level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.2F, f);
            return InteractionResult.SUCCESS;
        });
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return getBlockEntityOptional(blockAccess, pos).map(al -> al.state)
                .orElse(0);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return getConnectedDirection(blockState) == side ? getSignal(blockState, blockAccess, pos, side) : 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        withBlockEntityDo(worldIn, pos, be -> {
            if (be.state != 0 && rand.nextFloat() < 0.25F)
                addParticles(stateIn, worldIn, pos, 0.5F);
        });
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (isMoving || state.getBlock() == newState.getBlock()) return;
        withBlockEntityDo(level, pos, be -> {
            if (be.state != 0) updateNeighbors(state, level, pos);
            level.removeBlockEntity(pos);
        });
    }

    private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha) {
        var sDir = state.getValue(FACING).getOpposite();
        var cDir = getConnectedDirection(state).getOpposite();
        double d0 = (double) pos.getX() + 0.5D + 0.1D * (double) sDir.getStepX() + 0.2D * (double) cDir.getStepX();
        double d1 = (double) pos.getY() + 0.5D + 0.1D * (double) sDir.getStepY() + 0.2D * (double) cDir.getStepY();
        double d2 = (double) pos.getZ() + 0.5D + 0.1D * (double) sDir.getStepZ() + 0.2D * (double) cDir.getStepZ();
        worldIn.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), alpha), d0, d1, d2, 0.0D, 0.0D,
                0.0D);
    }

    static void updateNeighbors(BlockState state, Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, state.getBlock());
        world.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), state.getBlock());
    }


    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var face = state.getValue(FACE);
        var dir = Direction.fromAxisAndDirection(state.getValue(FACING).getAxis(), Direction.AxisDirection.POSITIVE);
        var dirF = state.getValue(FACING);
        return face == AttachFace.CEILING ?
                shape(3, 4, 0, 13, 16, 16).forHorizontalAxis().get(dir) : (face == AttachFace.FLOOR ?
                shape(3, 0, 0, 13, 12, 16).forHorizontalAxis().get(dir) :
                shape(3, 0, 0, 13, 16, 12).forHorizontal(Direction.SOUTH).get(dirF));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, FACE));
    }

    @Override
    public Class<SteppedLeverBlockEntity> getBlockEntityClass() {
        return SteppedLeverBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SteppedLeverBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.STEPPED_LEVER.get();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected @NotNull MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }



}
