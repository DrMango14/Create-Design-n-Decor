package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CrushingWheelTypeBlock extends CrushingWheelBlock {
    NonNullSupplier<Block> block;
    public CrushingWheelTypeBlock(NonNullSupplier<Block> block, Properties properties) {
        super(properties);
        this.block = block;
    }

    @Override
    public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.STONE_TYPE_CRUSHING_WHEELS.get(block).get();
    }
}
