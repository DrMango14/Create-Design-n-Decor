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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MillstoneInteractionTypes {
    private static final Map<ResourceLocation, ArmInteractionPointType> MILLSTONE_TYPES = new HashMap<>();
    private static final List<BlockEntry<? extends MillstoneBlock>> MILLSTONE_BLOCK_CHECKERS = new ArrayList<>();

    static {
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.ASURINE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.CALCITE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.CRIMSITE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.DEEPSLATE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.DIORITE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.DRIPSTONE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.GRANITE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.LIMESTONE_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.OCHRUM_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.SCORCHIA_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.SCORIA_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.TUFF_MILLSTONE);
        MILLSTONE_BLOCK_CHECKERS.add(MmbBlocks.VERIDIUM_MILLSTONE);
    }

    public static void register() {
        MILLSTONE_BLOCK_CHECKERS.forEach((entry) -> {
                    ResourceLocation resource = entry.getId();
                    MILLSTONE_TYPES.put(resource, register(resource, id -> new GenericMillstoneType(id, entry::has)));
        });
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
