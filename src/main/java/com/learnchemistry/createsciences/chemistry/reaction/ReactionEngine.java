package com.learnchemistry.createsciences.chemistry.reaction;

import java.util.ArrayList;
import java.util.List;

public final class ReactionEngine {
    private final List<ReactionRule> rules;

    public ReactionEngine(List<? extends ReactionRule> rules) {
        this.rules = List.copyOf(rules);
    }

    public ReactionResult tick(ReactionContext context) {
        List<ReactionVisualEvent> visualEvents = new ArrayList<>();
        for (ReactionRule rule : rules) {
            if (rule.matches(context)) {
                visualEvents.addAll(rule.apply(context).visualEvents());
            }
        }
        return new ReactionResult(visualEvents);
    }
}
