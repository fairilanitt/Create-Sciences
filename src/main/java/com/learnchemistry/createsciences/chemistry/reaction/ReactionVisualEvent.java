package com.learnchemistry.createsciences.chemistry.reaction;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record ReactionVisualEvent(
        ResourceLocation fromSubstance,
        ResourceLocation toSubstance,
        int amount,
        ReactionVisualStyle style
) {
    public ReactionVisualEvent {
        Objects.requireNonNull(fromSubstance);
        Objects.requireNonNull(toSubstance);
        Objects.requireNonNull(style);
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }
    }
}
