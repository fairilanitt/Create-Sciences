package com.learnchemistry.createsciences.chemistry.catalog;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ElementCatalog {
    private final Map<Integer, ElementDefinition> byAtomicNumber;
    private final Map<String, ElementDefinition> bySymbol;

    public ElementCatalog(Collection<ElementDefinition> definitions) {
        Map<Integer, ElementDefinition> atomicNumbers = new LinkedHashMap<>();
        Map<String, ElementDefinition> symbols = new LinkedHashMap<>();
        for (ElementDefinition definition : definitions) {
            if (atomicNumbers.put(definition.atomicNumber(), definition) != null) {
                throw new IllegalArgumentException("Duplicate atomic number: " + definition.atomicNumber());
            }
            if (symbols.put(definition.symbol(), definition) != null) {
                throw new IllegalArgumentException("Duplicate element symbol: " + definition.symbol());
            }
        }
        byAtomicNumber = Collections.unmodifiableMap(atomicNumbers);
        bySymbol = Collections.unmodifiableMap(symbols);
    }

    public ElementDefinition requireByAtomicNumber(int atomicNumber) {
        ElementDefinition definition = byAtomicNumber.get(atomicNumber);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown atomic number: " + atomicNumber);
        }
        return definition;
    }

    public ElementDefinition requireBySymbol(String symbol) {
        ElementDefinition definition = bySymbol.get(symbol);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown element symbol: " + symbol);
        }
        return definition;
    }

    public int size() {
        return byAtomicNumber.size();
    }
}
