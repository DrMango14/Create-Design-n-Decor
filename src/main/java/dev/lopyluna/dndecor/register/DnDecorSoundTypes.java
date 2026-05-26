package dev.lopyluna.dndecor.register;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

@SuppressWarnings("unused")
public class DnDecorSoundTypes {

    public static SoundType CRACKLE_STONE = new DeferredSoundType(0.9f, 0.7f,
            () -> SoundEvents.GILDED_BLACKSTONE_BREAK,
            () -> SoundEvents.GILDED_BLACKSTONE_STEP,
            () -> SoundEvents.GILDED_BLACKSTONE_PLACE,
            () -> SoundEvents.GILDED_BLACKSTONE_HIT,
            () -> SoundEvents.GILDED_BLACKSTONE_FALL
    );

    public static SoundType METAL_HEAVY  = new DeferredSoundType(0.9f, 0.5F,
            () -> SoundEvents.POLISHED_DEEPSLATE_BREAK,
            () -> SoundEvents.POLISHED_DEEPSLATE_STEP,
            () -> SoundEvents.POLISHED_DEEPSLATE_PLACE,
            () -> SoundEvents.POLISHED_DEEPSLATE_HIT,
            () -> SoundEvents.POLISHED_DEEPSLATE_FALL
    );

    public static SoundType NETHERITE_HEAVY  = new DeferredSoundType(0.9f, 0.5F,
            () -> SoundEvents.NETHERITE_BLOCK_BREAK,
            () -> SoundEvents.NETHERITE_BLOCK_STEP,
            () -> SoundEvents.NETHERITE_BLOCK_PLACE,
            () -> SoundEvents.NETHERITE_BLOCK_HIT,
            () -> SoundEvents.NETHERITE_BLOCK_FALL
    );

    public static SoundType COPPER_HEAVY  = new DeferredSoundType(0.9f, 0.5F,
            () -> SoundEvents.COPPER_BREAK,
            () -> SoundEvents.COPPER_STEP,
            () -> SoundEvents.COPPER_PLACE,
            () -> SoundEvents.COPPER_HIT,
            () -> SoundEvents.COPPER_FALL
    );

    public static SoundType CHAIN_HEAVY  = new DeferredSoundType(0.9f, 0.75F,
            () -> SoundEvents.CHAIN_BREAK,
            () -> SoundEvents.CHAIN_STEP,
            () -> SoundEvents.CHAIN_PLACE,
            () -> SoundEvents.CHAIN_HIT,
            () -> SoundEvents.CHAIN_FALL
    );

    public static SoundType CHAIN_LIGHT  = new DeferredSoundType(0.9f, 1.5F,
            () -> SoundEvents.CHAIN_BREAK,
            () -> SoundEvents.CHAIN_STEP,
            () -> SoundEvents.CHAIN_PLACE,
            () -> SoundEvents.CHAIN_HIT,
            () -> SoundEvents.CHAIN_FALL
    );
}
