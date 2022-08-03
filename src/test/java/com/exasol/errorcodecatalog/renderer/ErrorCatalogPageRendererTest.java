package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.h1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class ErrorCatalogPageRendererTest {
    private ErrorCatalogPageRenderer renderer;

    @BeforeEach
    void beforeEach() {
        this.renderer = new ErrorCatalogPageRenderer(new UrlBuilder());
    }

    @Test
    void testGeneration() {
        final String result = renderer.render("My Page", 1, h1("Test"));
        assertThat(result, equalTo("<html><title>Exasol Error Catalog – My Page</title>"
                + "<head><link rel=\"stylesheet\" href=\"../error-catalog-style.css\"></head>"
                + "<body><a href=\"../index.html\"><div id=\"navbar\"><span>Exasol Error Catalog</span></div></a>"
                + "<div id=\"mainBox\"><h1>Test</h1></div></body></html>"));
    }

    @ParameterizedTest
    @CsvSource(value = { //
            "0, \"error-catalog-style.css", //
            "1, \"../error-catalog-style.css", //
            "2, \"../../error-catalog-style.css",//
    })
    void testSubfolderDepth(final int depth, final String expectedString) {
        final String result = renderer.render("My Page", depth, h1("Test"));
        assertThat(result, containsString(expectedString));
    }
}