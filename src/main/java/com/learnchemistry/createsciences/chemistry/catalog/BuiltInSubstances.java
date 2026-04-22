package com.learnchemistry.createsciences.chemistry.catalog;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.cuboid.CuboidParticleColor;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class BuiltInSubstances {
    public static final ResourceLocation WATER = id("water");
    public static final ResourceLocation STEAM = id("steam");
    public static final ResourceLocation HYDROGEN = id("hydrogen");
    public static final ResourceLocation OXYGEN = id("oxygen");
    public static final ResourceLocation NITROGEN = id("nitrogen");
    public static final ResourceLocation CARBON_DIOXIDE = id("carbon_dioxide");
    public static final ResourceLocation METHANE = id("methane");
    public static final ResourceLocation ETHANOL = id("ethanol");
    public static final ResourceLocation SODIUM_CHLORIDE = id("sodium_chloride");
    public static final ResourceLocation HYDROCHLORIC_ACID = id("hydrochloric_acid");
    public static final ResourceLocation SODIUM_HYDROXIDE = id("sodium_hydroxide");
    public static final ResourceLocation SULFURIC_ACID = id("sulfuric_acid");
    public static final ResourceLocation AMMONIA = id("ammonia");
    public static final ResourceLocation GLUCOSE = id("glucose");

    private BuiltInSubstances() {
    }

    public static SubstanceCatalog createCatalog() {
        return new SubstanceCatalog(List.of(
                substance(WATER, "Water", "H2O", SubstanceKind.MOLECULE, SubstancePhase.LIQUID,
                        liquid(0.18F, 0.55F, 1.0F, 0.72F), "solvent", "nonflammable"),
                substance(STEAM, "Steam", "H2O", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        fragmentedGas(0.88F, 0.93F, 0.95F, 0.84F), "vapor", "hot", "nonflammable"),
                substance(HYDROGEN, "Hydrogen", "H2", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.82F, 0.92F, 1.0F, 0.55F), "fuel", "flammable"),
                substance(OXYGEN, "Oxygen", "O2", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.55F, 0.74F, 1.0F, 0.55F), "oxidizer"),
                substance(NITROGEN, "Nitrogen", "N2", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.62F, 0.68F, 0.82F, 0.48F), "inert"),
                substance(CARBON_DIOXIDE, "Carbon Dioxide", "CO2", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.75F, 0.82F, 0.84F, 0.58F), "product", "nonflammable"),
                substance(METHANE, "Methane", "CH4", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.72F, 0.96F, 0.90F, 0.55F), "fuel", "flammable"),
                substance(ETHANOL, "Ethanol", "C2H6O", SubstanceKind.MOLECULE, SubstancePhase.LIQUID,
                        liquid(0.92F, 0.86F, 0.55F, 0.68F), "fuel", "flammable", "volatile"),
                substance(SODIUM_CHLORIDE, "Sodium Chloride", "NaCl", SubstanceKind.COMPOUND, SubstancePhase.SOLID,
                        solid(0.88F, 0.88F, 0.82F, 1.0F), "salt"),
                substance(HYDROCHLORIC_ACID, "Hydrochloric Acid", "HCl", SubstanceKind.SOLUTION, SubstancePhase.AQUEOUS,
                        liquid(0.72F, 0.92F, 0.42F, 0.72F), "acid", "corrosive"),
                substance(SODIUM_HYDROXIDE, "Sodium Hydroxide", "NaOH", SubstanceKind.COMPOUND, SubstancePhase.AQUEOUS,
                        liquid(0.72F, 0.78F, 1.0F, 0.72F), "base", "corrosive"),
                substance(SULFURIC_ACID, "Sulfuric Acid", "H2SO4", SubstanceKind.COMPOUND, SubstancePhase.LIQUID,
                        liquid(0.95F, 0.94F, 0.70F, 0.74F), "acid", "corrosive"),
                substance(AMMONIA, "Ammonia", "NH3", SubstanceKind.MOLECULE, SubstancePhase.GAS,
                        gas(0.78F, 0.92F, 0.82F, 0.55F), "base", "pungent"),
                substance(GLUCOSE, "Glucose", "C6H12O6", SubstanceKind.MOLECULE, SubstancePhase.SOLID,
                        solid(0.95F, 0.84F, 0.60F, 1.0F), "organic", "energy_source")
        ));
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(CreateSciences.MOD_ID, path);
    }

    private static SubstanceDefinition substance(
            ResourceLocation id,
            String displayName,
            String formula,
            SubstanceKind kind,
            SubstancePhase phase,
            VisualProfile visualProfile,
            String... tags
    ) {
        return new SubstanceDefinition(id, displayName, formula, kind, phase, visualProfile, Set.of(tags));
    }

    private static VisualProfile liquid(float red, float green, float blue, float alpha) {
        return new VisualProfile(new CuboidParticleColor(red, green, blue, alpha), 0.125, CuboidSpawnStyle.LIQUID, false);
    }

    private static VisualProfile gas(float red, float green, float blue, float alpha) {
        return new VisualProfile(new CuboidParticleColor(red, green, blue, alpha), 0.095, CuboidSpawnStyle.FRAGMENTED_GAS, true);
    }

    private static VisualProfile fragmentedGas(float red, float green, float blue, float alpha) {
        return new VisualProfile(new CuboidParticleColor(red, green, blue, alpha), 0.070, CuboidSpawnStyle.FRAGMENTED_GAS, true);
    }

    private static VisualProfile solid(float red, float green, float blue, float alpha) {
        return new VisualProfile(new CuboidParticleColor(red, green, blue, alpha), 0.115, CuboidSpawnStyle.SOLID_GRAIN, false);
    }
}
