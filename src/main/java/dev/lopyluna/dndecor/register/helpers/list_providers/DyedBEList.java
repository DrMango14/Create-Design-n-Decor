package dev.lopyluna.dndecor.register.helpers.list_providers;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class DyedBEList<T extends BlockEntity> implements Iterable<BlockEntityEntry<T>> {

    private static final int COLOR_AMOUNT = DyeColor.values().length;

    private final BlockEntityEntry<?>[] values = new BlockEntityEntry<?>[COLOR_AMOUNT];

    public DyedBEList(Function<DyeColor, BlockEntityEntry<? extends T>> filler) {
        for (DyeColor color : DyeColor.values()) values[color.ordinal()] = filler.apply(color);
    }

    @SuppressWarnings("unchecked")
    public BlockEntityEntry<T> get(DyeColor color) {
        return (BlockEntityEntry<T>) values[color.ordinal()];
    }

    public boolean contains(Block block) {
        for (BlockEntityEntry<?> entry : values) if (entry.is(block)) return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public BlockEntityEntry<T>[] toArray() {
        return (BlockEntityEntry<T>[]) Arrays.copyOf(values, values.length);
    }

    @Override
    public @NotNull Iterator<BlockEntityEntry<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public BlockEntityEntry<T> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return (BlockEntityEntry<T>) values[index++];
            }
        };
    }

}
