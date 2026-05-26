package dev.lopyluna.dndecor;

import com.simibubi.create.foundation.utility.DyeHelper;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public class DnDecorUtils {

    public static ItemInteractionResult itemResult(InteractionResult result) {
        return switch (result) {
            case SUCCESS -> ItemInteractionResult.SUCCESS;
            case CONSUME -> ItemInteractionResult.CONSUME;
            case CONSUME_PARTIAL -> ItemInteractionResult.CONSUME_PARTIAL;
            case SUCCESS_NO_ITEM_USED -> ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            case PASS -> ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            case FAIL -> ItemInteractionResult.FAIL;
        };
    }

    public static ItemLike getWool(DyeColor color) {
        var depot = DnDecorBlocks.isDyeDepotColor(color);
        if (depot) return com.ninni.dye_depot.registry.DDBlocks.WOOL.get(color).orElse(Blocks.WHITE_WOOL);
        return DyeHelper.getWoolOfDye(color);
    }

    public static boolean randomChance(int chance, Level level) {
        int newChance = Mth.clamp(chance, 0, 100);
        return newChance != 0 && level.getRandom().nextInt(1,  100) <= newChance;
    }

    public static boolean randomChance(double chance, Level level) {
        int newChance = Mth.clamp(((int) chance * 100), 0, 100);
        return newChance != 0 && level.getRandom().nextInt(1,  100) <= newChance;
    }
}
