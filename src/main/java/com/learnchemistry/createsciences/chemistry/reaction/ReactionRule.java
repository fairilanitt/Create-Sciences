package com.learnchemistry.createsciences.chemistry.reaction;

public interface ReactionRule {
    boolean matches(ReactionContext context);

    ReactionResult apply(ReactionContext context);
}
