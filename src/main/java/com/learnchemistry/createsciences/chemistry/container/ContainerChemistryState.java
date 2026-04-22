package com.learnchemistry.createsciences.chemistry.container;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public final class ContainerChemistryState {
    private final Map<ResourceLocation, Integer> amounts = new LinkedHashMap<>();

    public void add(ResourceLocation substance, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }
        amounts.merge(substance, amount, Integer::sum);
    }

    public int amountOf(ResourceLocation substance) {
        return amounts.getOrDefault(substance, 0);
    }

    public int removeUpTo(ResourceLocation substance, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }

        int current = amountOf(substance);
        int removed = Math.min(current, amount);
        int remaining = current - removed;
        if (remaining > 0) {
            amounts.put(substance, remaining);
        } else {
            amounts.remove(substance);
        }
        return removed;
    }

    public Map<ResourceLocation, Integer> contents() {
        return Collections.unmodifiableMap(amounts);
    }
}
