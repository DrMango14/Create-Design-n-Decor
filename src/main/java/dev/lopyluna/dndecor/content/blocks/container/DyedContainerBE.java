package dev.lopyluna.dndecor.content.blocks.container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.api.packager.InventoryIdentifier;
import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import com.simibubi.create.foundation.ICapabilityProvider;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.VersionedInventoryWrapper;
import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;

import java.util.Objects;

@SuppressWarnings({"unchecked", "RedundantSuppression"})
public class DyedContainerBE extends ItemVaultBlockEntity {
    public boolean solidColor;
    public DyeColor color;

    public DyedContainerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        checkColor(state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DnDecorBETypes.DYED_CONTAINER.get(), (be, context) -> {
            be.initCapability();
            if (be.itemCapability == null) return null;
            return be.itemCapability.getCapability();
        });
    }

    public void checkColor(BlockState state) {
        color = DyedContainerBlock.getColor(state);
        solidColor = color != null && DyedContainerBlock.isSolidColor(state);
    }

    protected void updateComparators() {
        if (level == null || !(getControllerBE() instanceof DyedContainerBE be)) return;
        level.blockEntityChanged(be.worldPosition);
        int radius = be.getWidth();
        int length = be.getHeight();

        Direction.Axis axis = be.getMainConnectionAxis();

        int zMax = (axis == Direction.Axis.X ? radius : length);
        int xMax = (axis == Direction.Axis.Z ? radius : length);

        BlockPos.MutableBlockPos updatePos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos provokingPos = new BlockPos.MutableBlockPos();

        for (int y = 0; y < radius; y++) for (int z = 0; z < zMax; z++) for (int x = 0; x < xMax; x++) {
            if (!level.hasChunk(SectionPos.blockToSectionCoord(be.worldPosition.getX() + x), SectionPos.blockToSectionCoord(be.worldPosition.getZ() + z))) continue;
            provokingPos.setWithOffset(be.worldPosition, x, y, z);

            Block provokingBlock = level.getBlockState(provokingPos).getBlock();
            if (y == 0) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.DOWN);
            if (y == radius - 1) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.UP);
            if (z == 0) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.NORTH);
            if (z == zMax - 1) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.SOUTH);
            if (x == 0) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.WEST);
            if (x == xMax - 1) updateComparatorsInner(level, provokingBlock, provokingPos, updatePos, Direction.EAST);
        }
    }

    private static void updateComparatorsInner(Level level, Block provokingBlock, BlockPos provokingPos, BlockPos.MutableBlockPos updatePos, Direction direction) {
        updatePos.setWithOffset(provokingPos, direction);
        if (!level.hasChunk(SectionPos.blockToSectionCoord(updatePos.getX()), SectionPos.blockToSectionCoord(updatePos.getZ()))) return;

        var state = level.getBlockState(updatePos);
        state.onNeighborChange(level, updatePos, provokingPos);
        if (state.isRedstoneConductor(level, updatePos)) {
            updatePos.move(direction);
            state = level.getBlockState(updatePos);
            if (state.getWeakChanges(level, updatePos)) level.neighborChanged(state, updatePos, provokingBlock, provokingPos, false);
        }
    }

    @Override
    public DyedContainerBE getControllerBE() {
        if (level == null) return null;
        if (isController()) return this;
        if (level.getBlockEntity(controller) instanceof DyedContainerBE be) return be;
        return null;
    }

    private void initCapability() {
        if (level == null) return;
        if (itemCapability != null && itemCapability.getCapability() != null) return;
        var state = getBlockState();
        checkColor(state);
        if (!isController()) {
            var be = getControllerBE();
            if (be == null) return;
            be.initCapability();
            itemCapability = ICapabilityProvider.of(() -> {
                if (be.isRemoved()) return null;
                if (be.itemCapability == null) return null;
                return be.itemCapability.getCapability();
            });
            invId = be.invId;
            return;
        }

        boolean alongZ = DyedContainerBlock.getBlockAxis(state) == Direction.Axis.Z;
        IItemHandlerModifiable[] invs = new IItemHandlerModifiable[length * radius * radius];
        for (int yOffset = 0; yOffset < length; yOffset++) for (int xOffset = 0; xOffset < radius; xOffset++) for (int zOffset = 0; zOffset < radius; zOffset++) {
            BlockPos bePos = alongZ ? worldPosition.offset(xOffset, zOffset, yOffset) : worldPosition.offset(yOffset, xOffset, zOffset);
            DyedContainerBE beAt = ConnectivityHandler.partAt(DnDecorBETypes.DYED_CONTAINER.get(), level, bePos);
            invs[yOffset * radius * radius + xOffset * radius + zOffset] = beAt != null ? beAt.getInventoryOfBlock() : new ItemStackHandler();
        }
        itemCapability = ICapabilityProvider.of(new VersionedInventoryWrapper(new CombinedInvWrapper(invs)));
        invId = new InventoryIdentifier.Bounds(BoundingBox.fromCorners(this.worldPosition, alongZ ? worldPosition.offset(radius, radius, length) : worldPosition.offset(length, radius, radius)));
    }

    @Override
    protected void updateConnectivity() {
        updateConnectivity = false;
        if (level == null || level.isClientSide()) return;
        if (!isController()) return;
        DyedContainerConnectivity.formMulti(this);
    }

    @Override
    public void notifyMultiUpdated() {
        if (level == null) return;
        var state = getBlockState();
        checkColor(state);
        if (DyedContainerBlock.isContainer(state)) level.setBlock(getBlockPos(), state.setValue(DyedContainerBlock.LARGE, radius > 2), Block.UPDATE_CLIENTS | Block.UPDATE_INVISIBLE);
        itemCapability = null;
        invalidateCapabilities();
        setChanged();
    }

    @Override
    public void removeController(boolean keepContents) {
        if (level == null || level.isClientSide()) return;
        updateConnectivity = true;
        controller = null;
        radius = 1;
        length = 1;

        var state = getBlockState();
        checkColor(state);
        if (DyedContainerBlock.isContainer(state)) {
            state = state.setValue(DyedContainerBlock.LARGE, false);
            level.setBlock(worldPosition, state, Block.UPDATE_CLIENTS | Block.UPDATE_INVISIBLE | Block.UPDATE_KNOWN_SHAPE);
        }
        itemCapability = null;
        invalidateCapabilities();
        setChanged();
        sendData();
    }

    public boolean setColor(DyeColor color, boolean remove) {
        if (color == null && !remove) remove = true;
        if (remove && this.color == null) return false;
        var add = !remove && !solidColor && this.color == color;
        if (!(add || remove || this.color != color)) return false;
        if (level == null) return false;
        var be = getControllerBE();
        if (be == null) return false;
        if (!isController()) return be.setColor(color, remove);
        var axis = getMainConnectionAxis();
        int zMax = (axis == Direction.Axis.X ? radius : length);
        int xMax = (axis == Direction.Axis.Z ? radius : length);
        int i = add ? this.color == null ? 1 : 2 : remove ? solidColor ? 1 : 0 : solidColor ? 2 : 1;

        if (remove && solidColor) color = be.color;
        else be.color = color;

        for (int y = 0; y < radius; y++) for (int z = 0; z < zMax; z++) for (int x = 0; x < xMax; x++) {
            var pos = worldPosition.offset(x, y, z);
            var state = level.getBlockState(pos);
            if (!DyedContainerBlock.isContainer(state)) continue;
            var newState = (color == null ? DnDecorBlocks.CONTAINER : i == 2 ? DnDecorBlocks.DYED_SOLID_CONTAINERS.get(color) : i == 0 ? DnDecorBlocks.CONTAINER : DnDecorBlocks.DYED_CONTAINERS.get(color)).getDefaultState();
            level.setBlock(pos, BlockHelper.copyProperties(state, newState), Block.UPDATE_CLIENTS | Block.UPDATE_INVISIBLE | Block.UPDATE_KNOWN_SHAPE);
            level.getChunkSource().getLightEngine().checkBlock(pos);
            if (level.getBlockEntity(pos) instanceof DyedContainerBE dyed) {
                dyed.solidColor = i == 2;
                dyed.color = i == 0 ? null : color;
            }

        }
        be.updateConnectivity();
        return true;
    }

    @Override
    public int hashCode() {
        return color == null ? super.hashCode() : Objects.hash(super.hashCode(), color, solidColor);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof DyedContainerBE be && ((be.color == null && color == null) || (be.color == color && be.solidColor == solidColor));
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        checkColor(getBlockState());
    }
}
