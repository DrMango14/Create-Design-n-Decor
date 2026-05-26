package dev.lopyluna.dndecor.content.blocks.breaker_switch;

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

public class BreakerSwitchBE extends SmartBlockEntity implements IHaveGoggleInformation {
    int state = 0;
    int lastChange;
    LerpedFloat clientState = LerpedFloat.linear();

    public BreakerSwitchBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        this.state = compound.getInt("State");
        this.lastChange = compound.getInt("ChangeTimer");
        this.clientState.chase(this.state, 0.5F, LerpedFloat.Chaser.EXP);
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
        BreakerSwitchBlock.updateNeighbors(getBlockState(), level, worldPosition);
    }

    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    public void changeState(boolean sneak) {
        var stage = Mth.clamp(sneak ? state-1 : state+1, 0, 2);
        int prevState = state;
        state = stage;
        if (prevState != state) lastChange = 15;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.analogStrength", Math.round(state/2f * 15f)).forGoggles(tooltip);
        return true;
    }

    public int getState() {
        return this.state;
    }
}
