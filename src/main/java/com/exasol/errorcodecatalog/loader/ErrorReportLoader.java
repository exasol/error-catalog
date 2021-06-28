package com.exasol.errorcodecatalog.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorCodeReportReader;

/**
 * This class loads the error-code-reports from file.
 */
public class ErrorReportLoader {
    private static final Logger LOGGER = Logger.getLogger(ErrorReportLoader.class.getName());

    /**
     * Load the error-code-reports from file.
     * 
     * @param releasedReports released reports
     * @return loaded reports
     */
    public List<LoadedReport> loadReports(final List<ReleasedErrorCodeReport> releasedReports) {
        final List<LoadedReport> loadedReports = new ArrayList<>();
        for (final ReleasedErrorCodeReport releasedReport : releasedReports) {
            try {
                loadedReports.add(
                        new LoadedReport(new ErrorCodeReportReader().readReport(releasedReport.errorCodeReport())));
            } catch (final ErrorCodeReportReader.ReadException exception) {
                LOGGER.severe(ExaError.messageBuilder("E-EC-4").message(
                        "Failed to parse error-code-report of {{repository name}} {{version}}. Case: {{cause|uq}}",
                        releasedReport.projectName(), releasedReport.projectVersion(), exception.getMessage())
                        .toString());
            }
        }
        return loadedReports;
    }
}
