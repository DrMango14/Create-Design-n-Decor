package dev.lopyluna.dndecor.content.blocks.container;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.item.ItemHelper;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.simibubi.create.content.logistics.vault.ItemVaultBlock.SILENCED_METAL;

@SuppressWarnings({"NullableProblems", "unused"})
public class DyedContainerBlock extends Block implements IWrenchable, IBE<DyedContainerBE> {
    public static final Property<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty LARGE = BooleanProperty.create("large");

    public final boolean solidColor;
    public final DyeColor color;
    public DyedContainerBlock(Properties properties, boolean solidColor, DyeColor color) {
        super(properties);
        this.solidColor = solidColor;
        this.color = color;
        registerDefaultState(defaultBlockState().setValue(LARGE, false));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HORIZONTAL_AXIS, LARGE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var player = ctx.getPlayer();
        if (player == null || !player.isShiftKeyDown()) {
            var axis = getBlockAxis(ctx.getLevel().getBlockState(ctx.getClickedPos().relative(ctx.getClickedFace().getOpposite())));
            if (axis != null) return defaultBlockState().setValue(HORIZONTAL_AXIS, axis);
        }
        return defaultBlockState().setValue(HORIZONTAL_AXIS, ctx.getHorizontalDirection().getAxis());
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pOldState.getBlock() == pState.getBlock()) return;
        if (pIsMoving) return;
        if (isContainerReplacement(pOldState, pState)) return;
        withBlockEntityDo(pLevel, pPos, DyedContainerBE::updateConnectivity);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        var i = stack.getItem();
        if (i instanceof DyeItem dyeItem) {
            var flag = new AtomicBoolean(false);
            var color = dyeItem.getDyeColor();
            withBlockEntityDo(level, pos, be -> flag.set(be.setColor(color, false)));
            if (flag.get()) {
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
                return ItemInteractionResult.SUCCESS;
            }
        } else if (stack.is(AllItems.SAND_PAPER)) {
            var flag = new AtomicBoolean(false);
            withBlockEntityDo(level, pos, be -> flag.set(be.setColor(null, true)));
            if (flag.get()) {
                level.playSound(null, pos, AllSoundEvents.SANDING_SHORT.getMainEvent(), SoundSource.BLOCKS, 0.8f, 1.1f - level.random.nextFloat() * .2f);
                level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS, 0.1f, 0.7f - level.random.nextFloat() * .2f);
                return ItemInteractionResult.SUCCESS;
            }
        } else if (stack.is(Items.WATER_BUCKET) || stack.is(Items.WET_SPONGE)) {
            var flag = new AtomicBoolean(false);
            withBlockEntityDo(level, pos, be -> flag.set(be.setColor(null, true)));
            if (flag.get()) {
                level.playSound(null, pos, SoundEvents.AXOLOTL_SPLASH, SoundSource.BLOCKS, 0.8f, 1.5f - level.random.nextFloat() * .25f);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getClickedFace().getAxis().isVertical()) {
            if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof DyedContainerBE be) {
                ConnectivityHandler.splitMulti(be);
                be.removeController(true);
            }
            state = state.setValue(LARGE, false);
        }
        return IWrenchable.super.onWrenched(state, context);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean pIsMoving) {
        if (isContainerReplacement(state, newState)) return;
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            if (!(level.getBlockEntity(pos) instanceof DyedContainerBE be)) return;
            ItemHelper.dropContents(level, pos, be.getInventoryOfBlock());
            level.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(be);
        }
    }

    private static boolean isContainerReplacement(BlockState oldState, BlockState newState) {
        return oldState.hasBlockEntity() && newState.hasBlockEntity()
                && oldState.getBlock() instanceof DyedContainerBlock
                && newState.getBlock() instanceof DyedContainerBlock;
    }

    public static DyeColor getColor(BlockState state) {
        return state.getBlock() instanceof DyedContainerBlock block ? block.color : null;
    }
    public static boolean isSolidColor(BlockState state) {
        return state.getBlock() instanceof DyedContainerBlock block && block.solidColor;
    }

    public static boolean isContainer(BlockState state) {
        return state.getBlock() instanceof DyedContainerBlock block &&
                (block.color == null ? DnDecorBlocks.CONTAINER : block.solidColor ? DnDecorBlocks.DYED_SOLID_CONTAINERS.get(block.color) : DnDecorBlocks.DYED_CONTAINERS.get(block.color)).has(state);
    }

    @Nullable
    public static Direction.Axis getBlockAxis(BlockState state) {
        if (!isContainer(state)) return null;
        return state.getValue(HORIZONTAL_AXIS);
    }

    public static Direction.Axis getBlockAxisNon(BlockState state) {
        return state.getValue(HORIZONTAL_AXIS);
    }

    public static boolean isLarge(BlockState state) {
        if (!isContainer(state)) return false;
        return state.getValue(LARGE);
    }

    public static boolean isLargeNon(BlockState state) {
        return state.getValue(LARGE);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(HORIZONTAL_AXIS, rot.rotate(Direction.fromAxisAndDirection(state.getValue(HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE)).getAxis());
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state;
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
        SoundType soundType = super.getSoundType(state, world, pos, entity);
        if (entity != null && entity.getPersistentData().contains("SilenceVaultSound")) return SILENCED_METAL;
        return soundType;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return ItemHelper.calcRedstoneFromBlockEntity(this, pLevel, pPos);
    }

    @Override
    public BlockEntityType<DyedContainerBE> getBlockEntityType() {
        return DnDecorBETypes.DYED_CONTAINER.get();
    }

    @Override
    public Class<DyedContainerBE> getBlockEntityClass() {
        return DyedContainerBE.class;
    }
}
