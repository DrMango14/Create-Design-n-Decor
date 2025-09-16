package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MillstoneTypeBlock extends MillstoneBlock {
    NonNullSupplier<Block> block;
    String id;
    public MillstoneTypeBlock(String id, NonNullSupplier<Block> block, Properties properties) {
        super(properties);
        this.block = block;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public BlockEntityType<? extends MillstoneBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.STONE_TYPE_MILLSTONES.get(block).get();
    }
}
