package com.learnchemistry.createsciences.chemistry.container;

import com.learnchemistry.createsciences.block.GlassTubeBlock;
import com.learnchemistry.createsciences.cuboid.CuboidParticleColor;
import com.learnchemistry.createsciences.cuboid.CuboidParticleSpawn;
import com.learnchemistry.createsciences.cuboid.CuboidVector;
import net.minecraft.core.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChemistryContainerVolumeTest {
    @Test
    void beakerVolumeClampsCuboidsInsideTheGlassShell() {
        ContainerVolume beaker = ContainerVolume.beaker();
        double halfSize = 0.0625;

        CuboidVector clamped = beaker.clamp(new CuboidVector(0.05, 1.20, 0.95), halfSize);

        assertTrue(beaker.contains(clamped, halfSize));
        assertEquals(0.25 + halfSize, clamped.x(), 0.0001);
        assertEquals(0.88 - halfSize, clamped.y(), 0.0001);
        assertEquals(0.75 - halfSize, clamped.z(), 0.0001);
    }

    @Test
    void glassTubeVolumeExtendsThroughConnectedDirections() {
        ContainerVolume tube = ContainerVolume.glassTube(Direction.EAST, Direction.WEST);
        double halfSize = 0.03;

        assertTrue(tube.contains(new CuboidVector(0.08, 0.50, 0.50), halfSize));
        assertTrue(tube.contains(new CuboidVector(0.92, 0.50, 0.50), halfSize));
    }

    @Test
    void containedCuboidWorldKeepsParticlesInsideVolume() {
        ContainerCuboidParticleWorld world = new ContainerCuboidParticleWorld(ContainerVolume.beaker(), 16);
        world.spawn(new CuboidParticleSpawn(
                new CuboidVector(1.3, 1.3, -0.4),
                new CuboidVector(0.2, 0.1, -0.2),
                0.125,
                CuboidParticleColor.waterBlue(),
                100
        ));

        for (int i = 0; i < 4; i++) {
            world.tick();
        }

        assertTrue(ContainerVolume.beaker().contains(world.snapshots().getFirst().position(), 0.0625));
    }

    @Test
    void glassTubeExposesConnectionPropertyForEveryDirection() {
        assertEquals(6, GlassTubeBlock.CONNECTION_PROPERTIES.size());
        for (Direction direction : Direction.values()) {
            assertTrue(GlassTubeBlock.CONNECTION_PROPERTIES.containsKey(direction));
        }
    }
}
