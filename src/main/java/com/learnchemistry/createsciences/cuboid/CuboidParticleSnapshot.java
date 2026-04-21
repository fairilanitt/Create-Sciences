package com.learnchemistry.createsciences.cuboid;

public record CuboidParticleSnapshot(
        CuboidVector previousPosition,
        CuboidVector position,
        CuboidVector velocity,
        double size,
        double visualSize,
        CuboidParticleColor color,
        boolean onSurface
) {
}
