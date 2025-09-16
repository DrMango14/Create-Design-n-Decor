package dev.lopyluna.dndecor.content.blocks;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndecor.DnDecor;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MillstoneTypeRenderer extends KineticBlockEntityRenderer<MillstoneBlockEntity> {
    public MillstoneTypeRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(MillstoneBlockEntity be, BlockState state) {
        if (state.getBlock() instanceof MillstoneTypeBlock block && block.id != null && !block.id.isEmpty())
            return CachedBuffers.partial(PartialModel.of(DnDecor.asResource("block/" + block.id + "_millstone/inner")), state);
        return CachedBuffers.partial(AllPartialModels.MILLSTONE_COG, state);
    }
}
