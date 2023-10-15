package com.mangomilk.design_decor.base;

import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class DecorBuilderTransformer {

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> connected(
            Supplier<CTSpriteShiftEntry> ct) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new EncasedCTBehaviour(ct.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> layeredConnected(
            Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2) {
        return b -> b.initialProperties(SharedProperties::stone)
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                        .cubeColumn(c.getName(), ct.get()
                                        .getOriginalResourceLocation(),
                                ct2.get()
                                        .getOriginalResourceLocation())))
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(ct.get(), ct2.get())))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())));
    }
}
