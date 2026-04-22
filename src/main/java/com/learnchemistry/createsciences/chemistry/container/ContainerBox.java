package com.learnchemistry.createsciences.chemistry.container;

import com.learnchemistry.createsciences.cuboid.CuboidVector;

public record ContainerBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
    public ContainerBox {
        if (minX >= maxX || minY >= maxY || minZ >= maxZ) {
            throw new IllegalArgumentException("Container box minimums must be lower than maximums.");
        }
    }

    public boolean contains(CuboidVector position, double halfSize) {
        return position.x() - halfSize >= minX
                && position.y() - halfSize >= minY
                && position.z() - halfSize >= minZ
                && position.x() + halfSize <= maxX
                && position.y() + halfSize <= maxY
                && position.z() + halfSize <= maxZ;
    }

    public CuboidVector clamp(CuboidVector position, double halfSize) {
        return new CuboidVector(
                clamp(position.x(), minX + halfSize, maxX - halfSize),
                clamp(position.y(), minY + halfSize, maxY - halfSize),
                clamp(position.z(), minZ + halfSize, maxZ - halfSize)
        );
    }

    public double distanceSqrTo(CuboidVector position) {
        double dx = distanceToRange(position.x(), minX, maxX);
        double dy = distanceToRange(position.y(), minY, maxY);
        double dz = distanceToRange(position.z(), minZ, maxZ);
        return dx * dx + dy * dy + dz * dz;
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static double distanceToRange(double value, double minimum, double maximum) {
        if (value < minimum) {
            return minimum - value;
        }
        if (value > maximum) {
            return value - maximum;
        }
        return 0.0;
    }
}
