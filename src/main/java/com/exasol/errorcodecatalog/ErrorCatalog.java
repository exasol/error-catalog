package com.exasol.errorcodecatalog;

import java.util.List;

import com.exasol.errorcodecatalog.collector.ErrorReportCollector;
import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exasol.errorcodecatalog.loader.ErrorReportLoader;
import com.exasol.errorcodecatalog.loader.LoadedReport;
import com.exasol.errorcodecatalog.renderer.ErrorCatalogRenderer;

/**
 * This class is the main entry point of the error-catalog generator.
 * <p>
 * Before running this app please set an environment variable with the GitHub login. See:
 * https://github-api.kohsuke.org/index.html#Environmental_variables.
 * </p>
 */
public class ErrorCatalog {
    /**
     * Entry point of the error-catalog generator.
     * 
     * @param args command line args (unused)
     */
    public static void main(final String[] args) {
        final List<ReleasedErrorCodeReport> reports = new ErrorReportCollector().collectReports();
        final List<LoadedReport> loadedReports = new ErrorReportLoader().loadReports(reports);
        new ErrorCatalogRenderer().render(loadedReports);
    }
}
