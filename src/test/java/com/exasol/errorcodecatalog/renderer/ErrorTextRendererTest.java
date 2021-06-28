package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.div;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import j2html.tags.DomContent;

class ErrorTextRendererTest {

    @Test
    void testTextWithPlaceholder() {
        final DomContent[] result = new ErrorTextRenderer().renderTextWithPlaceholders("My text with {{placeholder}}.",
                new PlaceholderNumberProvider());
        assertThat(div(result).render(),
                equalTo("<div>My text with <span class=\"param\" nr=\"0\">{{placeholder}}</span>.</div>"));
    }

    @Test
    void testTextWithoutPlaceholder() {
        final DomContent[] result = new ErrorTextRenderer().renderTextWithPlaceholders("My text without placeholder.",
                new PlaceholderNumberProvider());
        assertThat(div(result).render(), equalTo("<div>My text without placeholder.</div>"));
    }

    @Test
    void testTextWithTwoPlaceholders() {
        final DomContent[] result = new ErrorTextRenderer().renderTextWithPlaceholders("{{A}}{{B}}",
                new PlaceholderNumberProvider());
        assertThat(div(result).render(), equalTo(
                "<div><span class=\"param\" nr=\"0\">{{A}}</span><span class=\"param\" nr=\"1\">{{B}}</span></div>"));
    }
}