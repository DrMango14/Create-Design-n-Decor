package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllShapes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DnDecorShapes {


    public static final VoxelShaper STEPPED_LEVER = shape((double)4.0F, (double)0.0F, (double)0.0F, (double)12.0F, (double)5.0F, (double)16.0F).forHorizontalAxis();
    public static final VoxelShaper STEPPED_LEVER_CEILING = shape((double)4.0F, (double)11.0F, (double)0.0F, (double)15.0F, (double)16.0F, (double)16.0F).forHorizontalAxis();
    public static final VoxelShaper STEPPED_LEVER_WALL = shape((double)4.0F, (double)0.0F, (double)0.0F, (double)12.0F, (double)16.0F, (double)5.0F).forHorizontal(Direction.SOUTH);
    public static final VoxelShaper BREAKER_SWITCH = shape((double)4.0F, (double)0.0F, (double)2.0F, (double)12.0F, (double)3.0F, (double)14.0F).forHorizontalAxis();
    public static final VoxelShaper BREAKER_SWITCH_CEILING = shape((double)4.0F, (double)13.0F, (double)2.0F, (double)15.0F, (double)16.0F, (double)14.0F).forHorizontalAxis();
    public static final VoxelShaper BREAKER_SWITCH_WALL = shape((double)4.0F, (double)2.0F, (double)0.0F, (double)12.0F, (double)14.0F, (double)3.0F).forHorizontal(Direction.SOUTH);
    public static final VoxelShaper DIAGONAL_METAL_SUPPORT = shape((double)3.0F, (double)0.0F, (double)11.0F, (double)13.0F, (double)10.0F, (double)16.0F).add((double)0.0F, (double)12.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F).forHorizontal(Direction.SOUTH);
    public static final VoxelShaper METAL_SUPPORT = shape((double)4.0F, (double)13.0F, (double)0.0F, (double)12.0F, (double)16.0F, (double)16.0F).add((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)13.0F, (double)12.0F).forHorizontalAxis();
    public static final VoxelShape  CATWALK_DOWN = shape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)2.0F, (double)16.0F).build();
    public static final VoxelShape  CATWALK = shape((double)0.0F, (double)14.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F).build();
    public static final VoxelShape  WOODEN_SUPPORT = shape((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)16.0F, (double)12.0F).build();
    public static final VoxelShape  EMPTY = shape((double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F).build();

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
