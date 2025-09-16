package dev.lopyluna.dndecor.content.blocks.bolt;

import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public enum BoltRotation implements StringRepresentable {
    D0("0", 0, 1, 7),
    D45("45", 1, 2, 0),
    D90("90", 2, 3, 1),
    D135("135", 3, 4, 2),
    D180("180", 4, 5, 3),
    D225("225", 5, 6, 4),
    D270("270", 6, 7, 5),
    D315("315", 7, 0, 6);

    public static final BoltRotation[] VALUES = values();
    public static final EnumCodec<BoltRotation> CODEC = StringRepresentable.fromEnum(BoltRotation::values);
    final String name;
    final int id, next, prev;

    BoltRotation(String name, int id, int next, int prev) {
        this.name = name;
        this.id = id;
        this.next = next;
        this.prev = prev;
    }

    public int getNextID() {
        return next;
    }

    public BoltRotation getNext() {
        return getByID(next);
    }

    public int getPrevID() {
        return prev;
    }

    public BoltRotation getPrev() {
        return getByID(prev);
    }

    public int getId() {
        return id;
    }

    public BoltRotation getRotation(Rotation rotation) {
        return switch (rotation) {
            case NONE -> this;
            case CLOCKWISE_90 -> getPrev().getPrev();
            case CLOCKWISE_180 -> getPrev().getPrev().getPrev().getPrev();
            case COUNTERCLOCKWISE_90 -> getNext().getNext();
        };
    }

    public static BoltRotation fromPlayerFacing(Player player) {
        return BoltRotation.fromYaw(player.getYRot());
    }

    public static BoltRotation fromYaw(double yaw) {
        yaw = (yaw + 360) % 360;
        return getByID((int) ((yaw + 22.5) / 45.0) % 8);
    }

    public static BoltRotation getByID(int id) {
        var bound = id;
        if (bound < 0) bound = 8;
        return VALUES[bound % 8];
    }

    public static BoltRotation getRandom(RandomSource random) {
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
