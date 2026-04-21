package com.learnchemistry.createsciences.cuboid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuboidParticleWorldTest {
    @Test
    void liquidParticlesFallAndRestOnFloor() {
        CuboidParticleWorld world = new CuboidParticleWorld(64, CuboidParticleMaterial.water());
        world.spawn(new CuboidParticleSpawn(
                new CuboidVector(0.0, 1.0, 0.0),
                CuboidVector.ZERO,
                0.125,
                CuboidParticleColor.waterBlue(),
                200
        ));

        for (int i = 0; i < 80; i++) {
            world.tick(FlatFloorCuboidWorldCollider.at(0.0));
        }

        CuboidParticleSnapshot particle = world.snapshots().getFirst();
        assertEquals(0.0625, particle.position().y(), 0.0001);
        assertEquals(0.0, particle.velocity().y(), 0.0001);
        assertTrue(particle.onSurface());
    }

    @Test
    void adjacentLiquidParticlesSpreadSidewaysOnSurface() {
        CuboidParticleWorld world = new CuboidParticleWorld(64, CuboidParticleMaterial.water());
        world.spawn(new CuboidParticleSpawn(new CuboidVector(-0.02, 0.2, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        world.spawn(new CuboidParticleSpawn(new CuboidVector(0.0, 0.2, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        world.spawn(new CuboidParticleSpawn(new CuboidVector(0.02, 0.2, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));

        for (int i = 0; i < 100; i++) {
            world.tick(FlatFloorCuboidWorldCollider.at(0.0));
        }

        double minX = world.snapshots().stream().mapToDouble(p -> p.position().x()).min().orElseThrow();
        double maxX = world.snapshots().stream().mapToDouble(p -> p.position().x()).max().orElseThrow();

        assertTrue(maxX - minX > 0.18);
    }

    @Test
    void respectsParticleBudgetByRemovingOldestParticles() {
        CuboidParticleWorld world = new CuboidParticleWorld(2, CuboidParticleMaterial.water());
        world.spawn(new CuboidParticleSpawn(new CuboidVector(1.0, 1.0, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        world.spawn(new CuboidParticleSpawn(new CuboidVector(2.0, 1.0, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        world.spawn(new CuboidParticleSpawn(new CuboidVector(3.0, 1.0, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));

        assertEquals(2, world.snapshots().size());
        assertEquals(2.0, world.snapshots().get(0).position().x(), 0.0001);
        assertEquals(3.0, world.snapshots().get(1).position().x(), 0.0001);
    }

    @Test
    void overlappingLiquidParticlesSeparateAfterTick() {
        CuboidParticleWorld world = new CuboidParticleWorld(64, CuboidParticleMaterial.water());
        world.spawn(new CuboidParticleSpawn(new CuboidVector(0.0, 0.0625, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        world.spawn(new CuboidParticleSpawn(new CuboidVector(0.0, 0.0625, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));

        world.tick(FlatFloorCuboidWorldCollider.at(0.0));

        assertPairwiseSeparated(world.snapshots());
    }

    @Test
    void denseSurfaceLiquidClusterDoesNotLeaveOverlappingCuboids() {
        CuboidParticleWorld world = new CuboidParticleWorld(64, CuboidParticleMaterial.water());
        for (int i = 0; i < 5; i++) {
            world.spawn(new CuboidParticleSpawn(new CuboidVector(0.0, 0.0625, 0.0), CuboidVector.ZERO, 0.125, CuboidParticleColor.waterBlue(), 200));
        }

        for (int i = 0; i < 8; i++) {
            world.tick(FlatFloorCuboidWorldCollider.at(0.0));
        }

        assertPairwiseSeparated(world.snapshots());
    }

    private static void assertPairwiseSeparated(java.util.List<CuboidParticleSnapshot> particles) {
        for (int i = 0; i < particles.size(); i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                assertFalse(overlaps(particles.get(i), particles.get(j)), "Particles " + i + " and " + j + " overlap.");
            }
        }
    }

    private static boolean overlaps(CuboidParticleSnapshot first, CuboidParticleSnapshot second) {
        double minimumSeparation = (first.size() + second.size()) * 0.5 - 1.0E-6;
        return Math.abs(first.position().x() - second.position().x()) < minimumSeparation
                && Math.abs(first.position().y() - second.position().y()) < minimumSeparation
                && Math.abs(first.position().z() - second.position().z()) < minimumSeparation;
    }
}
