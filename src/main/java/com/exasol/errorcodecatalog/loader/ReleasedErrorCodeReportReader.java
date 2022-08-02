package com.exasol.errorcodecatalog.loader;

import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorCodeReportReader;

/**
 * Reader for {@link ErrorCodeReport}.
 */
public class ReleasedErrorCodeReportReader {
    /**
     * Read an {@link ErrorCodeReport}.
     * 
     * @param releasedReport report
     * @return read {@link ErrorCodeReport}
     * @throws ErrorCodeReportReader.ReadException if read fails
     */
    public ErrorCodeReport readReport(final ReleasedErrorCodeReport releasedReport)
            throws ErrorCodeReportReader.ReadException {
        final ErrorCodeReport report = new ErrorCodeReportReader().readReport(releasedReport.getErrorCodeReport());
        return addInfosFromReleaseToReportIfEmpty(releasedReport, report);
    }

    private ErrorCodeReport addInfosFromReleaseToReportIfEmpty(ReleasedErrorCodeReport releasedReport,
            ErrorCodeReport report) {
        final String projectName = (report.getProjectName() == null || report.getProjectName().isBlank()) ?
               releasedReport.getProjectName() : report.getProjectName();
        final String projectVersion = (report.getProjectVersion() == null || report.getProjectVersion().isBlank()) ?
                releasedReport.getProjectVersion() : report.getProjectVersion();
        return new ErrorCodeReport(projectName, projectVersion, report.getErrorMessageDeclarations());
    }
}
