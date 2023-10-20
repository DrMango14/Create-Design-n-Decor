package com.mangomilk.design_decor.blocks.large_boiler.gold;

import com.mangomilk.design_decor.blocks.large_boiler.aluminum.AluminumLargeBoilerBlock;
import com.mangomilk.design_decor.registry.MmbBlocks;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GoldLargeBoilerBlockItem extends BlockItem {

    public GoldLargeBoilerBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        InteractionResult result = super.place(ctx);
        if (result != InteractionResult.FAIL)
            return result;
        Direction clickedFace = ctx.getClickedFace();
        if (clickedFace.getAxis() != ((GoldLargeBoilerBlock) getBlock()).getAxisForPlacement(ctx))
            result = super.place(BlockPlaceContext.at(ctx, ctx.getClickedPos()
                    .relative(clickedFace), clickedFace));
        if (result == InteractionResult.FAIL && ctx.getLevel()
                .isClientSide())
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showBounds(ctx));
        return result;
    }

    @OnlyIn(Dist.CLIENT)
    public void showBounds(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Direction.Axis axis = ((GoldLargeBoilerBlock) getBlock()).getAxisForPlacement(context);
        Vec3 contract = Vec3.atLowerCornerOf(Direction.get(Direction.AxisDirection.POSITIVE, axis)
                .getNormal());
        if (!(context.getPlayer()instanceof LocalPlayer localPlayer))
            return;
        CreateClient.OUTLINER.showAABB(Pair.of("waterwheel", pos), new AABB(pos).inflate(1)
                        .deflate(contract.x, contract.y, contract.z))
                .colored(0xFF_ff5d6c);
        Lang.translate("large_water_wheel.not_enough_space")
                .color(0xFF_ff5d6c)
                .sendStatus(localPlayer);
    }
}
