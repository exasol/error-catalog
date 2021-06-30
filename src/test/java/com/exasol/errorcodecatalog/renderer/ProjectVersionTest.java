package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProjectVersionTest {

    @ParameterizedTest
    @CsvSource(value = { //
            "1.0.0, 2.0.0", //
            "1.0.0, 1.1.0", //
            "1.0.0, 1.0.1", //
            "2.0.0, 10.0.0", //
            "1.0, 1.0.3", //
            "a, b",//
    })
    void testCompare(final String smallerVersion, final String greaterVersion) {
        assertThat(new ProjectVersion(smallerVersion), lessThan(new ProjectVersion(greaterVersion)));
    }
}