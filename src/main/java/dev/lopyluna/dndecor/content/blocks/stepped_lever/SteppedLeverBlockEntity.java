package dev.lopyluna.dndecor.content.blocks.stepped_lever;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SteppedLeverBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    int state = 0;
    int lastChange;
    LerpedFloat clientState = LerpedFloat.linear();

    public SteppedLeverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        this.state = compound.getInt("State");
        this.lastChange = compound.getInt("ChangeTimer");
        this.clientState.chase(this.state, 0.2F, LerpedFloat.Chaser.EXP);
        super.read(compound, registries, clientPacket);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("State", this.state);
        compound.putInt("ChangeTimer", this.lastChange);
        super.write(compound, registries, clientPacket);
    }

    public void tick() {
        super.tick();
        if (level == null) return;
        if (this.lastChange > 0) {
            --this.lastChange;
            if (this.lastChange == 0) this.updateOutput();
        }
        if (this.level.isClientSide) this.clientState.tickChaser();
    }

    public void initialize() {
        super.initialize();
    }

    private void updateOutput() {
        assert level != null;
        SteppedLeverBlock.updateNeighbors(getBlockState(), level, worldPosition);
    }

    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    public void changeState(boolean back) {
        int prevState = state;
        state += back ? -1 : 1;
        state = Mth.clamp(state, 0, 15);
        if (prevState != state) lastChange = 15;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.analogStrength", this.state).forGoggles(tooltip);
        return true;
    }

    public int getState() {
        return this.state;
    }
}
