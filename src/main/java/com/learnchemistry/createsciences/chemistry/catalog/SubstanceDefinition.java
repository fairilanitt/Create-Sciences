package com.learnchemistry.createsciences.chemistry.catalog;

import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public record SubstanceDefinition(
        ResourceLocation id,
        String displayName,
        String formula,
        SubstanceKind kind,
        SubstancePhase phase,
        VisualProfile visualProfile,
        Set<String> tags
) {
    public SubstanceDefinition {
        Objects.requireNonNull(id);
        Objects.requireNonNull(displayName);
        Objects.requireNonNull(formula);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(phase);
        Objects.requireNonNull(visualProfile);
        tags = Set.copyOf(tags);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }
}
