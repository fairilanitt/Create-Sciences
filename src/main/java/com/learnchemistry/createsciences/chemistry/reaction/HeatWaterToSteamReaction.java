package com.learnchemistry.createsciences.chemistry.reaction;

import com.learnchemistry.createsciences.chemistry.catalog.BuiltInSubstances;
import com.learnchemistry.createsciences.chemistry.catalog.SubstanceCatalog;

public final class HeatWaterToSteamReaction implements ReactionRule {
    public HeatWaterToSteamReaction(SubstanceCatalog catalog) {
        catalog.require(BuiltInSubstances.WATER);
        catalog.require(BuiltInSubstances.STEAM);
    }

    @Override
    public boolean matches(ReactionContext context) {
        return context.heatLevel().isAtLeast(HeatLevel.BURNER)
                && context.container().amountOf(BuiltInSubstances.WATER) > 0;
    }

    @Override
    public ReactionResult apply(ReactionContext context) {
        int amount = context.container().removeUpTo(BuiltInSubstances.WATER, context.container().amountOf(BuiltInSubstances.WATER));
        if (amount == 0) {
            return ReactionResult.EMPTY;
        }

        context.container().add(BuiltInSubstances.STEAM, amount);
        return new ReactionResult(java.util.List.of(new ReactionVisualEvent(
                BuiltInSubstances.WATER,
                BuiltInSubstances.STEAM,
                amount,
                ReactionVisualStyle.BOILING_TO_FRAGMENTED_GAS
        )));
    }
}
