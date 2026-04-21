package com.learnchemistry.createsciences.cuboid;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public final class CuboidParticleToolHooks {
    private static TestingLiquidSpawner testingLiquidSpawner = (location, face) -> {
    };
    private static Runnable settingsScreenOpener = () -> {
    };

    private CuboidParticleToolHooks() {
    }

    public static void setTestingLiquidSpawner(TestingLiquidSpawner spawner) {
        testingLiquidSpawner = spawner == null ? (location, face) -> {
        } : spawner;
    }

    public static void spawnTestingLiquid(Vec3 location, Direction face) {
        testingLiquidSpawner.spawn(location, face);
    }

    public static void setSettingsScreenOpener(Runnable opener) {
        settingsScreenOpener = opener == null ? () -> {
        } : opener;
    }

    public static void openSettingsScreen() {
        settingsScreenOpener.run();
    }

    @FunctionalInterface
    public interface TestingLiquidSpawner {
        void spawn(Vec3 location, Direction face);
    }
}
