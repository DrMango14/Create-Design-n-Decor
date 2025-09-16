package dev.lopyluna.dndecor.content.blocks.frontlight;

import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation", "unused"})
public enum Frontlight implements StringRepresentable {
    EMPTY("empty", 0, 1, 2),
    TOP("top", 1, 2, 0),
    GRATE("grate", 2, 0, 1);

    public static final Frontlight[] VALUES = values();
    public static final EnumCodec<Frontlight> CODEC = StringRepresentable.fromEnum(Frontlight::values);
    final String name;
    final int id, next, prev;

    Frontlight(String name, int id, int next, int prev) {
        this.name = name;
        this.id = id;
        this.next = next;
        this.prev = prev;
    }

    public int getNextID() {
        return next;
    }

    public Frontlight getNext() {
        return getByID(next);
    }

    public int getPrevID() {
        return prev;
    }

    public Frontlight getPrev() {
        return getByID(prev);
    }

    public int getId() {
        return id;
    }

    public static Frontlight getByID(int id) {
        var bound = id;
        if (bound < 0) bound = 8;
        return VALUES[bound % 8];
    }

    public static Frontlight getRandom(RandomSource random) {
        return Util.getRandom(VALUES, random);
    }

    public String getName() {
        return name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
