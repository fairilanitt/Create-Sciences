package com.learnchemistry.createsciences.chemistry.reaction;

import java.util.List;

public record ReactionResult(List<ReactionVisualEvent> visualEvents) {
    public static final ReactionResult EMPTY = new ReactionResult(List.of());

    public ReactionResult {
        visualEvents = List.copyOf(visualEvents);
    }
}
