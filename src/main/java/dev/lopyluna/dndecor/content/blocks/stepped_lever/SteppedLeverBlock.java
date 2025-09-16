package dev.lopyluna.dndecor.content.blocks.stepped_lever;


import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SteppedLeverBlock extends FaceAttachedHorizontalDirectionalBlock implements IBE<SteppedLeverBlockEntity> {
    public SteppedLeverBlock(Properties p_i48402_1_) {
        super(p_i48402_1_);
    }




    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,InteractionHand p_60507_, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            addParticles(state, worldIn, pos, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return onBlockEntityUse(worldIn, pos, be -> {
            boolean sneak = player.isShiftKeyDown();
            be.changeState(sneak);
            float f = .25f + ((be.state + 5) / 15f) * .5f;
            worldIn.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.2F, f);
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
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (isMoving || state.getBlock() == newState.getBlock())
            return;
        withBlockEntityDo(worldIn, pos, be -> {
            if (be.state != 0)
                updateNeighbors(state, worldIn, pos);
            worldIn.removeBlockEntity(pos);
        });
    }

    private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha) {
        Direction direction = state.getValue(FACING)
                .getOpposite();
        Direction direction1 = getConnectedDirection(state).getOpposite();
        double d0 =
                (double) pos.getX() + 0.5D + 0.1D * (double) direction.getStepX() + 0.2D * (double) direction1.getStepX();
        double d1 =
                (double) pos.getY() + 0.5D + 0.1D * (double) direction.getStepY() + 0.2D * (double) direction1.getStepY();
        double d2 =
                (double) pos.getZ() + 0.5D + 0.1D * (double) direction.getStepZ() + 0.2D * (double) direction1.getStepZ();
        worldIn.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), alpha), d0, d1, d2, 0.0D, 0.0D,
                0.0D);
    }

    static void updateNeighbors(BlockState state, Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, state.getBlock());
        world.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), state.getBlock());
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        AttachFace face = state.getValue(FACE);
        Direction direction = state.getValue(FACING);
        VoxelShape shape = face == AttachFace.CEILING ? DnDecorShapes.STEPPED_LEVER_CEILING.get(direction.getOpposite()) : (face == AttachFace.FLOOR ? DnDecorShapes.STEPPED_LEVER.get(direction.getOpposite()) : DnDecorShapes.STEPPED_LEVER_WALL.get(direction));
        if(shape !=null)
            return shape;

       return super.getShape(state, level, pos, context);
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



}
