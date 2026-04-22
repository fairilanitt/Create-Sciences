package com.learnchemistry.createsciences.chemistry.catalog;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public final class SubstanceCatalog {
    private final Map<ResourceLocation, SubstanceDefinition> substances;

    public SubstanceCatalog(Collection<SubstanceDefinition> definitions) {
        Map<ResourceLocation, SubstanceDefinition> byId = new LinkedHashMap<>();
        for (SubstanceDefinition definition : definitions) {
            SubstanceDefinition previous = byId.put(definition.id(), definition);
            if (previous != null) {
                throw new IllegalArgumentException("Duplicate substance id: " + definition.id());
            }
        }
        substances = Collections.unmodifiableMap(byId);
    }

    public SubstanceDefinition require(ResourceLocation id) {
        SubstanceDefinition definition = substances.get(id);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown substance: " + id);
        }
        return definition;
    }

    public boolean contains(ResourceLocation id) {
        return substances.containsKey(id);
    }

    public int size() {
        return substances.size();
    }

    public Collection<SubstanceDefinition> all() {
        return substances.values();
    }
}
