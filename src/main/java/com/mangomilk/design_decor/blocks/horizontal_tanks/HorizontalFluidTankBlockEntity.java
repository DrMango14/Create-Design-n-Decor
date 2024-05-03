package com.mangomilk.design_decor.blocks.horizontal_tanks;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static com.simibubi.create.content.fluids.tank.FluidTankBlockEntity.getCapacityMultiplier;

public class HorizontalFluidTankBlockEntity extends SmartBlockEntity implements IMultiBlockEntityContainer.Fluid, IHaveGoggleInformation {

	protected LazyOptional<CombinedTankWrapper> fluidCapability;

	SmartFluidTankBehaviour fluidInventory;


	protected BlockPos controller;
	protected BlockPos lastKnownPos;
	protected boolean updateConnectivity;
	protected int radius;
	protected int length;

	protected Axis axis;






	public HorizontalFluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);


		radius = 1;
		length = 1;
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		fluidInventory = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, getCapacityMultiplier(), true)
				.forbidExtraction();

		behaviours.add(fluidInventory);


		fluidCapability = LazyOptional.of(() -> {
			Storage<FluidVariant> inputCap = fluidInventory.getCapability();
			return new CombinedTankWrapper(inputCap);
		});
	}

	protected void updateConnectivity() {
		updateConnectivity = false;
		if (level.isClientSide())
			return;
		if (!isController())
			return;
		ConnectivityHandler.formMulti(this);
	}


	@Override
	public void tick() {
		super.tick();

		if(fluidInventory.getPrimaryHandler().getFluidAmount()> fluidInventory.getPrimaryHandler().getCapacity())
			fluidInventory.getPrimaryHandler().setFluid(new FluidStack(fluidInventory.getPrimaryHandler().getFluid(), fluidInventory.getPrimaryHandler().getCapacity()));




		if(isController()) {
			setTankSize(1, (int) (Math.pow(getHeight(),2)*getWidth()));
		}

		if (lastKnownPos == null)
			lastKnownPos = getBlockPos();
		else if (!lastKnownPos.equals(worldPosition) && worldPosition != null) {
			onPositionChanged();
			return;
		}

		if (updateConnectivity)
			updateConnectivity();
	}



	public int getCapacity(){
		return (int)(8000*(Math.pow(getHeight(),2)*getWidth()));
	}

	@Override
	public BlockPos getLastKnownPos() {
		return lastKnownPos;
	}

	@Override
	public boolean isController() {
		return controller == null || worldPosition.getX() == controller.getX()
			&& worldPosition.getY() == controller.getY() && worldPosition.getZ() == controller.getZ();
	}

	private void onPositionChanged() {
		removeController(true);
		lastKnownPos = worldPosition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HorizontalFluidTankBlockEntity getControllerBE() {
		if (isController())
			return this;
		BlockEntity blockEntity = level.getBlockEntity(controller);
		if (blockEntity instanceof HorizontalFluidTankBlockEntity)
			return (HorizontalFluidTankBlockEntity) blockEntity;
		return null;
	}

	public void removeController(boolean keepContents) {
		if (level.isClientSide())
			return;
		updateConnectivity = true;
		controller = null;
		radius = 1;
		length = 1;

		BlockState state = getBlockState();
		if (HorizontalFluidTankBlock.isBarrel(state)) {
			state = state.setValue(HorizontalFluidTankBlock.LARGE, false);
			getLevel().setBlock(worldPosition, state, 22);
		}

		fluidCapability.invalidate();
		setChanged();
		sendData();
	}

	@Override
	public void setController(BlockPos controller) {
		if (level.isClientSide && !isVirtual())
			return;
		if (controller.equals(this.controller))
			return;
		this.controller = controller;
		fluidCapability.invalidate();
		setChanged();
		sendData();
	}

	@Override
	public BlockPos getController() {
		return isController() ? worldPosition : controller;
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);

		BlockPos controllerBefore = controller;
		int prevSize = radius;
		int prevLength = length;

		updateConnectivity = compound.contains("Uninitialized");
		controller = null;
		lastKnownPos = null;

		if (compound.contains("LastKnownPos"))
			lastKnownPos = NbtUtils.readBlockPos(compound.getCompound("LastKnownPos"));
		if (compound.contains("Controller"))
			controller = NbtUtils.readBlockPos(compound.getCompound("Controller"));

		if (isController()) {
			radius = compound.getInt("Size");
			length = compound.getInt("Length");
		}
		fluidInventory.getPrimaryHandler().readFromNBT(compound.getCompound("inputContent"));


		//if (!clientPacket) {
		//	inputTank.deserializeNBT(compound.getCompound("Inventory"));
		//	return;
		//}

		boolean changeOfController =
			controllerBefore == null ? controller != null : !controllerBefore.equals(controller);
		if (hasLevel() && (changeOfController || prevSize != radius || prevLength != length))
			level.setBlocksDirty(getBlockPos(), Blocks.AIR.defaultBlockState(), getBlockState());
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		if (updateConnectivity)
			compound.putBoolean("Uninitialized", true);
		if (lastKnownPos != null)
			compound.put("LastKnownPos", NbtUtils.writeBlockPos(lastKnownPos));
		if (!isController())
			compound.put("Controller", NbtUtils.writeBlockPos(controller));
		if (isController()) {
			compound.putInt("Size", radius);
			compound.putInt("Length", length);
		}
		compound.put("inputContent", fluidInventory.getPrimaryHandler().writeToNBT(new CompoundTag()));



		super.write(compound, clientPacket);


	}




