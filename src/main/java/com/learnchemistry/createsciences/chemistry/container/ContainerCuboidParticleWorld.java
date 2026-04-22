package com.learnchemistry.createsciences.chemistry.container;

import com.learnchemistry.createsciences.cuboid.CuboidParticleSnapshot;
import com.learnchemistry.createsciences.cuboid.CuboidParticleSpawn;
import com.learnchemistry.createsciences.cuboid.CuboidVector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ContainerCuboidParticleWorld {
    private static final double DAMPING = 0.82;

    private ContainerVolume volume;
    private final int maxParticles;
    private final List<ContainedParticle> particles = new ArrayList<>();

    public ContainerCuboidParticleWorld(ContainerVolume volume, int maxParticles) {
        if (maxParticles <= 0) {
            throw new IllegalArgumentException("maxParticles must be positive.");
        }
        this.volume = volume;
        this.maxParticles = maxParticles;
    }

    public void setVolume(ContainerVolume volume) {
        this.volume = volume;
        for (ContainedParticle particle : particles) {
            particle.clampTo(volume);
        }
    }

    public void spawn(CuboidParticleSpawn spawn) {
        while (particles.size() >= maxParticles) {
            particles.removeFirst();
        }
        particles.add(new ContainedParticle(spawn, volume));
    }

    public void tick() {
        for (ContainedParticle particle : particles) {
            particle.tick(volume);
        }
        particles.removeIf(ContainedParticle::isExpired);
    }

    public List<CuboidParticleSnapshot> snapshots() {
        if (particles.isEmpty()) {
            return Collections.emptyList();
        }

        List<CuboidParticleSnapshot> snapshots = new ArrayList<>(particles.size());
        for (ContainedParticle particle : particles) {
            snapshots.add(particle.snapshot());
        }
        return List.copyOf(snapshots);
    }

    private static final class ContainedParticle {
        private CuboidVector previousPosition;
        private CuboidVector position;
        private CuboidVector velocity;
        private final CuboidParticleSpawn spawn;
        private int ageTicks;

        private ContainedParticle(CuboidParticleSpawn spawn, ContainerVolume volume) {
            this.spawn = spawn;
            this.previousPosition = spawn.position();
            this.position = volume.clamp(spawn.position(), spawn.size() * 0.5);
            this.velocity = spawn.velocity();
        }

        private void tick(ContainerVolume volume) {
            previousPosition = position;
            ageTicks++;
            CuboidVector nextPosition = position.add(velocity);
            CuboidVector clamped = volume.clamp(nextPosition, spawn.size() * 0.5);
            if (!clamped.equals(nextPosition)) {
                velocity = velocity.scale(-0.20);
            } else {
                velocity = velocity.scale(DAMPING);
            }
            position = clamped;
        }

        private void clampTo(ContainerVolume volume) {
            position = volume.clamp(position, spawn.size() * 0.5);
        }

        private boolean isExpired() {
            return ageTicks >= spawn.lifetimeTicks();
        }

        private CuboidParticleSnapshot snapshot() {
            return new CuboidParticleSnapshot(previousPosition, position, velocity, spawn.size(), spawn.size(), spawn.color(), false);
        }
    }
}
