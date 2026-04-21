package com.learnchemistry.createsciences.cuboid;

public record CuboidParticleColor(float red, float green, float blue, float alpha) {
    public static CuboidParticleColor waterBlue() {
        return new CuboidParticleColor(0.18F, 0.55F, 1.0F, 0.72F);
    }

    public int toOpaqueArgb() {
        return 0xFF000000
                | (toByte(red) << 16)
                | (toByte(green) << 8)
                | toByte(blue);
    }

    private static int toByte(float channel) {
        float clamped = Math.max(0.0F, Math.min(1.0F, channel));
        return Math.round(clamped * 255.0F);
    }
}
