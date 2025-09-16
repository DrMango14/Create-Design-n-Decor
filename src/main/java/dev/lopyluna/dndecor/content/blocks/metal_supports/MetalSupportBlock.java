package dev.lopyluna.dndecor.content.blocks.metal_supports;


import dev.lopyluna.dndecor.register.DnDecorShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MetalSupportBlock extends Block {
    public static final Property<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public MetalSupportBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{TOP, HORIZONTAL_AXIS});
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = updateState(defaultBlockState(),context.getLevel(),context.getClickedPos(),true);

        return state.setValue(HORIZONTAL_AXIS,context.getHorizontalDirection().getClockWise().getAxis());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        updateState(state,level,pos,false);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        updateState(state,level,pos,false);
    }

    public static BlockState updateState(BlockState state, Level level, BlockPos pos, boolean simulate){

        BlockState stateAbove = level.getBlockState(pos.above());
        BlockState newState = stateAbove.is(state.getBlock()) ? state.setValue(TOP,false) : state.setValue(TOP,true);


        if(!simulate)
            level.setBlock(pos,newState,2);

        return newState;


    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(TOP) ? DnDecorShapes.METAL_SUPPORT.get(state.getValue(HORIZONTAL_AXIS)) : DnDecorShapes.WOODEN_SUPPORT;

    }
}