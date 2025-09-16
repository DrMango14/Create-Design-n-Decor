package dev.lopyluna.dndecor.register.helpers.list_providers;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MetalTypeBlockList<T extends Block> implements Iterable<BlockEntry<T>> {
    private final BlockEntry<?>[] values;

    public MetalTypeBlockList(Function<NonNullSupplier<MaterialTypeProvider.MetalType>, BlockEntry<T>> filler) {
        values = new BlockEntry<?>[MaterialTypeProvider.metalTypes.size()];
        for (var type : MaterialTypeProvider.metalTypes) {
            int index = MaterialTypeProvider.metalTypes.indexOf(type);
            if (index >= 0) values[index] = filler.apply(type);
        }
    }

    @SuppressWarnings("unchecked")
    public BlockEntry<T> get(MaterialTypeProvider.MetalType metal) {
        NonNullSupplier<MaterialTypeProvider.MetalType> type = () -> metal;
        int index = MaterialTypeProvider.metalTypes.indexOf(type);
        if (index >= 0) return (BlockEntry<T>) values[index];
        return null;
    }

    @SuppressWarnings("unchecked")
    public BlockEntry<T> get(NonNullSupplier<MaterialTypeProvider.MetalType> type) {
        int index = MaterialTypeProvider.metalTypes.indexOf(type);
        if (index >= 0) return (BlockEntry<T>) values[index];
        return null;
    }

    public boolean contains(Block block) {
        for (BlockEntry<?> entry : values) if (entry.is(block)) return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public BlockEntry<T>[] toArray() {
        return (BlockEntry<T>[]) Arrays.copyOf(values, values.length);
    }

    @Override
    public @NotNull Iterator<BlockEntry<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public BlockEntry<T> next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (BlockEntry<T>) values[index++];
            }
        };
    }
}
