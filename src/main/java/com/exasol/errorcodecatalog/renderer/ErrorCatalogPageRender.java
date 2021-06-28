package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import j2html.tags.DomContent;

/**
 * This class renders a page of the error-catalog.
 */
public class ErrorCatalogPageRender {
    /**
     * Render a page of the error-catalog.
     * 
     * @param title   page title
     * @param content page content
     * @return rendered HTML
     */
    public String render(final String title, final DomContent... content) {
        return html(//
                title("Exasol Error Catalog – " + title), //
                head(link().withRel("stylesheet").withHref("../error-catalog-style.css")), //
                body(//
                        div(span("Exasol Error Catalog")).withId("navbar"), //
                        div(content).withId("mainBox")//
                )).render();
    }
}
