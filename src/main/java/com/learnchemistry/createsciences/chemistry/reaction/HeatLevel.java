package com.learnchemistry.createsciences.chemistry.reaction;

public enum HeatLevel {
    NONE(0),
    WARM(1),
    BURNER(2);

    private final int strength;

    HeatLevel(int strength) {
        this.strength = strength;
    }

    public boolean isAtLeast(HeatLevel other) {
        return strength >= other.strength;
    }
}
