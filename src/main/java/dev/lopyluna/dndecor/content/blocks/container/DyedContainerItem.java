package dev.lopyluna.dndecor.content.blocks.container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.symmetryWand.SymmetryWandItem;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("NullableProblems")
public class DyedContainerItem extends BlockItem {
    public DyedContainerItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        var result = super.place(ctx);
        if (!result.consumesAction()) return result;
        tryMultiPlace(ctx);
        return result;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos blockPos, Level level, Player player, ItemStack itemStack, BlockState blockState) {
        var server = level.getServer();
        if (server == null) return false;
        var data = itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data != null) {
            var nbt = data.copyTag();
            nbt.remove("Length");
            nbt.remove("Size");
            nbt.remove("Controller");
            nbt.remove("LastKnownPos");
            BlockEntity.addEntityType(nbt, ((IBE<?>) getBlock()).getBlockEntityType());
            itemStack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(nbt));
        }
        return super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState);
    }

    private void tryMultiPlace(BlockPlaceContext ctx) {
        var player = ctx.getPlayer();
        if (player == null) return;
        if (player.isShiftKeyDown()) return;
        var face = ctx.getClickedFace();
        var stack = ctx.getItemInHand();
        var level = ctx.getLevel();
        var pos = ctx.getClickedPos();
        var placedPos = pos.relative(face.getOpposite());
        var placedState = level.getBlockState(placedPos);

        if (!DyedContainerBlock.isContainer(placedState)) return;
        if (SymmetryWandItem.presentInHotbar(player)) return;
        if (!(ConnectivityHandler.partAt(DnDecorBETypes.DYED_CONTAINER.get(), level, placedPos) instanceof DyedContainerBE b && b.getControllerBE() instanceof DyedContainerBE be)) return;

        int width = be.getWidth();
        if (width == 1) return;

        var axis = DyedContainerBlock.getBlockAxis(placedState);
        if (axis == null) return;
        if (face.getAxis() != axis) return;
        int tanksToPlace = 0;

        var dir = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        var start = face == dir.getOpposite() ? be.getBlockPos().relative(dir.getOpposite()) : be.getBlockPos().relative(dir, be.getHeight());

        if (VecHelper.getCoordinate(start, axis) != VecHelper.getCoordinate(pos, axis)) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            var off = axis == Direction.Axis.X ? start.offset(0, xOffset, zOffset) : start.offset(xOffset, zOffset, 0);
            var state = level.getBlockState(off);
            if (DyedContainerBlock.isContainer(state)) continue;
            if (!state.canBeReplaced()) return;
            tanksToPlace++;
        }

        if (!player.isCreative() && stack.getCount() < tanksToPlace) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            var off = axis == Direction.Axis.X ? start.offset(0, xOffset, zOffset) : start.offset(xOffset, zOffset, 0);
            if (DyedContainerBlock.isContainer(level.getBlockState(off))) continue;
            var context = BlockPlaceContext.at(ctx, off, face);
            player.getPersistentData().putBoolean("SilenceVaultSound", true);
            super.place(context);
            player.getPersistentData().remove("SilenceVaultSound");
        }
    }
}
