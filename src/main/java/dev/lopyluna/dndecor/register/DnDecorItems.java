package dev.lopyluna.dndecor.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerItem;
import dev.lopyluna.dndecor.content.items.FullBeltConnectorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndecor.DnDecor.REG;
import static net.minecraft.world.item.DyeColor.BLUE;


@SuppressWarnings("unused")
public class DnDecorItems {

    public static final ItemEntry<FullBeltConnectorItem> BELT_CONNECTOR = REG.item("belt_connector", FullBeltConnectorItem::new)
            .lang("Mechanical Belt (Full)")
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("CC").pattern("CC")
                    .define('C', AllItems.BELT_CONNECTOR)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).register();

    public static final ItemEntry<ColoredStorageContainerItem> STORAGE_CONTAINERS = REG.item("storage_container", p -> new ColoredStorageContainerItem(p, BLUE))
            .lang("Storage Container")
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("CC").pattern("CC")
                    .define('C', AllBlocks.ITEM_VAULT)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).model((c, p) -> p.withExistingParent("item/" + c.getName(), DnDecor.loc("item/blue_storage_container")))
            .register();

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static void register() {}
}
