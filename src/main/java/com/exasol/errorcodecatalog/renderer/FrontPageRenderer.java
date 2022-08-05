package com.exasol.errorcodecatalog.renderer;

import static j2html.TagCreator.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import j2html.tags.DomContent;
import j2html.tags.specialized.LiTag;

/**
 * This class renders a page with a list of all projects.
 */
public class FrontPageRenderer {

    /**
     * Render a page with a list of all projects.
     * 
     * @param projects       projects information
     * @param subfolderDepth count of directories that this page is nested relative to the web root
     * @param urlBuilder     URL builder to generate links for projects
     * @return rendered HTML page
     */
    public String render(final List<Project> projects, final int subfolderDepth, final UrlBuilder urlBuilder) {
        final List<DomContent> htmlElements = new ArrayList<>();
        htmlElements.add(h1("Exasol Error Catalog"));
        final List<LiTag> projectsHtml = new ArrayList<>(projects.size());
        projects.stream() //
                .sorted(Comparator.comparing(Project::getProjectName)) //
                .forEach(project -> //
                        projectsHtml.add(li(a(project.getProjectName())
                                .withHref("../".repeat(subfolderDepth) + urlBuilder.getUrlFor(project).toString())))
                );
        htmlElements.add(ul(projectsHtml.toArray(DomContent[]::new)));
        return new ErrorCatalogPageRenderer(urlBuilder).render("Overview", subfolderDepth,
                htmlElements.toArray(DomContent[]::new));
    }
}
