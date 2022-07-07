package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;

class UrlBuilderTest {

    @ParameterizedTest
    @CsvSource(value = { //
            "test project, projects/test-project.html", //
            "test/project, projects/test-project.html",//
    })
    void testBuildProjectUrl(final String projectName, final String expectedUrl) {
        final Path url = new UrlBuilder().getUrlFor(new Project(projectName, Collections.emptyList()));
        assertThat(url.toString(), equalTo(Path.of(expectedUrl).toString()));
    }

    @Test
    void testBuildErrorCodeUrl() {
        final Path url = new UrlBuilder().getUrlFor(new ErrorCodeVersions(
                Map.of(new ProjectVersion("1.0.0"), ErrorMessageDeclaration.builder().identifier("E-TEST-1").build())));
        assertThat(url.toString(), equalTo(Path.of("error-codes/e-test-1.html").toString()));
    }
}