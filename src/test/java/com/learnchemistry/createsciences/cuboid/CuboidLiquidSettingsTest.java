package com.learnchemistry.createsciences.cuboid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CuboidLiquidSettingsTest {
    @Test
    void defaultsToWaterBlue() {
        CuboidLiquidSettings settings = new CuboidLiquidSettings();

        assertEquals(CuboidLiquidColorPreset.WATER_BLUE, settings.selectedPreset());
        assertEquals(CuboidLiquidColorPreset.WATER_BLUE.color(), settings.selectedColor());
    }

    @Test
    void selectsPresetByIndex() {
        CuboidLiquidSettings settings = new CuboidLiquidSettings();

        settings.selectPreset(2);

        assertEquals(CuboidLiquidColorPreset.MOLTEN_ORANGE, settings.selectedPreset());
        assertEquals(CuboidLiquidColorPreset.MOLTEN_ORANGE.color(), settings.selectedColor());
    }

    @Test
    void clampsPresetIndexes() {
        assertEquals(CuboidLiquidColorPreset.WATER_BLUE, CuboidLiquidColorPreset.byIndex(-4));
        assertEquals(CuboidLiquidColorPreset.OIL_BLACK, CuboidLiquidColorPreset.byIndex(99));
    }

    @Test
    void exposesOpaquePreviewColour() {
        assertEquals(0xFF2E8CFF, CuboidLiquidColorPreset.WATER_BLUE.previewArgb());
        assertEquals(0xFF1C1B18, CuboidLiquidColorPreset.OIL_BLACK.previewArgb());
    }
}
