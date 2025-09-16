package dev.lopyluna.dndecor.content.blocks.full_belt;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltGenerator;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class FullBeltGenerator extends BeltGenerator {

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        boolean casing = state.getValue(BeltBlock.CASING);

        if (!casing) return prov.models().getExistingFile(Create.asResource("block/belt/particle"));

        BeltPart part = state.getValue(BeltBlock.PART);
        Direction direction = state.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = state.getValue(BeltBlock.SLOPE);
        boolean downward = slope == BeltSlope.DOWNWARD;
        boolean diagonal = slope == BeltSlope.UPWARD || downward;
        boolean vertical = slope == BeltSlope.VERTICAL;
        boolean pulley = part == BeltPart.PULLEY;
        boolean sideways = slope == BeltSlope.SIDEWAYS;
        boolean negative = direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE;

        //if (!casing && pulley) part = BeltPart.MIDDLE;

        if ((vertical && negative || downward || sideways && negative) && part != BeltPart.MIDDLE && !pulley) part = part == BeltPart.END ? BeltPart.START : BeltPart.END;

        //if (!casing && vertical) slope = BeltSlope.HORIZONTAL;
        //if (casing && vertical) slope = BeltSlope.SIDEWAYS;
        if (vertical) slope = BeltSlope.SIDEWAYS;

        //String path = "block/" + (casing ? "belt_casing/" : "full_belt/");
        String path = "block/" + "belt_casing/";
        String slopeName = slope.getSerializedName();
        String partName = part.getSerializedName();

        if (diagonal) slopeName = "diagonal";

        //ResourceLocation location = prov.modLoc(path + slopeName + "_" + partName);
        ResourceLocation location = Create.asResource(path + slopeName + "_" + partName);
        return prov.models().getExistingFile(location);
    }
}
