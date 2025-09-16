package dev.lopyluna.dndecor.content.blocks.storage_container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerBlock.COLOR;

public class ColoredStorageContainerItem extends BlockItem {

    private final DyeColor color;

    public ColoredStorageContainerItem(Properties properties, DyeColor color) {
        /////super(DnDecorBlocks.DYED_STORAGE_CONTAINER.get(), properties);
        super(null,properties);
        this.color = color;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull BlockPlaceContext pContext) {
        if (super.getPlacementState(pContext) == null)
            return super.getPlacementState(pContext);
        return Objects.requireNonNull(super.getPlacementState(pContext)).setValue(COLOR, color);
    }

   // @Override
   // public @NotNull String getDescriptionId() {
   //     return "item.design_decor." + RegisteredObjectsHelper.getKeyOrThrow(this).getPath();
   // }

    @Override
    public @NotNull InteractionResult place(@NotNull BlockPlaceContext ctx) {
        InteractionResult initialResult = super.place(ctx);
        if (!initialResult.consumesAction())
            return initialResult;
        tryMultiPlace(ctx);
        return initialResult;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos p_195943_1_, Level p_195943_2_, Player p_195943_3_,
                                                 ItemStack p_195943_4_, BlockState p_195943_5_) {
        MinecraftServer minecraftserver = p_195943_2_.getServer();
        if (minecraftserver == null)
            return false;
        CompoundTag nbt = p_195943_4_.getTagElement("BlockEntityTag");
        if (nbt != null) {
            nbt.remove("Length");
            nbt.remove("Size");
            nbt.remove("Controller");
            nbt.remove("LastKnownPos");
        }
        return super.updateCustomBlockEntityTag(p_195943_1_, p_195943_2_, p_195943_3_, p_195943_4_, p_195943_5_);
    }

    private void tryMultiPlace(BlockPlaceContext ctx) {
        Player player = ctx.getPlayer();
        if (player == null) return;
        if (player.isShiftKeyDown()) return;
        Direction face = ctx.getClickedFace();
        ItemStack stack = ctx.getItemInHand();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockPos placedOnPos = pos.relative(face.getOpposite());
        BlockState placedOnState = world.getBlockState(placedOnPos);

        ColoredStorageContainerBlockEntity tankAt = ConnectivityHandler.partAt(DnDecorBETypes.COLORED_STORAGE_CONTAINER.get(), world, placedOnPos);
        if (tankAt == null) return;
        ColoredStorageContainerBlockEntity controllerBE = tankAt.getControllerBE();
        if (controllerBE == null) return;
        if (!ColoredStorageContainerBlock.isVault(placedOnState)) return;

        int width = controllerBE.radius;
        if (width == 1) return;

        int tanksToPlace = 0;
        Direction.Axis vaultBlockAxis = ColoredStorageContainerBlock.getVaultBlockAxis(placedOnState);
        if (vaultBlockAxis == null) return;
        if (face.getAxis() != vaultBlockAxis) return;

        Direction vaultFacing = Direction.fromAxisAndDirection(vaultBlockAxis, Direction.AxisDirection.POSITIVE);
        BlockPos startPos = face == vaultFacing.getOpposite() ? controllerBE.getBlockPos().relative(vaultFacing.getOpposite()) : controllerBE.getBlockPos().relative(vaultFacing, controllerBE.length);

        if (VecHelper.getCoordinate(startPos, vaultBlockAxis) != VecHelper.getCoordinate(pos, vaultBlockAxis)) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos offsetPos = vaultBlockAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
            BlockState blockState = world.getBlockState(offsetPos);
            if (ColoredStorageContainerBlock.isVault(blockState)) continue;
            if (!blockState.canBeReplaced()) return;
            tanksToPlace++;
        }

        if (!player.isCreative() && stack.getCount() < tanksToPlace) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos offsetPos = vaultBlockAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
            BlockState blockState = world.getBlockState(offsetPos);
            if (ColoredStorageContainerBlock.isVault(blockState)) continue;
            BlockPlaceContext context = BlockPlaceContext.at(ctx, offsetPos, face);
            player.getPersistentData().putBoolean("SilenceVaultSound", true);
            super.place(context);
            player.getPersistentData().remove("SilenceVaultSound");
        }
    }
}