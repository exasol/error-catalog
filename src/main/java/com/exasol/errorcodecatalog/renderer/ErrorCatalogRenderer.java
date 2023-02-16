package com.exasol.errorcodecatalog.renderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorCodeReport;

/**
 * This class renders the error catalog web site.
 */
public class ErrorCatalogRenderer {
    private final Path outputDirectory;
    private final UrlBuilder urlBuilder;

    /**
     * Create a new instance of {@link ErrorCatalogRenderer}.
     *
     * @param outputDirectory directory to write the report to
     */
    public ErrorCatalogRenderer(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.urlBuilder = new UrlBuilder();
    }

    /**
     * Render the error-catalog web site.
     *
     * @param loadedReports error code reports
     */
    public void render(final List<ErrorCodeReport> loadedReports) {
        deleteTargetDirectory();
        createDirectoryIfNotExists(this.outputDirectory);
        copyResource("error-catalog-style.css");
        copyResource("logo_error_catalog.svg");
        copyResource("Inter-Bold.woff");
        copyResource("Inter-Italic.woff");
        copyResource("Inter-Regular.woff");
        final List<Project> projects = Project.groupByProject(loadedReports);
        generateErrorCodePages(projects);
        generateProjectsPages(projects);
        generateFrontPage(projects);
    }

    private void generateFrontPage(final List<Project> projects) {
        final FrontPageRenderer frontPageRenderer = new FrontPageRenderer();
        final Path url = this.urlBuilder.getUrlForFrontPage();
        writePage(url, frontPageRenderer.render(projects, url.getNameCount() - 1, this.urlBuilder));
    }

    private void generateProjectsPages(final List<Project> projects) {
        final ProjectPageRenderer projectPageRenderer = new ProjectPageRenderer(this.urlBuilder);
        for (final Project project : projects) {
            final Path pageUrl = this.urlBuilder.getPathFor(project);
            writePage(pageUrl, projectPageRenderer.render(project, pageUrl.getNameCount() - 1));
        }
    }

    private void deleteTargetDirectory() {
        if (Files.exists(this.outputDirectory)) {
            try (final Stream<Path> pathStream = Files.walk(this.outputDirectory)) {
                pathStream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } catch (final IOException exception) {
                throw new IllegalStateException(ExaError.messageBuilder("E-EC-8")
                        .message("Failed to empty error-catalog target directory.").toString(), exception);
            }
        }
    }

    private void copyResource(final String resourceName) {
        try (final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            Files.copy(Objects.requireNonNull(resourceAsStream), this.outputDirectory.resolve(resourceName));
        } catch (final IOException | NullPointerException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-EC-7")
                    .message("Failed to copy resource {{resource}}.", resourceName).toString(), exception);
        }
    }

    private void createDirectoryIfNotExists(final Path directory) {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (final IOException exception) {
                throw new IllegalStateException(ExaError.messageBuilder("E-EC-6")
                        .message("Failed to create required directory {{directory}}.", directory).toString());
            }
        }
    }

    private void generateErrorCodePages(final List<Project> projects) {
        for (final Project project : projects) {
            for (final ErrorCodeVersions errorCodeVersions : project.getErrorCodes()) {
                final ErrorCodePageRenderer errorCodePageRenderer = new ErrorCodePageRenderer();
                final Path url = this.urlBuilder.getUrlFor(errorCodeVersions);
                writePage(url, errorCodePageRenderer.render(project, errorCodeVersions, url.getNameCount() - 1));
            }
        }
    }

    private void writePage(final Path fileName, final String page) {
        createDirectoryIfNotExists(this.outputDirectory.resolve(fileName).getParent());
        try {
            Files.writeString(this.outputDirectory.resolve(fileName), page);
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-5").message("Failed to write catalog page.").toString(), exception);
        }
    }
}
