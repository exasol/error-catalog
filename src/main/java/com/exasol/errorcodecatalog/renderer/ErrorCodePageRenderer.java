package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import java.util.ArrayList;
import java.util.List;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;
import com.exsol.errorcodemodel.NamedParameter;

import j2html.tags.DomContent;
import j2html.tags.specialized.LiTag;
import j2html.tags.specialized.TrTag;

/**
 * This class renders a catalog page per error-code.
 */
class ErrorCodePageRenderer {

    /**
     * Render a catalog page for an error-code.
     * 
     * @param errorCodeVersions all versions of the error code
     * @param subfolderDepth    count of directories that this page is nested relative to the web root
     * @return rendered HTML page
     */
    String render(final ErrorCodeVersions errorCodeVersions, final int subfolderDepth) {
        final ErrorMessageDeclaration errorMessageDeclaration = errorCodeVersions
                .getVersion(errorCodeVersions.getLatestVersionNumber());
        final List<DomContent> htmlElements = new ArrayList<>();
        htmlElements.add(h1(errorMessageDeclaration.getIdentifier()));
        htmlElements.add(h4("Message"));
        final PlaceholderNumberProvider placeholderNumberProvider = new PlaceholderNumberProvider();
        final ErrorTextRenderer textRenderer = new ErrorTextRenderer(placeholderNumberProvider);
        final DomContent[] messageHtml = textRenderer.renderTextWithPlaceholders(errorMessageDeclaration.getMessage());
        htmlElements.add(div(messageHtml).withClass("code"));
        addMitigationSection(htmlElements, textRenderer, errorMessageDeclaration.getMitigations());

        addPlaceholdersSection(htmlElements, placeholderNumberProvider, errorMessageDeclaration.getNamedParameters());

        return new ErrorCatalogPageRender(new UrlBuilder()).render(errorMessageDeclaration.getIdentifier(),
                subfolderDepth, htmlElements.toArray(DomContent[]::new));
    }

    private void addPlaceholdersSection(final List<DomContent> htmlElements,
            final PlaceholderNumberProvider placeholderNumberProvider, final List<NamedParameter> namedParameters) {
        if (existsPlaceholderWithDescription(namedParameters)) {
            htmlElements.add(h4("Parameters"));
            final List<TrTag> placeholdersHtml = new ArrayList<>();
            for (final NamedParameter placeholder : namedParameters) {
                final DomContent placeholderDescriptionHtml = getPlaceholderDescription(placeholder);
                placeholdersHtml.add(tr(
                        td(span(text(placeholder.getName())).withClass("param").attr("nr",
                                placeholderNumberProvider.getNumberFor(placeholder.getName()))),
                        td(placeholderDescriptionHtml)));
            }
            htmlElements.add(table(placeholdersHtml.toArray(DomContent[]::new)));
        }
    }

    private boolean existsPlaceholderWithDescription(final List<NamedParameter> namedParameters) {
        return namedParameters.stream().anyMatch(
                placeholder -> placeholder.getDescription() != null && !placeholder.getDescription().isBlank());
    }

    private DomContent getPlaceholderDescription(final NamedParameter placeholder) {
        final String description = placeholder.getDescription();
        if (description == null || description.isBlank()) {
            return span("No description").withClass("no-description");
        } else {
            return text(description);
        }
    }

    private void addMitigationSection(final List<DomContent> htmlElements, final ErrorTextRenderer textRenderer,
            final List<String> mitigations) {
        if (mitigations.size() == 1) {
            htmlElements.add(h4("Mitigation"));
            final DomContent[] mitigationHtml = textRenderer.renderTextWithPlaceholders(mitigations.get(0));
            htmlElements.add(div(mitigationHtml).withClass("code"));
        } else if (mitigations.size() > 1) {
            htmlElements.add(h4("Mitigations"));
            final List<LiTag> mitigationsHtml = new ArrayList<>();
            for (final String mitigation : mitigations) {
                final DomContent[] mitigationHtml = textRenderer.renderTextWithPlaceholders(mitigation);
                mitigationsHtml.add(li(mitigationHtml));
            }
            htmlElements.add(div(mitigationsHtml.toArray(DomContent[]::new)).withClass("code"));
        }
    }
}
