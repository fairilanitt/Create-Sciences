package com.learnchemistry.createsciences.cuboid;

public record CuboidParticleMaterial(
        double gravity,
        double airDamping,
        double surfaceDamping,
        double spreadStrength,
        double interactionRadius,
        double maxHorizontalSpeed
) {
    public static CuboidParticleMaterial water() {
        return new CuboidParticleMaterial(0.035, 0.985, 0.90, 0.018, 0.24, 0.075);
    }
}
