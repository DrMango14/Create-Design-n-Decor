package dev.lopyluna.dndecor.register.helpers;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetalTypeHelper {
    public static List<Map<String, TagKey<Block>>> mineableBlocksTag = new ArrayList<>();

    public static void create(String type, TagKey<Block> tag) {
        Map<String, TagKey<Block>> map = new HashMap<>();
        map.put(type, tag);
        mineableBlocksTag.add(map);
    }
}
