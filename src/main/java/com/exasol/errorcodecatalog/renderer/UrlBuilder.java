package com.exasol.errorcodecatalog.renderer;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Locale;

import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * This class defines the URLs of the pages in the error-catalog.
 */
public class UrlBuilder {
    public static final String GITHUB_HOST = "github.com";
    private static final String GITHUB_ORGANIZATION = "exasol";
    public static final String URI_PATH_SEPARATOR = "/";
    private static final String SUFFIX = ".html";

    /**
     * Get the HTML URL for a project page
     *
     * @param project project
     * @return URL as String
     */
    public String getUrlFor(final Project project) {
        return "projects" + URI_PATH_SEPARATOR + getUrlFriendlyProjectName(project) + SUFFIX;
    }

    /**
     * Get the path for a project page
     *
     * @param project project
     * @return URL as Path
     */
    public Path getPathFor(final Project project) {
        return Path.of("projects", getUrlFriendlyProjectName(project) + SUFFIX);
    }

    private String getUrlFriendlyProjectName(final Project project) {
        return project.getProjectName().replace(" ", "-").replace(URI_PATH_SEPARATOR, "-");
    }

    /**
     * Get the URL for a error-code page.
     *
     * @param errorCode error code
     * @return URL
     */
    public Path getUrlFor(final ErrorCodeVersions errorCode) {
        return Path.of("error-codes", errorCode.getIdentifier().toLowerCase(Locale.ROOT) + SUFFIX);
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
     * @param project        project this error is defined in
     * @param projectVersion version of the project
     * @param error          error declaration
     * @return URL to the source in GitHub
     */
    public URI getSourceUriFor(final Project project, final String projectVersion,
            final ErrorMessageDeclaration error) {
        final int line = error.getLine();
        final String sourcePath = error.getSourceFile();
        final String uriPath = URI_PATH_SEPARATOR + GITHUB_ORGANIZATION + URI_PATH_SEPARATOR + project.getProjectName()
                + URI_PATH_SEPARATOR + "blob" + URI_PATH_SEPARATOR + projectVersion
                + (sourcePath.startsWith(URI_PATH_SEPARATOR) ? "" : URI_PATH_SEPARATOR) + sourcePath;
        final String fragment = "L" + line;
        try {
            return new URI("https", GITHUB_HOST, uriPath, fragment);
        } catch (final URISyntaxException exception) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-EC-18")
                    .message("Unable to create source link URI").ticketMitigation().toString(), exception);
        }
    }
}
