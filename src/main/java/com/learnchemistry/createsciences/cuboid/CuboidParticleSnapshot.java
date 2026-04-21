package com.learnchemistry.createsciences.cuboid;

public record CuboidParticleSnapshot(
        CuboidVector previousPosition,
        CuboidVector position,
        CuboidVector velocity,
        double size,
        CuboidParticleColor color,
        boolean onSurface
) {
}
