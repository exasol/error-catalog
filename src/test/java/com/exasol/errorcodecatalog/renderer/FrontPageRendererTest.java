package com.exasol.errorcodecatalog.renderer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class FrontPageRendererTest {
    private FrontPageRenderer renderer;

    @BeforeEach
    void beforeEach() {
        this.renderer = new FrontPageRenderer();
    }

    @Test
    void testRendering() {
        final UrlBuilder urlBuilder = new UrlBuilder();
        final List<Project> projects = List.of(
                new Project("Project B", Collections.emptyList()),
                new Project("Project C", Collections.emptyList()),
                new Project("Project A", Collections.emptyList())
        );
        final String result = this.renderer.render(projects, 0, urlBuilder);
        assertThat(result, containsString("<h1>Exasol Error Catalog</h1>"
                + "<ul>"
                + "<li><a href=\"projects/Project-A.html\">Project A</a></li>"
                + "<li><a href=\"projects/Project-B.html\">Project B</a></li>"
                + "<li><a href=\"projects/Project-C.html\">Project C</a></li>"
                + "</ul>"));
    }
}
