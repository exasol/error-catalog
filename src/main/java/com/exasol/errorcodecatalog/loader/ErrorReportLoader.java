package com.exasol.errorcodecatalog.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exasol.errorreporting.ExaError;
import com.exsol.errorcodemodel.ErrorCodeReport;
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
    public List<ErrorCodeReport> loadReports(final List<ReleasedErrorCodeReport> releasedReports) {
        final List<ErrorCodeReport> loadedReports = new ArrayList<>();
        final ReleasedErrorCodeReportReader reportReader = new ReleasedErrorCodeReportReader();
        for (final ReleasedErrorCodeReport releasedReport : releasedReports) {
            try {
                final ErrorCodeReport report = reportReader.readReport(releasedReport);
                loadedReports.add(report);
            } catch (final ErrorCodeReportReader.ReadException exception) {
                LOGGER.severe(ExaError.messageBuilder("E-EC-4").message(
                        "Failed to parse error-code-report of {{repository name}} {{version}}. Case: {{cause|uq}}",
                        releasedReport.getProjectName(), releasedReport.getProjectVersion(), exception.getMessage())
                        .toString());
            }
        }
        return loadedReports;
    }
}
