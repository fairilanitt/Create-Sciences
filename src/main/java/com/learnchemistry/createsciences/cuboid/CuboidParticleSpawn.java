package com.learnchemistry.createsciences.cuboid;

public record CuboidParticleSpawn(
        CuboidVector position,
        CuboidVector velocity,
        double size,
        CuboidParticleColor color,
        int lifetimeTicks
) {
    public CuboidParticleSpawn {
        if (size <= 0.0) {
            throw new IllegalArgumentException("Cuboid particle size must be positive.");
        }
        if (lifetimeTicks <= 0) {
            throw new IllegalArgumentException("Cuboid particle lifetime must be positive.");
        }
    }
}
