package com.exasol.errorcodecatalog.renderer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class assigns each placeholder a number. We use it to color the error codes.
 */
public class PlaceholderNumberProvider {
    private final Map<String, Integer> placeholderNumbers = new HashMap<>();

    /**
     * Get a number for a placeholder.
     * 
     * @param placeholder placeholder
     * @return number
     */
    public int getNumberFor(final String placeholder) {
        this.placeholderNumbers.putIfAbsent(placeholder, this.placeholderNumbers.size());
        return this.placeholderNumbers.get(placeholder);
    }
}
