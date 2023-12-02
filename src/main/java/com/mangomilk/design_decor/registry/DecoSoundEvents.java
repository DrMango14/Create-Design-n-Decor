package com.mangomilk.design_decor.registry;

import com.mangomilk.design_decor.CreateMMBuilding;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class DecoSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreateMMBuilding.MOD_ID);

    public static RegistryObject<SoundEvent> LARGE_CHAIN_BREAK = registerSoundEvent("large_chain_break");
    public static RegistryObject<SoundEvent> LARGE_CHAIN_STEP = registerSoundEvent("large_chain_step");
    public static RegistryObject<SoundEvent> LARGE_CHAIN_PLACE = registerSoundEvent("large_chain_place");
    public static RegistryObject<SoundEvent> LARGE_CHAIN_HIT = registerSoundEvent("large_chain_hit");
    public static RegistryObject<SoundEvent> LARGE_CHAIN_FALL = registerSoundEvent("large_chain_fall");

    public static RegistryObject<SoundEvent> FLOODLIGHT_ON = registerSoundEvent("floodlight_on");
    public static RegistryObject<SoundEvent> FLOODLIGHT_OFF = registerSoundEvent("floodlight_off");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateMMBuilding.MOD_ID, name)));
    }



    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
