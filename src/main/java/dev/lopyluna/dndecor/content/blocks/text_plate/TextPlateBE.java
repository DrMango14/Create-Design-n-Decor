package dev.lopyluna.dndecor.content.blocks.text_plate;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;

import javax.annotation.Nullable;
import java.util.List;

public class TextPlateBE extends SmartBlockEntity {
    protected DyeColor dyedText = DyeColor.WHITE;
    protected String text = "a";
    protected boolean glowing = false;

    protected MaterialTypeProvider.MetalType metalBase = null;
    protected DyeColor colorBase = DyeColor.WHITE;

    protected boolean extended = false;
    protected Direction facing = Direction.NORTH;
    protected AttachFace face = AttachFace.FLOOR;

    public TextPlateBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        update(state);
        if (extended) text = "ab";
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    protected void write(CompoundTag nbt, HolderLookup.Provider provider, boolean clientPacket) {
        super.write(nbt, provider, clientPacket);
        nbt.putString("DyedText", dyedText.getName());
        nbt.putString("Text", text.isEmpty() ? text : text.substring(0, Math.min(text.length(), extended?2:1)));
        nbt.putBoolean("Glowing", glowing);

        if (metalBase != null) nbt.putString("Base", metalBase.id);
        else if (colorBase != null) nbt.putString("Base", colorBase.getName());
    }

    @Override
    protected void read(CompoundTag nbt, HolderLookup.Provider provider, boolean clientPacket) {
        super.read(nbt, provider, clientPacket);
        update(null);

        dyedText = DyeColor.byName(nbt.getString("DyedText"), DyeColor.WHITE);
        text = nbt.getString("Text");
        if (!text.isEmpty()) text = text.substring(0, Math.min(text.length(), extended?2:1));
        glowing = nbt.getBoolean("Glowing");


        metalBase = null;
        colorBase = null;
        if (!nbt.contains("Base")) return;

        var type = nbt.getString("Base");
        MaterialTypeProvider.getMetalTypeByID(type).ifPresent(t -> metalBase = t.get());
        if (metalBase != null) return;
        colorBase = DyeColor.byName(type, DyeColor.WHITE);
    }

    public boolean isEmpty() {
        return metalBase == null && colorBase == null;
    }

    public void update(@Nullable BlockState blockState) {
        var state = blockState == null ? getBlockState() : blockState;
        extended = state.getValue(TextPlateBlock.EXTENDED);
        facing = state.getValue(TextPlateBlock.FACING);
        face = state.getValue(TextPlateBlock.FACE);
    }
}
