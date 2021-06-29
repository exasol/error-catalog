package com.exasol.errorcodecatalog.renderer;

import java.nio.file.Path;
import java.util.Locale;

/**
 * This class defines the URLs of the pages in the error-catalog.
 */
public class UrlBuilder {

    /**
     * Get the URL for a project page.
     * 
     * @param project project
     * @return URL
     */
    public Path getUrlFor(final Project project) {
        final String urlFriendlyProjectName = project.getProjectName().replace(" ", "-").replace("/", "-");
        return Path.of("projects", urlFriendlyProjectName + ".html");
    }

    /**
     * Get the URL for a error-code page.
     * 
     * @param errorCode error code
     * @return URL
     */
    public Path getUrlFor(final ErrorCodeVersions errorCode) {
        return Path.of("error-codes", errorCode.getIdentifier().toLowerCase(Locale.ROOT) + ".html");
    }

    /**
     * Get the URL for the front-page.
     * 
     * @return URL
     */
    public Path getUrlForFrontPage() {
        return Path.of("index.html");
    }
}
