package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class PlaceholderNumberProviderTest {
    private static final PlaceholderNumberProvider PROVIDER = new PlaceholderNumberProvider();

    @Test
    void testEqualPlaceholders() {
        assertThat(PROVIDER.getNumberFor("test"), equalTo(PROVIDER.getNumberFor("test")));
    }

    @Test
    void testNonEqualPlaceholders() {
        assertThat(PROVIDER.getNumberFor("test"), not(equalTo(PROVIDER.getNumberFor("other"))));
    }
}