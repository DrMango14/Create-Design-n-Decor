package dev.lopyluna.dndecor.content.configs;

import net.createmod.catnip.config.ConfigBase;

@SuppressWarnings({"unused", "NullableProblems"})
public class DClient extends ConfigBase {
    public final ConfigGroup client = group(0,
            "client", "Configs for the Client");

    @Override
    public String getName() {
        return "client";
    }

    private static class Comments {

        static String ponder = "Ponder settings";
    }
}
