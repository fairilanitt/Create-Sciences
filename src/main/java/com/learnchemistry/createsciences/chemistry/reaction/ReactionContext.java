package com.learnchemistry.createsciences.chemistry.reaction;

import com.learnchemistry.createsciences.chemistry.container.ContainerChemistryState;
import java.util.Objects;

public record ReactionContext(ContainerChemistryState container, HeatLevel heatLevel) {
    public ReactionContext {
        Objects.requireNonNull(container);
        Objects.requireNonNull(heatLevel);
    }
}
