package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.h1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class ErrorCatalogPageRenderTest {
    @Test
    void test() {
        final String result = new ErrorCatalogPageRender().render("My Page", h1("Test"));
        assertThat(result, equalTo(
                "<html><title>Exasol Error Catalog – My Page</title><head><link rel=\"stylesheet\" href=\"../error-catalog-style.css\"></head><body><div id=\"navbar\"><span>Exasol Error Catalog</span></div><div id=\"mainBox\"><h1>Test</h1></div></body></html>"));
    }
}