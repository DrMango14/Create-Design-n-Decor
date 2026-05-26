package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BulkCTBehaviour extends ConnectedTextureBehaviour.Base {
    private final List<CTSpriteShiftEntry> shifts;

    public BulkCTBehaviour(CTSpriteShiftEntry... shifts) {
        this.shifts = new ArrayList<>(List.of(shifts));
    }

    @Override
    public @Nullable CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        for (var shift : shifts) {
            if (sprite == null) return shift;
            var s = shift.getOriginal();
            if (s.getU0() == sprite.getU0() && s.getU1() == sprite.getU1() && s.getV0() == sprite.getV0() && s.getV1() == sprite.getV1()) return shift;
        }
        return null;
    }
}
