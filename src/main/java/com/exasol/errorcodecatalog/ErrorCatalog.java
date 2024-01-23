package com.exasol.errorcodecatalog;

import static com.exasol.errorcodecatalog.collector.GithubTokenReader.readTokenFromEnv;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.exasol.errorcodecatalog.collector.*;
import com.exasol.errorcodecatalog.loader.ErrorReportLoader;
import com.exasol.errorcodecatalog.renderer.ErrorCatalogRenderer;
import com.exsol.errorcodemodel.ErrorCodeReport;

import picocli.CommandLine;

/**
 * This class is the main entry point of the error-catalog generator.
 * <p>
 * Before running this app please set an environment variable with the GitHub login. See:
 * https://github-api.kohsuke.org/index.html#Environmental_variables.
 * </p>
 */
@CommandLine.Command( //
        name = "error-catalog", //
        description = "Exasol error-catalog generator" //
)
public class ErrorCatalog implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ErrorCatalog.class.getName());

    @CommandLine.Option(names = {
            "--report-repo" }, required = true, description = "Local directory for caching the error-report JSON files.")
    private String reportRepo;
    @CommandLine.Option(names = { "-o",
            "--output-directory" }, required = true, description = "Directory were the website will be written to.")
    private String outputDirectory;

    /**
     * Entry point.
     *
     * @param arguments command line arguments
     */
    public static void main(final String[] arguments) {
        final CommandLine commandLineClient = new CommandLine(new ErrorCatalog());
        final int exitCode = commandLineClient.execute(arguments);
        System.exit(exitCode);
    }

    /**
     * Entry point of the error-catalog generator.
     */
    @Override
    public void run() {
        LOGGER.info(() -> "Starting ErrorCatalog using repo path: " + reportRepo + " and output directory: "
                + outputDirectory);
        final GithubToken githubToken = readTokenFromEnv();
        final List<ReleasedErrorCodeReport> reports = new ErrorReportCollector(Path.of(this.reportRepo), githubToken)
                .collectReports();
        final List<ErrorCodeReport> loadedReports = new ErrorReportLoader().loadReports(reports);
        new ErrorCatalogRenderer(Path.of(this.outputDirectory)).render(loadedReports);
    }
}
