package dev.lopyluna.dndecor.content.configs;

import dev.lopyluna.dndecor.content.configs.server.DKinetics;
import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DServer extends ConfigBase {
    public final ConfigGroup server = group(0,
            "server", "Configs for the World");

    public final DKinetics kinetics = nested(0, DKinetics::new, "Parameters and abilities of DnDecor's kinetic mechanisms");

    @Override
    public @NotNull String getName() {
        return "server";
    }

    private static class Comments {
    }
}
