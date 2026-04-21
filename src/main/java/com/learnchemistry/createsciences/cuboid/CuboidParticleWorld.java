package com.learnchemistry.createsciences.cuboid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuboidParticleWorld {
    private static final double MIN_DISTANCE = 1.0E-5;
    private static final double OVERLAP_EPSILON = 1.0E-6;
    private static final double SEPARATION_DAMPING = 0.35;
    private static final double EDGE_VISUAL_SIZE_FACTOR = 0.74;
    private static final double INTERIOR_VISUAL_SIZE_FACTOR = 0.98;
    private static final double MIN_VISUAL_SIZE_FACTOR = 0.58;
    private static final double MAX_VISUAL_SIZE_FACTOR = 1.0;
    private static final double MOTION_VISUAL_SHRINK = 0.12;
    private static final double AIRBORNE_VISUAL_SHRINK = 0.08;
    private static final double FALLING_VISUAL_SHRINK = 0.18;
    private static final double VISUAL_SIZE_RESPONSE = 0.45;
    private static final double NEIGHBOR_RADIUS_FACTOR = 1.36;
    private static final double NEIGHBOR_VERTICAL_FACTOR = 0.80;
    private static final int FULL_VISUAL_NEIGHBOR_COUNT = 4;
    private static final int OVERLAP_SOLVER_PASSES = 8;

    private final int maxParticles;
    private final CuboidParticleMaterial material;
    private final List<ParticleState> particles = new ArrayList<>();

    public CuboidParticleWorld(int maxParticles, CuboidParticleMaterial material) {
        if (maxParticles <= 0) {
            throw new IllegalArgumentException("maxParticles must be positive.");
        }

        this.maxParticles = maxParticles;
        this.material = material;
    }

    public void spawn(CuboidParticleSpawn spawn) {
        while (particles.size() >= maxParticles) {
            particles.removeFirst();
        }

        particles.add(new ParticleState(spawn));
    }

    public void tick(CuboidWorldCollider collider) {
        applyLiquidSpread();

        for (ParticleState particle : particles) {
            particle.tick(material, collider);
        }

        resolveCuboidOverlaps(collider);
        updateVisualSizes();
        particles.removeIf(ParticleState::isExpired);
    }

    public void clear() {
        particles.clear();
    }

    public List<CuboidParticleSnapshot> snapshots() {
        if (particles.isEmpty()) {
            return Collections.emptyList();
        }

        List<CuboidParticleSnapshot> snapshots = new ArrayList<>(particles.size());
        for (ParticleState particle : particles) {
            snapshots.add(particle.snapshot());
        }
        return List.copyOf(snapshots);
    }

    private void applyLiquidSpread() {
        double radius = material.interactionRadius();
        double radiusSqr = radius * radius;

        for (int i = 0; i < particles.size(); i++) {
            ParticleState first = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                ParticleState second = particles.get(j);
                double dx = second.position.x() - first.position.x();
                double dz = second.position.z() - first.position.z();
                double distanceSqr = dx * dx + dz * dz;

                if (distanceSqr > radiusSqr) {
                    continue;
                }

                double distance = Math.sqrt(Math.max(distanceSqr, MIN_DISTANCE));
                double normalX = distanceSqr < MIN_DISTANCE ? alternateDirection(i, j) : dx / distance;
                double normalZ = distanceSqr < MIN_DISTANCE ? alternateDirection(j, i) : dz / distance;
                double surfaceBias = first.onSurface || second.onSurface ? 1.0 : 0.35;
                double push = (radius - distance) * material.spreadStrength() * surfaceBias;

                first.velocity = first.velocity.add(-normalX * push, 0.0, -normalZ * push);
                second.velocity = second.velocity.add(normalX * push, 0.0, normalZ * push);
            }
        }
    }

    private static double alternateDirection(int first, int second) {
        return ((first + second) & 1) == 0 ? 0.70710678118 : -0.70710678118;
    }

    private void updateVisualSizes() {
        for (int i = 0; i < particles.size(); i++) {
            ParticleState particle = particles.get(i);
            particle.updateVisualSize(material, countCloseNeighbors(i));
        }
    }

    private int countCloseNeighbors(int particleIndex) {
        ParticleState particle = particles.get(particleIndex);
        int closeNeighbors = 0;

        for (int i = 0; i < particles.size(); i++) {
            if (i == particleIndex) {
                continue;
            }

            ParticleState other = particles.get(i);
            double neighborRadius = ((particle.size + other.size) * 0.5) * NEIGHBOR_RADIUS_FACTOR;
            double verticalLimit = ((particle.size + other.size) * 0.5) * NEIGHBOR_VERTICAL_FACTOR;
            double dx = other.position.x() - particle.position.x();
            double dz = other.position.z() - particle.position.z();
            double dy = Math.abs(other.position.y() - particle.position.y());

            if (dy <= verticalLimit && dx * dx + dz * dz <= neighborRadius * neighborRadius) {
                closeNeighbors++;
            }
        }

        return closeNeighbors;
    }

    private void resolveCuboidOverlaps(CuboidWorldCollider collider) {
        if (particles.size() < 2) {
            return;
        }

        for (int pass = 0; pass < OVERLAP_SOLVER_PASSES; pass++) {
            boolean separatedAny = false;
            for (int i = 0; i < particles.size(); i++) {
                ParticleState first = particles.get(i);
                for (int j = i + 1; j < particles.size(); j++) {
                    separatedAny |= separateIfOverlapping(first, particles.get(j), i, j);
                }
            }

            if (!separatedAny) {
                return;
            }

            for (ParticleState particle : particles) {
                particle.clampToFloor(collider);
            }
        }
    }

    private static boolean separateIfOverlapping(ParticleState first, ParticleState second, int firstIndex, int secondIndex) {
        double minimumX = (first.size + second.size) * 0.5;
        double minimumY = minimumX;
        double minimumZ = minimumX;

        double dx = second.position.x() - first.position.x();
        double dy = second.position.y() - first.position.y();
        double dz = second.position.z() - first.position.z();

        double overlapX = minimumX - Math.abs(dx);
        double overlapY = minimumY - Math.abs(dy);
        double overlapZ = minimumZ - Math.abs(dz);

        if (overlapX <= OVERLAP_EPSILON || overlapY <= OVERLAP_EPSILON || overlapZ <= OVERLAP_EPSILON) {
            return false;
        }

        SeparationAxis axis = chooseSeparationAxis(overlapX, overlapY, overlapZ, first.onSurface || second.onSurface, firstIndex, secondIndex);
        double overlap = switch (axis) {
            case X -> overlapX;
            case Y -> overlapY;
            case Z -> overlapZ;
        };
        double sign = signForAxis(axis, dx, dy, dz, firstIndex, secondIndex);
        double correction = (overlap + OVERLAP_EPSILON) * 0.5;

        first.moveAlong(axis, -sign * correction);
        second.moveAlong(axis, sign * correction);
        first.dampenVelocity(axis);
        second.dampenVelocity(axis);
        return true;
    }

    private static SeparationAxis chooseSeparationAxis(
            double overlapX,
            double overlapY,
            double overlapZ,
            boolean surfaceBias,
            int firstIndex,
            int secondIndex
    ) {
        double horizontalOverlap = Math.min(overlapX, overlapZ);
        if (surfaceBias && horizontalOverlap <= overlapY + OVERLAP_EPSILON) {
            return chooseHorizontalAxis(overlapX, overlapZ, firstIndex, secondIndex);
        }

        if (overlapY < overlapX && overlapY < overlapZ) {
            return SeparationAxis.Y;
        }

        if (overlapX < overlapZ) {
            return SeparationAxis.X;
        }
        if (overlapZ < overlapX) {
            return SeparationAxis.Z;
        }
        return chooseHorizontalAxis(overlapX, overlapZ, firstIndex, secondIndex);
    }

    private static SeparationAxis chooseHorizontalAxis(double overlapX, double overlapZ, int firstIndex, int secondIndex) {
        if (overlapX < overlapZ) {
            return SeparationAxis.X;
        }
        if (overlapZ < overlapX) {
            return SeparationAxis.Z;
        }
        return ((firstIndex + secondIndex) & 1) == 0 ? SeparationAxis.X : SeparationAxis.Z;
    }

    private static double signForAxis(SeparationAxis axis, double dx, double dy, double dz, int firstIndex, int secondIndex) {
        double delta = switch (axis) {
            case X -> dx;
            case Y -> dy;
            case Z -> dz;
        };
        if (Math.abs(delta) > MIN_DISTANCE) {
            return Math.signum(delta);
        }
        return ((firstIndex * 31 + secondIndex * 17 + axis.ordinal()) & 1) == 0 ? 1.0 : -1.0;
    }

    private enum SeparationAxis {
        X,
        Y,
        Z
    }

    private static final class ParticleState {
        private CuboidVector previousPosition;
        private CuboidVector position;
        private CuboidVector velocity;
        private final double size;
        private double visualSize;
        private final CuboidParticleColor color;
        private final int lifetimeTicks;
        private int ageTicks;
        private boolean onSurface;

        private ParticleState(CuboidParticleSpawn spawn) {
            this.previousPosition = spawn.position();
            this.position = spawn.position();
            this.velocity = spawn.velocity();
            this.size = spawn.size();
            this.visualSize = spawn.size();
            this.color = spawn.color();
            this.lifetimeTicks = spawn.lifetimeTicks();
        }

        private void tick(CuboidParticleMaterial material, CuboidWorldCollider collider) {
            previousPosition = position;
            ageTicks++;

            velocity = velocity.add(0.0, -material.gravity(), 0.0);
            velocity = velocity.scale(material.airDamping());
            velocity = clampHorizontalSpeed(velocity, material.maxHorizontalSpeed());

            CuboidVector nextPosition = position.add(velocity);
            double halfSize = size * 0.5;
            double floorY = collider.floorYAt(nextPosition, halfSize);

            if (nextPosition.y() - halfSize <= floorY) {
                nextPosition = nextPosition.withY(floorY + halfSize);
                velocity = velocity.withY(0.0);
                velocity = velocity.withHorizontal(velocity.x() * material.surfaceDamping(), velocity.z() * material.surfaceDamping());
                onSurface = true;
            } else {
                onSurface = false;
            }

            position = nextPosition;
        }

        private void clampToFloor(CuboidWorldCollider collider) {
            double halfSize = size * 0.5;
            double floorY = collider.floorYAt(position, halfSize);
            if (position.y() - halfSize <= floorY) {
                position = position.withY(floorY + halfSize);
                if (velocity.y() < 0.0) {
                    velocity = velocity.withY(0.0);
                }
                onSurface = true;
            }
        }

        private void moveAlong(SeparationAxis axis, double distance) {
            position = switch (axis) {
                case X -> position.add(distance, 0.0, 0.0);
                case Y -> position.add(0.0, distance, 0.0);
                case Z -> position.add(0.0, 0.0, distance);
            };
        }

        private void dampenVelocity(SeparationAxis axis) {
            velocity = switch (axis) {
                case X -> new CuboidVector(velocity.x() * SEPARATION_DAMPING, velocity.y(), velocity.z());
                case Y -> velocity.withY(0.0);
                case Z -> new CuboidVector(velocity.x(), velocity.y(), velocity.z() * SEPARATION_DAMPING);
            };
        }

        private void updateVisualSize(CuboidParticleMaterial material, int closeNeighbors) {
            double neighborFill = Math.min(1.0, closeNeighbors / (double) FULL_VISUAL_NEIGHBOR_COUNT);
            double targetFactor = EDGE_VISUAL_SIZE_FACTOR
                    + (INTERIOR_VISUAL_SIZE_FACTOR - EDGE_VISUAL_SIZE_FACTOR) * neighborFill;

            double maxHorizontalSpeed = material.maxHorizontalSpeed();
            if (maxHorizontalSpeed > 0.0) {
                double speedRatio = Math.min(1.0, Math.sqrt(velocity.horizontalLengthSqr()) / maxHorizontalSpeed);
                targetFactor -= speedRatio * MOTION_VISUAL_SHRINK;
            }

            if (!onSurface) {
                targetFactor -= AIRBORNE_VISUAL_SHRINK;
                if (velocity.y() < -MIN_DISTANCE) {
                    targetFactor -= FALLING_VISUAL_SHRINK;
                }
            }

            targetFactor = Math.max(MIN_VISUAL_SIZE_FACTOR, Math.min(MAX_VISUAL_SIZE_FACTOR, targetFactor));
            double targetSize = size * targetFactor;
            visualSize += (targetSize - visualSize) * VISUAL_SIZE_RESPONSE;
        }

        private CuboidVector clampHorizontalSpeed(CuboidVector input, double maxSpeed) {
            double speedSqr = input.horizontalLengthSqr();
            double maxSqr = maxSpeed * maxSpeed;
            if (speedSqr <= maxSqr) {
                return input;
            }

            double scale = maxSpeed / Math.sqrt(speedSqr);
            return new CuboidVector(input.x() * scale, input.y(), input.z() * scale);
        }

        private boolean isExpired() {
            return ageTicks >= lifetimeTicks;
        }

        private CuboidParticleSnapshot snapshot() {
            return new CuboidParticleSnapshot(previousPosition, position, velocity, size, visualSize, color, onSurface);
        }
    }
}
