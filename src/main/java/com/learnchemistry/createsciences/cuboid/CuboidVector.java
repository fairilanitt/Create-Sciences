package com.learnchemistry.createsciences.cuboid;

public record CuboidVector(double x, double y, double z) {
    public static final CuboidVector ZERO = new CuboidVector(0.0, 0.0, 0.0);

    public CuboidVector add(CuboidVector other) {
        return new CuboidVector(x + other.x, y + other.y, z + other.z);
    }

    public CuboidVector add(double x, double y, double z) {
        return new CuboidVector(this.x + x, this.y + y, this.z + z);
    }

    public CuboidVector scale(double scalar) {
        return new CuboidVector(x * scalar, y * scalar, z * scalar);
    }

    public CuboidVector withY(double y) {
        return new CuboidVector(x, y, z);
    }

    public CuboidVector withHorizontal(double x, double z) {
        return new CuboidVector(x, y, z);
    }

    public double horizontalLengthSqr() {
        return x * x + z * z;
    }
}
