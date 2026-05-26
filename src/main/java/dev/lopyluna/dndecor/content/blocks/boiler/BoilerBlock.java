package dev.lopyluna.dndecor.content.blocks.boiler;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import dev.lopyluna.dndecor.register.DnDecorShapes;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.createmod.catnip.data.Pair;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("NullableProblems")
@ParametersAreNonnullByDefault
public class BoilerBlock extends RotatedPillarBlock implements IWrenchable, IBE<BoilerBlockEntity> {
    public static final EnumProperty<BoilerSize> SIZE = EnumProperty.create("size", BoilerSize.class);
    public final MaterialTypeProvider.MetalType metal;
    public BoilerBlock(MaterialTypeProvider.MetalType metal, Properties properties) {
        super(properties.noOcclusion().dynamicShape());
        this.metal = metal;
        registerDefaultState(defaultBlockState().setValue(SIZE, BoilerSize.SMALL).setValue(AXIS, Direction.Axis.Z));
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var size = state.getValue(SIZE);
        if (size == BoilerSize.SMALL) return IWrenchable.super.onSneakWrenched(state, context);
        var newState = state.setValue(SIZE, size.shrink());
        if (!newState.canSurvive(level, context.getClickedPos())) return InteractionResult.FAIL;
        var pos = context.getClickedPos();

        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(newState, context));
        checkBlocks(level, level.getBlockState(pos), pos);
        if (level.getBlockState(pos) != state) AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var level = context.getLevel();
        var newState = state.setValue(SIZE, state.getValue(SIZE).grow());
        if (newState.getValue(SIZE) == state.getValue(SIZE)) return InteractionResult.FAIL;
        if (newState.getValue(SIZE) == BoilerSize.LARGE && !hasRequiredLargeSpace(level, context.getClickedPos(), newState)) return InteractionResult.FAIL;
        if (!newState.canSurvive(level, context.getClickedPos())) return InteractionResult.FAIL;
        var pos = context.getClickedPos();

        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(newState, context));
        checkBlocks(level, level.getBlockState(pos), pos);
        if (level.getBlockState(pos) != state) AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        return newState;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(SIZE).getShape(state.getValue(AXIS));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context.equals(CollisionContext.empty())) return Shapes.empty();
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(SIZE));

    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        checkBlocks(level, state, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        checkBlocks(level, state, pos);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1f;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var axis = state.getValue(AXIS);
        for (var dir : Direction.values()) {
            if (dir.getAxis().equals(axis)) continue;
            var target = level.getBlockState(pos.relative(dir));
            var block = target.getBlock();
            if (block instanceof BoilerBlock) return false;
            if (block instanceof BoilerStructureBlock && target.getValue(BoilerStructureBlock.FACING) != dir.getOpposite()) return false;
        }
        return super.canSurvive(state, level, pos);
    }

    protected boolean hasRequiredLargeSpace(LevelReader level, BlockPos pos, BlockState state) {
        var axis = state.getValue(AXIS);
        for (var dir : Direction.values()) {
            if (dir.getAxis().equals(axis)) continue;
            var offState = level.getBlockState(pos.relative(dir));
            if (offState.getBlock() instanceof BoilerStructureBlock) continue;
            if (!offState.canBeReplaced()) return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void checkBlocks(LevelAccessor level, BlockState state, BlockPos pos) {
        if (!(state.getBlock() instanceof BoilerBlock)) return;
        var size = state.getValue(SIZE);
        var axis = state.getValue(AXIS);
        for (var dir : Direction.values()) {
            if (dir.getAxis().equals(axis)) continue;
            var off = pos.relative(dir);
            var offState = level.getBlockState(off);
            var newState = DnDecorBlocks.BOILER_STRUCTURE.getDefaultState();
            if (size == BoilerSize.SMALL) {
                if (offState.getBlock() instanceof BoilerStructureBlock) level.setBlock(off, Blocks.AIR.defaultBlockState(), 3);
                continue;
            }

            newState = newState.setValue(BoilerStructureBlock.FACING, dir.getOpposite()).setValue(BoilerStructureBlock.SIZE, size);

            if (offState.equals(newState)) continue;
            if (offState.getBlock() instanceof BoilerStructureBlock) {
                level.setBlock(off, newState, 3);
                continue;
            }

            var flag = offState.isAir() || (offState.canBeReplaced() && !(offState.is(DnDecorBlocks.BOILER_STRUCTURE) || !(offState.liquid() && size == BoilerSize.LARGE)));
            if (!flag) continue;
            level.setBlock(off, newState, 3);
        }
    }

    @Override
    protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    @Override
    public Class<BoilerBlockEntity> getBlockEntityClass() {
        return BoilerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BoilerBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.BOILER.get();
    }

    public enum BoilerSize implements StringRepresentable {
        SMALL,
        NORMAL,
        MEDIUM,
        LARGE;

        public BoilerSize shrink() {
            return switch (this) {
                case LARGE -> MEDIUM;
                case MEDIUM -> NORMAL;
                case NORMAL, SMALL -> SMALL;
            };
        }
        public BoilerSize grow() {
            return switch (this) {
                case SMALL -> NORMAL;
                case NORMAL -> MEDIUM;
                case MEDIUM, LARGE -> LARGE;
            };
        }

        private static final Map<Pair<BoilerSize, Direction.Axis>, VoxelShape> SHAPE_CACHE = new ConcurrentHashMap<>();

        public VoxelShape getShape(Direction.Axis axis) {
            return SHAPE_CACHE.computeIfAbsent(Pair.of(this, axis), p -> getBoilerShaper(p.getFirst()).get(p.getSecond()));
        }

        private static VoxelShaper getBoilerShaper(BoilerSize size) {
            return switch (size) {
                case SMALL -> DnDecorShapes.shape(-0.5, -0.5, 0, 16.5, 16.5, 16)
                        .add(-4, 3, 0, 20, 13, 16)
                        .add(3, -4, 0, 13, 20, 16)
                        .forDirectional(Direction.NORTH);
                case NORMAL -> DnDecorShapes.shape(-3.5, -3.5, 0, 19.5, 19.5, 16)
                        .add(-8, 1.5, 0, 24, 14.5, 16)
                        .add(1.5, -8, 0, 14.5, 24, 16)
                        .forDirectional(Direction.NORTH);
                case MEDIUM -> DnDecorShapes.shape(-6, -6, 0, 22, 22, 16)
                        .add(-12, -0.5, 0, 28, 16.5, 16)
                        .add(-0.5, -12, 0, 16.5, 28, 16)
                        .forDirectional(Direction.NORTH);
                case LARGE -> DnDecorShapes.shape(-9, -9, 0, 25, 25, 16)
                        .add(-16, -2, 0, 32, 18, 16)
                        .add(-2, -16, 0, 18, 32, 16)
                        .forDirectional(Direction.NORTH);
            };
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
