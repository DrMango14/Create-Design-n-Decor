package dev.lopyluna.dndecor.content.blocks.container;

import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

public class DyedContainerConnectivity {
    public static void formMulti(DyedContainerBE be) {
        SearchCache cache = new SearchCache();
        List<DyedContainerBE> frontier = new ArrayList<>();
        frontier.add(be);
        formMulti(be.getLevel(), cache, frontier, getColor(be), isSolidColor(be));
    }

    private static void formMulti(Level level, SearchCache cache, List<DyedContainerBE> frontier, @Nullable DyeColor color, boolean solidColor) {
        if (level == null || frontier.isEmpty()) return;
        PriorityQueue<Pair<Integer, DyedContainerBE>> creationQueue = new PriorityQueue<>((a, b) -> b.getLeft() - a.getLeft());
        Set<BlockPos> visited = new HashSet<>();
        Direction.Axis mainAxis = frontier.getFirst().getMainConnectionAxis();

        int minX = mainAxis == Direction.Axis.Y ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int minY = mainAxis != Direction.Axis.Y ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int minZ = mainAxis == Direction.Axis.Y ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        for (DyedContainerBE part : frontier) {
            BlockPos pos = part.getBlockPos();
            minX = Math.min(pos.getX(), minX);
            minY = Math.min(pos.getY(), minY);
            minZ = Math.min(pos.getZ(), minZ);
        }
        if (mainAxis == Direction.Axis.Y) minX -= frontier.getFirst().getMaxWidth();
        if (mainAxis != Direction.Axis.Y) minY -= frontier.getFirst().getMaxWidth();
        if (mainAxis == Direction.Axis.Y) minZ -= frontier.getFirst().getMaxWidth();

        while (!frontier.isEmpty()) {
            DyedContainerBE part = frontier.removeFirst();
            BlockPos partPos = part.getBlockPos();
            if (!matchesVariant(part, color, solidColor) || !visited.add(partPos)) continue;

            int amount = tryToFormNewMulti(part, cache, true, color, solidColor);
            if (amount > 1) creationQueue.add(Pair.of(amount, part));

            for (Direction.Axis axis : Iterate.axes) {
                Direction dir = Direction.get(Direction.AxisDirection.NEGATIVE, axis);
                BlockPos next = partPos.relative(dir);
                if (next.getX() <= minX || next.getY() <= minY || next.getZ() <= minZ) continue;
                if (visited.contains(next)) continue;
                DyedContainerBE nextBe = partAt(level, next, color, solidColor);
                if (nextBe == null || nextBe.isRemoved()) continue;
                frontier.add(nextBe);
            }
        }

        visited.clear();
        while (!creationQueue.isEmpty()) {
            Pair<Integer, DyedContainerBE> next = creationQueue.poll();
            DyedContainerBE toCreate = next.getRight();
            if (!visited.add(toCreate.getBlockPos())) continue;
            tryToFormNewMulti(toCreate, cache, false, color, solidColor);
        }
    }

    private static int tryToFormNewMulti(DyedContainerBE be, SearchCache cache, boolean simulate, DyeColor color, boolean solidColor) {
        int bestWidth = 1;
        int bestAmount = -1;
        if (!be.isController() || !matchesVariant(be, color, solidColor)) return 0;

        for (int width = 1; width <= be.getMaxWidth(); width++) {
            int amount = tryToFormNewMultiOfWidth(be, width, cache, true, color, solidColor);
            if (amount < bestAmount) continue;
            bestWidth = width;
            bestAmount = amount;
        }

        if (!simulate) {
            int currentWidth = be.getWidth();
            if (currentWidth == bestWidth && currentWidth * currentWidth * be.getHeight() == bestAmount) return bestAmount;

            splitMultiAndInvalidate(be, cache);
            tryToFormNewMultiOfWidth(be, bestWidth, cache, false, color, solidColor);

            be.preventConnectivityUpdate();
            be.setWidth(bestWidth);
            be.setHeight(bestAmount / bestWidth / bestWidth);
            be.notifyMultiUpdated();
        }

        return bestAmount;
    }

