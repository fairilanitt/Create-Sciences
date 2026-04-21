package com.learnchemistry.createsciences.cuboid;

public final class CuboidLiquidSettings {
    private CuboidLiquidColorPreset selectedPreset = CuboidLiquidColorPreset.defaultPreset();

    public CuboidLiquidColorPreset selectedPreset() {
        return selectedPreset;
    }

    public CuboidParticleColor selectedColor() {
        return selectedPreset.color();
    }

    public void selectPreset(int index) {
        selectedPreset = CuboidLiquidColorPreset.byIndex(index);
    }
}
