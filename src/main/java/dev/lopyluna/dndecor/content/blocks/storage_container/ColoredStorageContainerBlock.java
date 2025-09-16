package dev.lopyluna.dndecor.content.blocks.storage_container;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class ColoredStorageContainerBlock extends Block implements IWrenchable, IBE<ColoredStorageContainerBlockEntity> {

    public static final Property<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty LARGE = BooleanProperty.create("large");
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("colors", DyeColor.class);

    public ColoredStorageContainerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LARGE, false).setValue(COLOR, DyeColor.WHITE));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack pStack = player.getItemInHand(hand);
        if (pStack.getItem() instanceof DyeItem dyeItem) {
            if (applyDye(state, level, pos, dyeItem.getDyeColor(), player, pStack)) {
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }



    public boolean applyDye(BlockState pState, Level pLevel, BlockPos pPos, @Nullable DyeColor pDyeColor, Player pPlayer, ItemStack pDyeStack) {
        assert pDyeColor != null;
        if (!(pState.hasProperty(COLOR) && !pDyeStack.isEmpty() && pLevel.getBlockEntity(pPos) instanceof ColoredStorageContainerBlockEntity be)) return false;

        ColoredStorageContainerBlockEntity controllerBE = be.getControllerBE();
        if (controllerBE == null) return false;

        BlockState blockState = pLevel.getBlockState(controllerBE.getBlockPos());
        BlockState newState = blockState.setValue(COLOR, pDyeColor);

        if (blockState.getValue(COLOR) != newState.getValue(COLOR)) {
            List<BlockPos> positions = new ArrayList<>();
            if (be.isController()) {
                int radius = be.radius;
                int length = be.length;
                for (int y = 0; y < radius; y++) for (int z = 0; z < (controllerBE.axis == Direction.Axis.X ? radius : length); z++) for (int x = 0; x < (controllerBE.axis == Direction.Axis.Z ? radius : length); x++) {
                    BlockPos pos = controllerBE.getBlockPos().offset(x, y, z);
                    BlockState stateAtPos = pLevel.getBlockState(pos);
                    if (pLevel.getBlockEntity(pos) instanceof ColoredStorageContainerBlockEntity be2 && be.getController() != be2.getController()) continue;
                    if (stateAtPos.isAir()) continue;
                    if (!stateAtPos.hasProperty(COLOR)) continue;
                    positions.add(pos);
                }
            }

            pLevel.setBlockAndUpdate(controllerBE.getBlockPos(), controllerBE.getBlockState().setValue(COLOR, pDyeColor));
            for (BlockPos pos : positions) pLevel.setBlockAndUpdate(pos, pLevel.getBlockState(pos).setValue(COLOR, pDyeColor));
            if (!pPlayer.isCreative()) pDyeStack.shrink(1);
            return true;
        } else return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HORIZONTAL_AXIS, LARGE, COLOR);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getPlayer() == null || !pContext.getPlayer()
                .isShiftKeyDown()) {
            BlockState placedOn = pContext.getLevel()
                    .getBlockState(pContext.getClickedPos()
                            .relative(pContext.getClickedFace()
                                    .getOpposite()));
            Direction.Axis preferredAxis = getVaultBlockAxis(placedOn);
            if (preferredAxis != null)
                return this.defaultBlockState()
                        .setValue(HORIZONTAL_AXIS, preferredAxis);
        }
        return this.defaultBlockState()
                .setValue(HORIZONTAL_AXIS, pContext.getHorizontalDirection()
                        .getAxis());
    }

    @Override
    public void onPlace(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pOldState.getBlock() == pState.getBlock())
            return;
        if (pIsMoving)
            return;
        withBlockEntityDo(pLevel, pPos, ColoredStorageContainerBlockEntity::updateConnectivity);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getClickedFace()
                .getAxis()
                .isVertical()) {
            BlockEntity be = context.getLevel()
                    .getBlockEntity(context.getClickedPos());
            if (be instanceof ColoredStorageContainerBlockEntity vault) {
                ConnectivityHandler.splitMulti(vault);
                vault.removeController(true);
            }
            state = state.setValue(LARGE, false);
        }
        return IWrenchable.super.onWrenched(state, context);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean pIsMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof ColoredStorageContainerBlockEntity vaultBE))
                return;
            ItemHelper.dropContents(world, pos, vaultBE.inventory);
            world.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(vaultBE);
        }
    }

    public static boolean isVault(BlockState state) {
     /////   return DnDecorBlocks.DYED_STORAGE_CONTAINER.has(state);
        return false;
    }

    @Nullable
    public static Direction.Axis getVaultBlockAxis(BlockState state) {
        if (!isVault(state))
            return null;
        return state.getValue(HORIZONTAL_AXIS);
    }

    public static boolean isLarge(BlockState state) {
        if (!isVault(state))
            return false;
        return state.getValue(LARGE);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
        return state.setValue(HORIZONTAL_AXIS, rot.rotate(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE))
                .getAxis());
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirrorIn) {
        return state;
    }

    public static final SoundType SILENCED_METAL =
            new SoundType(0.1F, 1.5F, SoundEvents.NETHERITE_BLOCK_BREAK,  SoundEvents.NETHERITE_BLOCK_STEP,
                    SoundEvents.NETHERITE_BLOCK_PLACE, SoundEvents.NETHERITE_BLOCK_HIT,
                    SoundEvents.NETHERITE_BLOCK_FALL);

    @Override
    public @NotNull SoundType getSoundType(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos, Entity entity) {
        SoundType soundType = super.getSoundType(state, world, pos, entity);
        if (entity != null && entity.getPersistentData().contains("SilenceVaultSound")) return SILENCED_METAL;
        return soundType;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState p_149740_1_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return ItemHelper.calcRedstoneFromBlockEntity(this, pLevel, pPos);
    }

    @Override
    public BlockEntityType<? extends ColoredStorageContainerBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.COLORED_STORAGE_CONTAINER.get();
    }

    @Override
    public Class<ColoredStorageContainerBlockEntity> getBlockEntityClass() {
        return ColoredStorageContainerBlockEntity.class;
    }
}