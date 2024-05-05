package com.mangomilk.design_decor.blocks.millstone;

import com.mangomilk.design_decor.registry.MmbBlocks;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.mangomilk.design_decor.registry.MmbBlocks.*;

public class MillstoneInteractionTypes {
    private static final Map<ResourceLocation, ArmInteractionPointType> MILLSTONE_TYPES = new HashMap<>();
    private static final Map<BlockEntry<? extends MillstoneBlock>, Function<BlockState, Boolean>> MILLSTONE_BLOCK_CHECKERS = new HashMap<>();

    static {
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.ASURINE_MILLSTONE, ASURINE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.CALCITE_MILLSTONE, CALCITE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.CRIMSITE_MILLSTONE, CRIMSITE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.DEEPSLATE_MILLSTONE, DEEPSLATE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.DIORITE_MILLSTONE, DIORITE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.DRIPSTONE_MILLSTONE, DRIPSTONE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.GRANITE_MILLSTONE, GRANITE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.LIMESTONE_MILLSTONE, LIMESTONE_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.OCHRUM_MILLSTONE, OCHRUM_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.SCORCHIA_MILLSTONE, SCORCHIA_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.SCORIA_MILLSTONE, SCORIA_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.TUFF_MILLSTONE, TUFF_MILLSTONE::has);
        MILLSTONE_BLOCK_CHECKERS.put(MmbBlocks.VERIDIUM_MILLSTONE, VERIDIUM_MILLSTONE::has);
    }

    public static void register() {
        MILLSTONE_BLOCK_CHECKERS.forEach((key, value) ->
                MILLSTONE_TYPES.put(key.getId(), register(key.getId(), id -> new GenericMillstoneType(id, value)))
        );
    }

    private static <T extends ArmInteractionPointType> T register(ResourceLocation id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(id);
        ArmInteractionPointType.register(type);
        return type;
    }

    public static class GenericMillstoneType extends AllArmInteractionPointTypes.MillstoneType {
        private final Function<BlockState, Boolean> stateChecker;

        public GenericMillstoneType(ResourceLocation id, Function<BlockState, Boolean> stateChecker) {
            super(id);
            this.stateChecker = stateChecker;
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return stateChecker.apply(state);
        }
    }
}
