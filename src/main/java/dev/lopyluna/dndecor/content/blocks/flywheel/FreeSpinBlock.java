package dev.lopyluna.dndecor.content.blocks.flywheel;

import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

public class FreeSpinBlock extends FlywheelBlock {
    @Nullable
    public final DyeColor color;
    public final Type type;
    public FreeSpinBlock(@Nullable DyeColor color, Type type, Properties properties) {
        super(properties);
        this.color = color;
        this.type = type;
    }
    public FreeSpinBlock(Type type, Properties properties) {
        super(properties);
        this.color = null;
        this.type = type;
    }

    @Override
    public BlockEntityType<? extends FlywheelBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.COLORED_FLYWHEELS.get();
    }

    public enum Type {
        FLYWHEEL,
        LARGE_FAN
    }
}
