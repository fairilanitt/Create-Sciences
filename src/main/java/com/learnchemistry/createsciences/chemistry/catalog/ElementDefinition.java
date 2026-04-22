package com.learnchemistry.createsciences.chemistry.catalog;

import java.util.Objects;

public record ElementDefinition(int atomicNumber, String symbol, String displayName) {
    public ElementDefinition {
        if (atomicNumber <= 0) {
            throw new IllegalArgumentException("atomicNumber must be positive.");
        }
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(displayName);
    }
}
