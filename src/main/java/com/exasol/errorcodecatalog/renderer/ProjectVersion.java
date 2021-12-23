package com.exasol.errorcodecatalog.renderer;

import java.util.Arrays;

/**
 * This class represents a version number.
 */
public class ProjectVersion implements Comparable<ProjectVersion> {
    private final String[] parts;

    /**
     * Create a new instance of {@link ProjectVersion}.
     *
     * @param semanticVersion version number
     */
    public ProjectVersion(final String semanticVersion) {
        this.parts = semanticVersion.split("\\.");
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        final ProjectVersion that = (ProjectVersion) other;
        return Arrays.equals(this.parts, that.parts);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.parts);
    }

    @Override
    public int compareTo(final ProjectVersion other) {
        final int minPartsLength = Math.min(this.parts.length, other.parts.length);
        for (int partIndex = 0; partIndex < minPartsLength; partIndex++) {
            final int compareResult = compareParts(other.parts[partIndex], partIndex);
            if (compareResult != 0) {
                return compareResult;
            }
        }
        return Integer.compare(this.parts.length, other.parts.length);
    }

    private int compareParts(final String otherPart, final int partIndex) {
        try {
            return Integer.compare(Integer.parseInt(this.parts[partIndex]), Integer.parseInt(otherPart));
        } catch (final NumberFormatException exception) {
            return this.parts[partIndex].compareTo(otherPart);
        }
    }

    @Override
    public String toString() {
        return String.join(".", this.parts);
    }
}
