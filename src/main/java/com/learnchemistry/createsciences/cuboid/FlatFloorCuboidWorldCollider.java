package com.learnchemistry.createsciences.cuboid;

public record FlatFloorCuboidWorldCollider(double floorY) implements CuboidWorldCollider {
    public static FlatFloorCuboidWorldCollider at(double floorY) {
        return new FlatFloorCuboidWorldCollider(floorY);
    }

    @Override
    public double floorYAt(CuboidVector position, double halfSize) {
        return floorY;
    }
}
