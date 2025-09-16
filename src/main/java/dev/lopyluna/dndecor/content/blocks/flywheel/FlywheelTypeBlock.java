package dev.lopyluna.dndecor.content.blocks.flywheel;

import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FlywheelTypeBlock extends FlywheelBlock {
    public DyeColor color;
    public FlywheelTypeBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public BlockEntityType<? extends FlywheelBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.COLORED_FLYWHEELS.get();
    }
}
