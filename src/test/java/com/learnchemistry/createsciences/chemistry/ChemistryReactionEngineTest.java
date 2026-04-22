package com.learnchemistry.createsciences.chemistry;

import com.learnchemistry.createsciences.block.GasBurnerBlock;
import com.learnchemistry.createsciences.chemistry.catalog.BuiltInSubstances;
import com.learnchemistry.createsciences.chemistry.catalog.CuboidSpawnStyle;
import com.learnchemistry.createsciences.chemistry.catalog.ElementCatalog;
import com.learnchemistry.createsciences.chemistry.catalog.PeriodicElements;
import com.learnchemistry.createsciences.chemistry.catalog.SubstanceCatalog;
import com.learnchemistry.createsciences.chemistry.catalog.SubstanceDefinition;
import com.learnchemistry.createsciences.chemistry.catalog.SubstancePhase;
import com.learnchemistry.createsciences.chemistry.container.ContainerChemistryState;
import com.learnchemistry.createsciences.chemistry.reaction.HeatLevel;
import com.learnchemistry.createsciences.chemistry.reaction.HeatWaterToSteamReaction;
import com.learnchemistry.createsciences.chemistry.reaction.ReactionContext;
import com.learnchemistry.createsciences.chemistry.reaction.ReactionEngine;
import com.learnchemistry.createsciences.chemistry.reaction.ReactionResult;
import com.learnchemistry.createsciences.chemistry.reaction.ReactionVisualStyle;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChemistryReactionEngineTest {
    @Test
    void builtInSubstancesIncludeWaterSteamAndCommonChemistrySeeds() {
        SubstanceCatalog catalog = BuiltInSubstances.createCatalog();

        SubstanceDefinition water = catalog.require(BuiltInSubstances.WATER);
        SubstanceDefinition steam = catalog.require(BuiltInSubstances.STEAM);

        assertEquals("H2O", water.formula());
        assertEquals(SubstancePhase.LIQUID, water.phase());
        assertEquals(CuboidSpawnStyle.LIQUID, water.visualProfile().spawnStyle());
        assertEquals("H2O", steam.formula());
        assertEquals(SubstancePhase.GAS, steam.phase());
        assertEquals(CuboidSpawnStyle.FRAGMENTED_GAS, steam.visualProfile().spawnStyle());
        assertTrue(steam.visualProfile().fragmented());
        assertTrue(steam.visualProfile().color().alpha() >= 0.75F);
        assertTrue(catalog.size() >= 14);
    }

    @Test
    void elementDirectoryContainsPeriodicTableEntries() {
        ElementCatalog elements = PeriodicElements.createCatalog();

        assertEquals(118, elements.size());
        assertEquals("Hydrogen", elements.requireBySymbol("H").displayName());
        assertEquals("O", elements.requireByAtomicNumber(8).symbol());
        assertEquals("Oganesson", elements.requireByAtomicNumber(118).displayName());
    }

    @Test
    void burnerHeatTurnsContainedWaterIntoFragmentedSteam() {
        SubstanceCatalog catalog = BuiltInSubstances.createCatalog();
        ContainerChemistryState container = new ContainerChemistryState();
        container.add(BuiltInSubstances.WATER, 12);
        ReactionEngine engine = new ReactionEngine(List.of(new HeatWaterToSteamReaction(catalog)));

        ReactionResult result = engine.tick(new ReactionContext(container, HeatLevel.BURNER));

        assertEquals(0, container.amountOf(BuiltInSubstances.WATER));
        assertEquals(12, container.amountOf(BuiltInSubstances.STEAM));
        assertEquals(1, result.visualEvents().size());
        assertEquals(BuiltInSubstances.WATER, result.visualEvents().getFirst().fromSubstance());
        assertEquals(BuiltInSubstances.STEAM, result.visualEvents().getFirst().toSubstance());
        assertEquals(ReactionVisualStyle.BOILING_TO_FRAGMENTED_GAS, result.visualEvents().getFirst().style());
    }

    @Test
    void waterDoesNotReactWithoutHeat() {
        SubstanceCatalog catalog = BuiltInSubstances.createCatalog();
        ContainerChemistryState container = new ContainerChemistryState();
        container.add(BuiltInSubstances.WATER, 12);
        ReactionEngine engine = new ReactionEngine(List.of(new HeatWaterToSteamReaction(catalog)));

        ReactionResult result = engine.tick(new ReactionContext(container, HeatLevel.NONE));

        assertEquals(12, container.amountOf(BuiltInSubstances.WATER));
        assertEquals(0, container.amountOf(BuiltInSubstances.STEAM));
        assertTrue(result.visualEvents().isEmpty());
    }

    @Test
    void gasBurnerBlockProvidesBurnerHeat() {
        assertTrue(GasBurnerBlock.generatesHeatAtRest());
        assertEquals(HeatLevel.BURNER, GasBurnerBlock.burnerHeatLevel());
    }
}
