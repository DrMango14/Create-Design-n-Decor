package com.mangomilk.design_decor.blocks.industrial_gear;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mangomilk.design_decor.registry.CDDPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class IndustrialGearInstance  extends SingleRotatingInstance<BracketedKineticBlockEntity> {

    protected RotatingData additionalShaft;

    public IndustrialGearInstance(MaterialManager materialManager, BracketedKineticBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        super.init();
        if (!ICogWheel.isLargeCog(blockEntity.getBlockState()))
            return;

        // Large cogs sometimes have to offset their teeth by 11.25 degrees in order to
        // mesh properly

        float speed = blockEntity.getSpeed();
        Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        BlockPos pos = blockEntity.getBlockPos();
        float offset = BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos);
        Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        Instancer<RotatingData> half = getRotatingMaterial().getModel(CDDPartialModels.EMPTY, blockState,
                facing, () -> this.rotateToAxis(axis));

        additionalShaft = setup(half.createInstance(), speed);
        additionalShaft.setRotationOffset(offset);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        if (!ICogWheel.isLargeCog(blockEntity.getBlockState()))
            return super.getModel();

        Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        return getRotatingMaterial().getModel(CDDPartialModels.SHAFTLESS_LARGE_COGWHEEL, blockState, facing,
                () -> this.rotateToAxis(axis));
    }

    private PoseStack rotateToAxis(Direction.Axis axis) {
        Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        PoseStack poseStack = new PoseStack();
        TransformStack.cast(poseStack)
                .centre()
                .rotateToFace(facing)
                .multiply(Axis.XN.rotationDegrees(-90))
                .unCentre();
        return poseStack;
    }

    @Override
    public void update() {
        super.update();
        if (additionalShaft != null) {
            updateRotation(additionalShaft);
            additionalShaft.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos));
        }
    }

    @Override
    public void updateLight() {
        super.updateLight();
        if (additionalShaft != null)
            relight(pos, additionalShaft);
    }

    @Override
    public void remove() {
        super.remove();
        if (additionalShaft != null)
            additionalShaft.delete();
    }

}
