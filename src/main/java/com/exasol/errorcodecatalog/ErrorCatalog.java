package com.exasol.errorcodecatalog;

import java.nio.file.Path;
import java.util.List;

import com.exasol.errorcodecatalog.collector.ErrorReportCollector;
import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exasol.errorcodecatalog.loader.ErrorReportLoader;
import com.exasol.errorcodecatalog.loader.LoadedReport;
import com.exasol.errorcodecatalog.renderer.ErrorCatalogRenderer;

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

    @CommandLine.Option(names = {
            "--report-repo" }, required = true, description = "Local directory for caching the error-report JSON files.")
    private String reportRepo;
    @CommandLine.Option(names = { "-o",
            "--output-directory" }, required = true, description = "Directory were the website will be written to.")
    private String outputDirectory;

    public static void main(final String[] arguments) {
        final CommandLine commandLineClient = new CommandLine(new ErrorCatalog());
        final int exitCode = commandLineClient.execute(arguments);
        System.exit(exitCode);
    }

    /**
     * Entry point of the error-catalog generator.
     */
    public void run() {
        final List<ReleasedErrorCodeReport> reports = new ErrorReportCollector(Path.of(this.reportRepo))
                .collectReports();
        final List<LoadedReport> loadedReports = new ErrorReportLoader().loadReports(reports);
        new ErrorCatalogRenderer(Path.of(this.outputDirectory)).render(loadedReports);
    }
}
