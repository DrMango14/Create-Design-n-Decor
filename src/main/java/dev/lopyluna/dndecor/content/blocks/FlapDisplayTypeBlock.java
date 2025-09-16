package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FlapDisplayTypeBlock extends FlapDisplayBlock {
    public FlapDisplayTypeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends FlapDisplayBlockEntity> getBlockEntityType() {
        return DnDecorBETypes.FLAP_DISPLAYS.get();
    }
}
