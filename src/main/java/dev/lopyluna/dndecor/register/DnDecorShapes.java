package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllShapes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DnDecorShapes {

    public static final VoxelShaper DIAGONAL_METAL_SUPPORT = shape(3, 0, 11, 13, 10, 16).add(0, 12, 0, 16, 16, 16).forHorizontal(Direction.SOUTH);
    public static final VoxelShaper METAL_SUPPORT = shape(4, 13, 0, 12, 16, 16).add(4, 0, 4, 12, 13, 12).forHorizontalAxis();
    public static final VoxelShape  WOODEN_SUPPORT = shape(4, 0, 4, 12, 16, 12).build();

    public static AllShapes.Builder shape(VoxelShape shape) {
        return new AllShapes.Builder(shape);
    }

    public static AllShapes.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }



    public static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }
}
