package com.exasol.errorcodecatalog.renderer;

import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Locale;

/**
 * This class defines the URLs of the pages in the error-catalog.
 */
public class UrlBuilder {
    public static final String GITHUB_HOST = "github.com";
    private static final String GITHUB_ORGANIZATION = "exasol";
    public static final String URI_PATH_SEPARATOR = "/";

    /**
     * Get the URL for a project page.
     * 
     * @param project project
     * @return URL
     */
    public Path getUrlFor(final Project project) {
        final String urlFriendlyProjectName = project.getProjectName().replace(" ", "-").replace(URI_PATH_SEPARATOR, "-");
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

    /**
     * The URI where the source code can be found that defines the error code on GitHub.
     *
     * @param project project this error is defined in
     * @param projectVersion version of the project
     * @param error error declaration
     * @return URL to the source in GitHub
     */
    public URI getSourceUriFor(final Project project, final String projectVersion,
                               final ErrorMessageDeclaration error) {
        final int line = error.getLine();
        final String path = URI_PATH_SEPARATOR + GITHUB_ORGANIZATION + URI_PATH_SEPARATOR + project.getProjectName()
                + URI_PATH_SEPARATOR + "blob" + URI_PATH_SEPARATOR  + projectVersion
                + error.getSourceFile();
        final String fragment = "L" + line;
        try {
            return new URI("https", GITHUB_HOST, path, fragment);
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-EC-18")
                    .message("Unable to create source link URI").ticketMitigation().toString(), exception);
        }
    }
}
