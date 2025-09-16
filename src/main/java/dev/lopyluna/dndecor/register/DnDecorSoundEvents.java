package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllSoundEvents;
import dev.lopyluna.dndecor.DnDecor;

public class DnDecorSoundEvents {


    private static AllSoundEvents.SoundEntryBuilder create(String name) {
        return AllSoundEvents.create(DnDecor.asResource(name));
    }
}
