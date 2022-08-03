package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import j2html.tags.DomContent;

/**
 * This class renders a page of the error-catalog.
 */
public class ErrorCatalogPageRenderer {
    private final UrlBuilder urlBuilder;

    /**
     * Create a new instance of {@link ErrorCatalogPageRenderer}.
     * 
     * @param urlBuilder URL builder
     */
    public ErrorCatalogPageRenderer(final UrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    /**
     * Render a page of the error-catalog.
     * 
     * @param title          page title
     * @param subfolderDepth count of directories that this page is nested relative to the web root
     * @param content        page content
     * @return rendered HTML
     */
    public String render(final String title, final int subfolderDepth, final DomContent... content) {
        return html(//
                title("Exasol Error Catalog – " + title), //
                head(link().withRel("stylesheet").withHref("../".repeat(subfolderDepth) + "error-catalog-style.css")), //
                body(//
                        a(div(span("Exasol Error Catalog")).withId("navbar"))
                                .withHref("../".repeat(subfolderDepth) + this.urlBuilder.getUrlForFrontPage()), //
                        div(content).withId("mainBox")//
                )).render();
    }
}
