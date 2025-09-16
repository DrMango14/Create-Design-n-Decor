package dev.lopyluna.dndecor.register.helpers.list_providers;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndecor.content.entries.BoltEntry;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MetalTypeBoltBlockList<T extends Block> implements Iterable<BoltEntry<T>> {
    private final BoltEntry<?>[] values;

    public MetalTypeBoltBlockList(Function<NonNullSupplier<MaterialTypeProvider.MetalType>, BoltEntry<? extends T>> filler) {
        values = new BoltEntry<?>[MaterialTypeProvider.metalTypes.size()];
        for (var type : MaterialTypeProvider.metalTypes) {
            int index = MaterialTypeProvider.metalTypes.indexOf(type);
            if (index >= 0) values[index] = filler.apply(type);
        }
    }

    @SuppressWarnings("unchecked")
    public BoltEntry<T> get(MaterialTypeProvider.MetalType metal) {
        NonNullSupplier<MaterialTypeProvider.MetalType> type = () -> metal;
        int index = MaterialTypeProvider.metalTypes.indexOf(type);
        if (index >= 0) return (BoltEntry<T>) values[index];
        return null;
    }

    @SuppressWarnings("unchecked")
    public BoltEntry<T> get(NonNullSupplier<MaterialTypeProvider.MetalType> type) {
        int index = MaterialTypeProvider.metalTypes.indexOf(type);
        if (index >= 0) return (BoltEntry<T>) values[index];
        return null;
    }

    public boolean contains(Block block) {
        for (BoltEntry<?> entry : values) if (entry.is(block)) return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public BoltEntry<T>[] toArray() {
        return (BoltEntry<T>[]) Arrays.copyOf(values, values.length);
    }

    @Override
    public @NotNull Iterator<BoltEntry<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public BoltEntry<T> next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (BoltEntry<T>) values[index++];
            }
        };
    }
}
