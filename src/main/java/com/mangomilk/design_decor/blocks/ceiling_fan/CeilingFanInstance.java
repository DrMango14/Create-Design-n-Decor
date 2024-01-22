package com.mangomilk.design_decor.blocks.ceiling_fan;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;

public class CeilingFanInstance extends KineticBlockEntityInstance<CeilingFanBlockEntity> implements DynamicInstance {

    protected final RotatingData shaft;
    protected final ModelData fan;
    protected float lastAngle = Float.NaN;

    public CeilingFanInstance(MaterialManager materialManager, CeilingFanBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        shaft = setup(getRotatingMaterial().getModel(shaft())
                .createInstance());
        fan = getTransformMaterial()
                .getModel(CDDPartialModels.CEILING_FAN, blockState)
                .createInstance();

        animate(blockEntity.angle);
    }

    @Override
    public void beginFrame() {

        float partialTicks = AnimationTickHolder.getPartialTicks();

        float speed = blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - lastAngle) < 0.001)
            return;

        animate(angle);

        lastAngle = angle;
    }

    private void animate(float angle) {
        PoseStack ms = new PoseStack();
        TransformStack msr = TransformStack.cast(ms);

        msr.translate(getInstancePosition());
        msr.centre()
                .rotate(Direction.get(Direction.AxisDirection.POSITIVE, axis), AngleHelper.rad(angle))
                .unCentre();

        fan.setTransform(ms);
    }

    @Override
    public void update() {
        updateRotation(shaft);
    }

    @Override
    public void updateLight() {
        relight(pos, shaft, fan);
    }

    @Override
    public void remove() {
        shaft.delete();
        fan.delete();
    }

}