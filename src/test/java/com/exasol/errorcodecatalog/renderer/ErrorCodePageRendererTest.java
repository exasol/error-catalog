package com.exasol.errorcodecatalog.renderer;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static j2html.TagCreator.h1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class ErrorCodePageRendererTest {
    private ErrorCodePageRenderer renderer;

    @BeforeEach
    void beforeEach() {
        renderer = new ErrorCodePageRenderer();
    }

    @Test
    void testWithSourceLink() {
        final Project project = new Project("the-project", Collections.emptyList());
        final ErrorMessageDeclaration declaration = ErrorMessageDeclaration.builder()
                .identifier("E-WSL-1").prependMessage("the message")
                .setPosition("/src/main/java/com/exasol/example/Example.java", 26).build();
        final ProjectVersion projectVersion = new ProjectVersion("1.3.5");
        final ErrorCodeVersions codeVersions = new ErrorCodeVersions(Map.of(projectVersion, declaration));
        final String result = renderer.render(project, codeVersions, 1);
        assertThat(result, containsString("<a href=\"https://github.com/exasol/the-project/blob/1.3.5"
                + "/src/main/java/com/exasol/example/Example.java#L26\">"
                + "<code>/src/main/java/com/exasol/example/Example.java:26</code></a>"));
    }
}
