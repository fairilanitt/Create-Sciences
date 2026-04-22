package com.learnchemistry.createsciences.chemistry.catalog;

import com.learnchemistry.createsciences.cuboid.CuboidParticleColor;
import java.util.Objects;

public record VisualProfile(
        CuboidParticleColor color,
        double baseCuboidSize,
        CuboidSpawnStyle spawnStyle,
        boolean fragmented
) {
    public VisualProfile {
        Objects.requireNonNull(color);
        Objects.requireNonNull(spawnStyle);
        if (baseCuboidSize <= 0.0) {
            throw new IllegalArgumentException("baseCuboidSize must be positive.");
        }
    }
}
