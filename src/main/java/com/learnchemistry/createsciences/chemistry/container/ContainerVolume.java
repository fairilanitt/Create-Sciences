package com.learnchemistry.createsciences.chemistry.container;

import com.learnchemistry.createsciences.cuboid.CuboidVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ContainerVolume {
    private static final double TUBE_MIN = 0.375;
    private static final double TUBE_MAX = 0.625;

    private final List<ContainerBox> boxes;

    public ContainerVolume(Collection<ContainerBox> boxes) {
        if (boxes.isEmpty()) {
            throw new IllegalArgumentException("Container volume needs at least one box.");
        }
        this.boxes = List.copyOf(boxes);
    }

    public static ContainerVolume beaker() {
        return new ContainerVolume(List.of(new ContainerBox(0.25, 0.05, 0.25, 0.75, 0.88, 0.75)));
    }

    public static ContainerVolume glassTube(Direction... connections) {
        return glassTube(Arrays.asList(connections));
    }

    public static ContainerVolume glassTube(Collection<Direction> connections) {
        List<ContainerBox> boxes = new ArrayList<>();
        boxes.add(new ContainerBox(TUBE_MIN, TUBE_MIN, TUBE_MIN, TUBE_MAX, TUBE_MAX, TUBE_MAX));
        for (Direction direction : connections) {
            boxes.add(tubeSegment(direction));
        }
        return new ContainerVolume(boxes);
    }

    public boolean contains(CuboidVector position, double halfSize) {
        for (ContainerBox box : boxes) {
            if (box.contains(position, halfSize)) {
                return true;
            }
        }
        return false;
    }

    public CuboidVector clamp(CuboidVector position, double halfSize) {
        ContainerBox nearest = boxes.getFirst();
        double nearestDistance = nearest.distanceSqrTo(position);
        for (int i = 1; i < boxes.size(); i++) {
            ContainerBox box = boxes.get(i);
            double distance = box.distanceSqrTo(position);
            if (distance < nearestDistance) {
                nearest = box;
                nearestDistance = distance;
            }
        }
        return nearest.clamp(position, halfSize);
    }

    public VoxelShape toVoxelShape() {
        VoxelShape shape = Shapes.empty();
        for (ContainerBox box : boxes) {
            shape = Shapes.or(shape, Shapes.box(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        }
        return shape;
    }

    public List<ContainerBox> boxes() {
        return boxes;
    }

    private static ContainerBox tubeSegment(Direction direction) {
        return switch (direction) {
            case DOWN -> new ContainerBox(TUBE_MIN, 0.0, TUBE_MIN, TUBE_MAX, TUBE_MAX, TUBE_MAX);
            case UP -> new ContainerBox(TUBE_MIN, TUBE_MIN, TUBE_MIN, TUBE_MAX, 1.0, TUBE_MAX);
            case NORTH -> new ContainerBox(TUBE_MIN, TUBE_MIN, 0.0, TUBE_MAX, TUBE_MAX, TUBE_MAX);
            case SOUTH -> new ContainerBox(TUBE_MIN, TUBE_MIN, TUBE_MIN, TUBE_MAX, TUBE_MAX, 1.0);
            case WEST -> new ContainerBox(0.0, TUBE_MIN, TUBE_MIN, TUBE_MAX, TUBE_MAX, TUBE_MAX);
            case EAST -> new ContainerBox(TUBE_MIN, TUBE_MIN, TUBE_MIN, 1.0, TUBE_MAX, TUBE_MAX);
        };
    }
}