//	@Nonnull
//	@Override
//	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//
//		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
//			initCapability();
//			return fluidCapability.cast();
//		}
//		return super.getCapability(cap, side);
//	}

	private void initCapability() {
		if (!isController()) {
			HorizontalFluidTankBlockEntity controllerBE = getControllerBE();
			if (controllerBE == null)
				return;


			controllerBE.initCapability();
			fluidCapability = controllerBE.fluidCapability;
			return;
		}
		fluidCapability = LazyOptional.of(() -> {
			Storage<FluidVariant> inputCap = fluidInventory.getCapability();

			return new CombinedTankWrapper(inputCap);
		});


	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		HorizontalFluidTankBlockEntity controllerBE = getControllerBE();



		LangBuilder mb = Lang.translate("generic.unit.millibuckets");

		boolean isEmpty = true;
		for (int i = 0; i < fluidInventory.getTanks().length; i++) {
			FluidTank tank = getTank(i);
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack.isEmpty())
				continue;

			Lang.fluidName(fluidStack)
					.style(ChatFormatting.GRAY)
					.forGoggles(tooltip, 1);

			Lang.builder()
					.add(Lang.number(fluidStack.getAmount())
							.add(mb)
							.style(ChatFormatting.DARK_GREEN))
					.text(ChatFormatting.GRAY, " / ")
					.add(Lang.number(tank.getCapacity())
							.add(mb)
							.style(ChatFormatting.DARK_GRAY))
					.forGoggles(tooltip, 1);

			isEmpty = false;
		}

		if (fluidInventory.getTanks().length > 1) {
			if (isEmpty)
				tooltip.remove(tooltip.size() - 1);
			return true;
		}

		if (!isEmpty)
			return true;

		Lang.translate("gui.goggles.fluid_container.capacity")
				.add(Lang.number(getTankSize(0))
						.add(mb)
						.style(ChatFormatting.DARK_GREEN))
				.style(ChatFormatting.DARK_GRAY)
				.forGoggles(tooltip, 1);


		return true;
	}

	public static int getMaxLength(int radius) {
		return radius * 3;
	}

	@Override
	public void preventConnectivityUpdate() { updateConnectivity = false; }

	@Override
	public void notifyMultiUpdated() {
		BlockState state = this.getBlockState();
		if (HorizontalFluidTankBlock.isBarrel(state)) { // safety
			level.setBlock(getBlockPos(), state.setValue(HorizontalFluidTankBlock.LARGE, radius > 2), 6);
		}
		fluidCapability.invalidate();
		setChanged();
	}

	@Override
	public Axis getMainConnectionAxis() { return getMainAxisOf(this); }

	@Override
	public int getMaxLength(Axis longAxis, int width) {
		if (longAxis == Axis.Y) return getMaxWidth();
		return getMaxLength(width);
	}

	@Override
	public void setTankSize(int tank, int blocks) {
		applyFluidTankSize(blocks);
	}
	public void applyFluidTankSize(int blocks) {
		fluidInventory.getPrimaryHandler().setCapacity(blocks * getCapacityMultiplier());
	}
	@Override
	public int getMaxWidth() {
		return 3;
	}

	@Override
	public int getHeight() { return length; }

	@Override
	public int getWidth() { return radius; }


	public int getLength() { return length; }

	@Override
	public void setHeight(int height) { this.length = height; }

	@Override
	public void setWidth(int width) { this.radius = width; }


}
