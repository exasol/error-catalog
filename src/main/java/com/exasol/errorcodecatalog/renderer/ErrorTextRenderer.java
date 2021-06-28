package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.span;
import static j2html.TagCreator.text;

import java.util.*;

import com.exasol.errorreporting.Placeholder;
import com.exasol.errorreporting.PlaceholderMatcher;

import j2html.tags.DomContent;

/**
 * This class renders error text (message / mitigation) as HTML with highlighted placeholders.
 */
public class ErrorTextRenderer {
    private final PlaceholderNumberProvider placeholderNumberProvider;

    /**
     * Create a new instance of {@link ErrorTextRenderer}.
     * 
     * @param placeholderNumberProvider number provider that assigns each placeholder a number
     */
    public ErrorTextRenderer(final PlaceholderNumberProvider placeholderNumberProvider) {
        this.placeholderNumberProvider = placeholderNumberProvider;
    }

    /**
     * Render error text (message / mitigation) as HTML with highlighted placeholders.
     *
     * @param text test to render
     * @return rendered HTML elements
     */
    public DomContent[] renderTextWithPlaceholders(final String text) {
        final List<DomContent> textAsHtml = new ArrayList<>();
        int lastIndex = 0;
        while (true) {
            final int paramStartIndex = text.indexOf("{{", lastIndex);
            final int paramEndIndex = text.indexOf("}}", paramStartIndex);
            if (paramStartIndex == -1 || paramEndIndex == -1) {
                textAsHtml.add(text(text.substring(lastIndex)));
                break;
            } else {
                textAsHtml.add(text(text.substring(lastIndex, paramStartIndex)));
                final String parameter = text.substring(paramStartIndex, paramEndIndex + 2);
                final int placeholderNumber = getPlaceholderNumber(this.placeholderNumberProvider, parameter);
                textAsHtml.add(span(parameter).withClass("param").attr("nr", placeholderNumber));
                lastIndex = paramEndIndex + 2;
            }
        }
        return textAsHtml.toArray(DomContent[]::new);
    }

    private int getPlaceholderNumber(final PlaceholderNumberProvider placeholderNumberProvider,
            final String parameter) {
        final Iterator<Placeholder> placeholders = PlaceholderMatcher.findPlaceholders(parameter).iterator();
        if (placeholders.hasNext()) {
            return placeholderNumberProvider.getNumberFor(placeholders.next().getName());
        } else {
            return -1;
        }
    }
}
