package com.learnchemistry.createsciences.cuboid;

public enum CuboidLiquidColorPreset {
    WATER_BLUE("gui.create_sciences.liquid_colour.water_blue", CuboidParticleColor.waterBlue()),
    ACID_GREEN("gui.create_sciences.liquid_colour.acid_green", new CuboidParticleColor(0.38F, 1.0F, 0.20F, 0.72F)),
    MOLTEN_ORANGE("gui.create_sciences.liquid_colour.molten_orange", new CuboidParticleColor(1.0F, 0.38F, 0.05F, 0.82F)),
    VAPOR_PURPLE("gui.create_sciences.liquid_colour.vapor_purple", new CuboidParticleColor(0.62F, 0.34F, 1.0F, 0.62F)),
    OIL_BLACK("gui.create_sciences.liquid_colour.oil_black", new CuboidParticleColor(0.11F, 0.105F, 0.095F, 0.82F));

    private final String translationKey;
    private final CuboidParticleColor color;

    CuboidLiquidColorPreset(String translationKey, CuboidParticleColor color) {
        this.translationKey = translationKey;
        this.color = color;
    }

    public static CuboidLiquidColorPreset defaultPreset() {
        return WATER_BLUE;
    }

    public static CuboidLiquidColorPreset byIndex(int index) {
        CuboidLiquidColorPreset[] presets = values();
        if (index < 0) {
            return presets[0];
        }
        if (index >= presets.length) {
            return presets[presets.length - 1];
        }
        return presets[index];
    }

    public String translationKey() {
        return translationKey;
    }

    public CuboidParticleColor color() {
        return color;
    }

    public int previewArgb() {
        return color.toOpaqueArgb();
    }
}
