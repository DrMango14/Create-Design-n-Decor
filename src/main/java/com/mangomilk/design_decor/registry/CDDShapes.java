package com.mangomilk.design_decor.registry;

import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

import static net.minecraft.core.Direction.SOUTH;
import static net.minecraft.core.Direction.UP;

public class CDDShapes {

    public static final VoxelShaper
            STEPPED_LEVER = shape(4, 0, 0, 12, 5, 16)
            .forHorizontalAxis(),



            STEPPED_LEVER_CEILING = shape(4, 11, 0, 15, 16, 16)
                    .forHorizontalAxis(),
            STEPPED_LEVER_WALL = shape(4, 0, 0, 12, 16, 5)
                    .forHorizontal(SOUTH),


            BREAKER_SWITCH = shape(4, 0, 2, 12, 3, 14)
                    .forHorizontalAxis(),
            BREAKER_SWITCH_CEILING = shape(4, 13, 2, 15, 16, 14)
                    .forHorizontalAxis(),
            BREAKER_SWITCH_WALL = shape(4, 2, 0, 12, 14, 3)
                    .forHorizontal(SOUTH),

            DIAGONAL_METAL_SUPPORT = shape(3, 0, 11, 13, 10, 16).add(0, 12, 0, 16, 16, 16)
                    .forHorizontal(SOUTH),

            METAL_SUPPORT = shape(4, 13, 0, 12, 16, 16).add(4, 0, 4, 12, 13, 12)
            .forHorizontalAxis()


                    ;
    public static final VoxelShape
            CATWALK_DOWN = shape(0, 0, 0, 16, 2, 16).build(),
            CATWALK = shape(0, 14, 0, 16, 16, 16).build(),

            WOODEN_SUPPORT = shape(4, 0, 4, 12, 16, 12).build(),

            EMPTY = shape(0, 0, 0, 0, 0, 0).build();



    private static CDDShapes.Builder shape(VoxelShape shape) {
        return new CDDShapes.Builder(shape);
    }

    private static CDDShapes.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }

    private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static class Builder {

        private VoxelShape shape;

        public Builder(VoxelShape shape) {
            this.shape = shape;
        }

        public CDDShapes.Builder add(VoxelShape shape) {
            this.shape = Shapes.or(this.shape, shape);
            return this;
        }

        public CDDShapes.Builder add(double x1, double y1, double z1, double x2, double y2, double z2) {
            return add(cuboid(x1, y1, z1, x2, y2, z2));
        }

        public CDDShapes.Builder erase(double x1, double y1, double z1, double x2, double y2, double z2) {
            this.shape = Shapes.join(shape, cuboid(x1, y1, z1, x2, y2, z2), BooleanOp.ONLY_FIRST);
            return this;
        }

        public VoxelShape build() {
            return shape;
        }

        public VoxelShaper build(BiFunction<VoxelShape, Direction, VoxelShaper> factory, Direction direction) {
            return factory.apply(shape, direction);
        }

        public VoxelShaper build(BiFunction<VoxelShape, Direction.Axis, VoxelShaper> factory, Direction.Axis axis) {
            return factory.apply(shape, axis);
        }

        public VoxelShaper forDirectional(Direction direction) {
            return build(VoxelShaper::forDirectional, direction);
        }

        public VoxelShaper forAxis() {
            return build(VoxelShaper::forAxis, Direction.Axis.Y);
        }

        public VoxelShaper forHorizontalAxis() {
            return build(VoxelShaper::forHorizontalAxis, Direction.Axis.Z);
        }

        public VoxelShaper forHorizontal(Direction direction) {
            return build(VoxelShaper::forHorizontal, direction);
        }

        public VoxelShaper forDirectional() {
            return forDirectional(UP);
        }

    }

}