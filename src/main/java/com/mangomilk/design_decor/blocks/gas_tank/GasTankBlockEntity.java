package com.mangomilk.design_decor.blocks.gas_tank;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class GasTankBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation, SidedStorageBlockEntity {


    protected FluidTank tankInventory;

    protected LazyOptional<FluidTank> fluidCapability;

    public GasTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        tankInventory = new SmartFluidTank(5000,this::onFluidStackChanged);

        fluidCapability = LazyOptional.of(() -> tankInventory);
    }

    @Override
    public void invalidate() {
        super.invalidate();

        fluidCapability.invalidate();
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);

        tankInventory.readFromNBT(compound.getCompound("TankContent"));



    }
    protected void onFluidStackChanged(FluidStack newFluidStack) {
        if (!hasLevel())
            return;



        if (!level.isClientSide) {
            setChanged();
            sendData();
        }


    }
    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);


        compound.put("TankContent", tankInventory.writeToNBT(new CompoundTag()));



    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

	@Nullable
	@Override
	public Storage<FluidVariant> getFluidStorage(@Nullable Direction direction) {
		return tankInventory;
	}
    @Override
    @SuppressWarnings("removal")
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        LangBuilder mb = Lang.translate("generic.unit.millibuckets");


        boolean isEmpty = true;


		FluidStack fluidStack = tankInventory.getFluid();

		Lang.fluidName(fluidStack)
				.style(ChatFormatting.GRAY)
				.forGoggles(tooltip, 1);

		Lang.builder()
				.add(Lang.number(fluidStack.getAmount())
						.add(mb)
						.style(ChatFormatting.DARK_PURPLE))
				.text(ChatFormatting.GRAY, " / ")
				.add(Lang.number(tankInventory.getCapacity())
						.add(mb)
						.style(ChatFormatting.DARK_GRAY))
				.forGoggles(tooltip, 1);

		isEmpty = false;


        if (!isEmpty)
            return true;

        Lang.translate("gui.goggles.fluid_container.capacity")
                .add(Lang.number(tankInventory.getCapacity())
                        .add(mb)
                        .style(ChatFormatting.DARK_PURPLE))
                .style(ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip, 1);


        return true;
    }
}
