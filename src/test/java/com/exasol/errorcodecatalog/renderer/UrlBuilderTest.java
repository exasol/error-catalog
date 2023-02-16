package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;
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
        final Path path = new UrlBuilder().getPathFor(new Project(projectName, Collections.emptyList()));
        final String string = new UrlBuilder().getUrlFor(new Project(projectName, Collections.emptyList()));
        assertThat(path.toString(), equalTo(Path.of(expectedUrl).toString()));
        assertThat(string, equalTo(expectedUrl));
    }

    @Test
    void testBuildErrorCodeUrl() {
        final Path url = new UrlBuilder().getUrlFor(new ErrorCodeVersions(
                Map.of(new ProjectVersion("1.0.0"), ErrorMessageDeclaration.builder().identifier("E-TEST-1").build())));
        assertThat(url.toString(), equalTo(Path.of("error-codes/e-test-1.html").toString()));
    }

    @CsvSource({ //
            "prj1, 2.4.6, /foo/bar.zoo, 42, https://github.com/exasol/prj1/blob/2.4.6/foo/bar.zoo#L42",
            "prj2, 1.2.3, one/two.three, 33, https://github.com/exasol/prj2/blob/1.2.3/one/two.three#L33" })
    @ParameterizedTest
    void testBuildSourceCodeUrl(final String projectName, final String version, final String path, final int line,
            final String expectedUrl) {
        final ErrorMessageDeclaration declaration = ErrorMessageDeclaration.builder().identifier("E-NEVERMIND-1")
                .setPosition(path, line).build();
        final Project project = new Project(projectName, Collections.emptyList());
        final URI uri = new UrlBuilder().getSourceUriFor(project, version, declaration);
        assertThat(uri.toString(), equalTo(expectedUrl));
    }
}