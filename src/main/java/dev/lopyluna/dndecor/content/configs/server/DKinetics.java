package dev.lopyluna.dndecor.content.configs.server;

import dev.lopyluna.dndecor.content.configs.server.kinetics.DStress;
import net.createmod.catnip.config.ConfigBase;

public class DKinetics extends ConfigBase {

    public final DStress stressValues = nested(1, DStress::new, "Fine tune the kinetic stats of individual components");

    @SuppressWarnings("NullableProblems")
    @Override
    public String getName() {
        return "kinetics";
    }
}