    private static int tryToFormNewMultiOfWidth(DyedContainerBE be, int width, SearchCache cache, boolean simulate, @Nullable DyeColor color, boolean solidColor) {
        Level level = be.getLevel();
        if (level == null) return 0;

        int amount = 0;
        int height = 0;
        BlockPos origin = be.getBlockPos();
        Direction.Axis axis = be.getMainConnectionAxis();

        search:
        for (int yOffset = 0; yOffset < be.getMaxLength(axis, width); yOffset++) {
            for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
                BlockPos pos = switch (axis) {
                    case X -> origin.offset(yOffset, xOffset, zOffset);
                    case Y -> origin.offset(xOffset, yOffset, zOffset);
                    case Z -> origin.offset(xOffset, zOffset, yOffset);
                };

                Optional<DyedContainerBE> part = cache.getOrCache(level, pos, color, solidColor);
                if (part.isEmpty()) break search;

                DyedContainerBE controller = part.get();
                int otherWidth = controller.getWidth();
                if (otherWidth > width) break search;
                if (otherWidth == width && controller.getHeight() == be.getMaxLength(axis, width)) break search;
                if (axis != controller.getMainConnectionAxis()) break search;

                BlockPos controllerPos = controller.getBlockPos();
                if (!controllerPos.equals(origin)) {
                    if (axis == Direction.Axis.Y) {
                        if (controllerPos.getX() < origin.getX()) break search;
                        if (controllerPos.getZ() < origin.getZ()) break search;
                        if (controllerPos.getX() + otherWidth > origin.getX() + width) break search;
                        if (controllerPos.getZ() + otherWidth > origin.getZ() + width) break search;
                    } else {
                        if (axis == Direction.Axis.Z && controllerPos.getX() < origin.getX()) break search;
                        if (controllerPos.getY() < origin.getY()) break search;
                        if (axis == Direction.Axis.X && controllerPos.getZ() < origin.getZ()) break search;
                        if (axis == Direction.Axis.Z && controllerPos.getX() + otherWidth > origin.getX() + width) break search;
                        if (controllerPos.getY() + otherWidth > origin.getY() + width) break search;
                        if (axis == Direction.Axis.X && controllerPos.getZ() + otherWidth > origin.getZ() + width) break search;
                    }
                }
            }
            amount += width * width;
            height++;
        }

        if (simulate) return amount;

        for (int yOffset = 0; yOffset < height; yOffset++) for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos pos = switch (axis) {
                case X -> origin.offset(yOffset, xOffset, zOffset);
                case Y -> origin.offset(xOffset, yOffset, zOffset);
                case Z -> origin.offset(xOffset, zOffset, yOffset);
            };

            DyedContainerBE part = partAt(level, pos, color, solidColor);
            if (part == null || part == be) continue;

            splitMultiAndInvalidate(part, cache);
            part.setController(origin);
            part.preventConnectivityUpdate();
            cache.put(pos, be);
            part.setHeight(height);
            part.setWidth(width);
            part.notifyMultiUpdated();
        }

        be.notifyMultiUpdated();
        return amount;
    }

    private static void splitMultiAndInvalidate(DyedContainerBE be, @Nullable SearchCache cache) {
        Level level = be.getLevel();
        if (level == null) return;

        DyedContainerBE controller = be.getControllerBE();
        if (controller == null) return;

        int height = controller.getHeight();
        int width = controller.getWidth();
        if (width == 1 && height == 1) return;

        BlockPos origin = controller.getBlockPos();
        Direction.Axis axis = controller.getMainConnectionAxis();

        for (int yOffset = 0; yOffset < height; yOffset++) for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos pos = switch (axis) {
                case X -> origin.offset(yOffset, xOffset, zOffset);
                case Y -> origin.offset(xOffset, yOffset, zOffset);
                case Z -> origin.offset(xOffset, zOffset, yOffset);
            };

            DyedContainerBE partAt = partAt(level, pos, getColor(controller), isSolidColor(controller));
            if (partAt == null || !partAt.getController().equals(origin)) continue;

            partAt.removeController(true);
            if (cache != null) cache.put(pos, partAt);
        }
        level.invalidateCapabilities(controller.getBlockPos());
    }

    private static boolean matchesVariant(DyedContainerBE be, @Nullable DyeColor color, boolean solidColor) {
        return be != null && (color != null ? color == getColor(be) && solidColor == isSolidColor(be) : getColor(be) == null);
    }

    private static @Nullable DyeColor getColor(DyedContainerBE be) {
        var fromState = DyedContainerBlock.getColor(be.getBlockState());
        return fromState != null ? fromState : be.color;
    }

    private static boolean isSolidColor(DyedContainerBE be) {
        return DyedContainerBlock.isSolidColor(be.getBlockState()) || be.solidColor;
    }

    private static @Nullable DyedContainerBE partAt(BlockGetter level, BlockPos pos, @Nullable DyeColor color, boolean solidColor) {
        if (!(level.getBlockEntity(pos) instanceof DyedContainerBE be) || be.isRemoved()) return null;
        return matchesVariant(be, color, solidColor) ? be : null;
    }

    private static class SearchCache {
        private final Map<BlockPos, Optional<DyedContainerBE>> controllerMap = new HashMap<>();

        void put(BlockPos pos, DyedContainerBE target) {
            controllerMap.put(pos, Optional.of(target));
        }

        void putEmpty(BlockPos pos) {
            controllerMap.put(pos, Optional.empty());
        }

        Optional<DyedContainerBE> getOrCache(Level level, BlockPos pos, @Nullable DyeColor color, boolean solidColor) {
            Optional<DyedContainerBE> cached = controllerMap.get(pos);
            if (cached != null) return cached;

            DyedContainerBE partAt = partAt(level, pos, color, solidColor);
            if (partAt == null) {
                putEmpty(pos);
                return Optional.empty();
            }

            DyedContainerBE controller = partAt.getControllerBE();
            if (!matchesVariant(controller, color, solidColor)) {
                putEmpty(pos);
                return Optional.empty();
            }

            put(pos, controller);
            return Optional.of(controller);
        }
    }
}