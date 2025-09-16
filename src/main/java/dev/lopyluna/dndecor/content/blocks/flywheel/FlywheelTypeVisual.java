package dev.lopyluna.dndecor.content.blocks.flywheel;

import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class FlywheelTypeVisual extends FlywheelVisual {

    public FlywheelTypeVisual(PartialModel model, VisualizationContext context, FlywheelBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(model)).stealInstance(wheel);
    }
}
