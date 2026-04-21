package com.learnchemistry.createsciences.cuboid;

@FunctionalInterface
public interface CuboidWorldCollider {
    CuboidWorldCollider NONE = (position, halfSize) -> Double.NEGATIVE_INFINITY;

    double floorYAt(CuboidVector position, double halfSize);
}
