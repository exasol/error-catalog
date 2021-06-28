package com.exasol.errorcodecatalog.renderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import com.exasol.errorcodecatalog.loader.LoadedReport;
import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * This class renders the error catalog web site.
 */
public class ErrorCatalogRenderer {
    private static final String ERROR_CODES_DIRECTORY = "error-codes";
    private final Path outputDirectory;

    /**
     * Create a new instance of {@link ErrorCatalogRenderer}.
     * 
     * @param outputDirectory directory to write the report to
     */
    public ErrorCatalogRenderer(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Render the error-catalog web site.
     * 
     * @param loadedReports error code reports
     */
    public void render(final List<LoadedReport> loadedReports) {
        deleteTargetDirectory();
        createDirectoryIfNotExists(this.outputDirectory);
        copyResource("error-catalog-style.css");
        copyResource("logo_error_catalog.svg");
        generateErrorCodePages(loadedReports);
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

    private void generateErrorCodePages(final List<LoadedReport> loadedReports) {
        final ErrorCodePageRenderer errorCodePageRenderer = new ErrorCodePageRenderer();
        createDirectoryIfNotExists(this.outputDirectory.resolve(ERROR_CODES_DIRECTORY));
        for (final LoadedReport loadedReport : loadedReports) {
            for (final ErrorMessageDeclaration errorMessageDeclaration : loadedReport.report()
                    .getErrorMessageDeclarations()) {
                final String page = errorCodePageRenderer.render(errorMessageDeclaration);
                writePage(Path.of(ERROR_CODES_DIRECTORY, errorMessageDeclaration.getIdentifier() + ".html"), page);
            }
        }
    }

    private void writePage(final Path fileName, final String page) {
        try {
            Files.writeString(this.outputDirectory.resolve(fileName), page);
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-5").message("Failed to write catalog page.").toString(), exception);
        }
    }
}
