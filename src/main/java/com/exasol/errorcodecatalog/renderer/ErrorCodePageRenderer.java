package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import java.util.ArrayList;
import java.util.List;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;

import j2html.tags.DomContent;

class ErrorCodePageRenderer {

    String render(final ErrorMessageDeclaration errorMessageDeclaration) {
        final List<DomContent> htmlElements = new ArrayList<>();
        htmlElements.add(h1(errorMessageDeclaration.getIdentifier()));
        htmlElements.add(h4("Message"));
        final DomContent[] messageHtml = new ErrorTextRenderer()
                .renderTextWithPlaceholders(errorMessageDeclaration.getMessage(), new PlaceholderNumberProvider());
        htmlElements.add(div(messageHtml).withClass("code"));
        htmlElements.add(h4("Placeholders"));
        return new ErrorCatalogPageRender().render(errorMessageDeclaration.getIdentifier(),
                htmlElements.toArray(DomContent[]::new));
    }
}
