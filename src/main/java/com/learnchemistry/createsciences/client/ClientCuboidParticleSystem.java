package com.learnchemistry.createsciences.client;

import com.learnchemistry.createsciences.cuboid.CuboidParticleColor;
import com.learnchemistry.createsciences.cuboid.CuboidLiquidSettings;
import com.learnchemistry.createsciences.cuboid.CuboidParticleMaterial;
import com.learnchemistry.createsciences.cuboid.CuboidParticleSnapshot;
import com.learnchemistry.createsciences.cuboid.CuboidParticleSpawn;
import com.learnchemistry.createsciences.cuboid.CuboidParticleWorld;
import com.learnchemistry.createsciences.cuboid.CuboidVector;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public final class ClientCuboidParticleSystem {
    private static final int MAX_PARTICLES = 1200;
    private static final int TESTING_BURST_SIZE = 56;
    private static final double TESTING_PARTICLE_SIZE = 0.125;

    private final CuboidParticleWorld world = new CuboidParticleWorld(MAX_PARTICLES, CuboidParticleMaterial.water());
    private final CuboidLiquidSettings liquidSettings;
    private final Random random = new Random();

    public ClientCuboidParticleSystem() {
        this(new CuboidLiquidSettings());
    }

    public ClientCuboidParticleSystem(CuboidLiquidSettings liquidSettings) {
        this.liquidSettings = Objects.requireNonNull(liquidSettings);
    }

    public void spawnTestingLiquid(Vec3 location, Direction face) {
        CuboidVector normal = new CuboidVector(face.getStepX(), face.getStepY(), face.getStepZ());
        CuboidVector origin = new CuboidVector(location.x, location.y, location.z).add(normal.scale(0.16));
        CuboidParticleColor color = liquidSettings.selectedColor();
        if (face.getAxis().isHorizontal()) {
            origin = origin.add(0.0, 0.12, 0.0);
        } else if (face == Direction.UP) {
            origin = origin.add(0.0, 0.10, 0.0);
        }

        for (int i = 0; i < TESTING_BURST_SIZE; i++) {
            CuboidVector jitter = new CuboidVector(randomOffset(0.18), random.nextDouble() * 0.12, randomOffset(0.18));
            CuboidVector velocity = new CuboidVector(
                    randomOffset(0.035) + face.getStepX() * 0.018,
                    0.035 + random.nextDouble() * 0.025 + Math.max(0, face.getStepY()) * 0.025,
                    randomOffset(0.035) + face.getStepZ() * 0.018
            );

            world.spawn(new CuboidParticleSpawn(
                    origin.add(jitter),
                    velocity,
                    TESTING_PARTICLE_SIZE,
                    color,
                    260
            ));
        }
    }

    public void tick(ClientLevel level) {
        world.tick(new LevelCuboidWorldCollider(level));
    }

    public List<CuboidParticleSnapshot> snapshots() {
        return world.snapshots();
    }

    public void clear() {
        world.clear();
    }

    private double randomOffset(double range) {
        return (random.nextDouble() - 0.5) * range;
    }
}
