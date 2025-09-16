package dev.lopyluna.dndecor.register.helpers.list_providers;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class StoneTypeBEList<T extends BlockEntity> implements Iterable<BlockEntityEntry<T>> {
    private final BlockEntityEntry<?>[] values;

    public StoneTypeBEList(BiFunction<NonNullSupplier<Block>, String, BlockEntityEntry<? extends T>> filler) {
        MaterialTypeProvider.checkStoneTypeList();
        values = new BlockEntityEntry<?>[MaterialTypeProvider.stoneTypes.size()];
        for (var type : MaterialTypeProvider.stoneTypes) {
            int index = MaterialTypeProvider.stoneTypes.indexOf(type);
            var id = MaterialTypeProvider.stoneTypesRegister.get(type);
            if (index >= 0) values[index] = filler.apply(type, id);
        }
    }

    @SuppressWarnings("unchecked")
    public BlockEntityEntry<T> get(Block block) {
        NonNullSupplier<Block> type = () -> block;
        int index = MaterialTypeProvider.stoneTypes.indexOf(type);
        if (index >= 0) return (BlockEntityEntry<T>) values[index];
        return null;
    }

    @SuppressWarnings("unchecked")
    public BlockEntityEntry<T> get(NonNullSupplier<Block> type) {
        int index = MaterialTypeProvider.stoneTypes.indexOf(type);
        if (index >= 0) return (BlockEntityEntry<T>) values[index];
        return null;
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
                if (!hasNext()) throw new NoSuchElementException();
                return (BlockEntityEntry<T>) values[index++];
            }
        };
    }
}
