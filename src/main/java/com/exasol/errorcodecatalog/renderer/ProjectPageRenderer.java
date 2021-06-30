package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import java.util.*;
import java.util.function.Function;

import j2html.tags.DomContent;
import j2html.tags.specialized.LiTag;
import j2html.tags.specialized.UlTag;

/**
 * This class renders a page with a list of the project's error codes.
 */
public class ProjectPageRenderer {
    private final UrlBuilder urlBuilder;

    /**
     * Create a new instance of {@link UrlBuilder}.
     * 
     * @param urlBuilder URL builder
     */
    public ProjectPageRenderer(final UrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    /**
     * Render a page with a list of the project's error codes.
     * 
     * @param project        project error-code information
     * @param subfolderDepth count of directories that this page is nested relative to the web root
     * @return rendered HTML page
     */
    public String render(final Project project, final int subfolderDepth) {
        final List<DomContent> htmlElements = new ArrayList<>();
        htmlElements.add(h1("Error messages of " + project.getProjectName()));
        final List<ErrorCodeVersions> activeErrorCodes = new ArrayList<>(project.getActiveErrorCodes());
        htmlElements.add(renderErrorCodeList(activeErrorCodes, ErrorCodeVersions::getIdentifier, subfolderDepth));
        addDeprecatedCodesSection(project, htmlElements, subfolderDepth);
        return new ErrorCatalogPageRender(this.urlBuilder).render(project.getProjectName(), subfolderDepth,
                htmlElements.toArray(DomContent[]::new));
    }

    private void addDeprecatedCodesSection(final Project project, final List<DomContent> htmlElements,
            final int subfolderDepth) {
        final List<ErrorCodeVersions> deprecatedErrorCodes = new ArrayList<>(project.getDeprecatedErrorCodes());
        if (!deprecatedErrorCodes.isEmpty()) {
            htmlElements.add(h4("Deprecated Messages"));
            htmlElements.add(text("The following error codes are longer in use."));
            htmlElements.add(renderErrorCodeList(deprecatedErrorCodes,
                    code -> code.getIdentifier() + " (last used in version " + code.getLatestVersionNumber() + ")",
                    subfolderDepth));
        }
    }

    private UlTag renderErrorCodeList(final List<ErrorCodeVersions> activeErrorCodes,
            final Function<ErrorCodeVersions, String> textRenderer, final int subfolderDepth) {
        activeErrorCodes.sort(Comparator.comparing(ErrorCodeVersions::getIdentifier));
        final List<LiTag> codesHtml = new ArrayList<>(activeErrorCodes.size());
        for (final ErrorCodeVersions errorCode : activeErrorCodes) {
            codesHtml.add(li(a(textRenderer.apply(errorCode))
                    .withHref("../".repeat(subfolderDepth) + this.urlBuilder.getUrlFor(errorCode).toString())));
        }
        return ul(codesHtml.toArray(DomContent[]::new));
    }
}
