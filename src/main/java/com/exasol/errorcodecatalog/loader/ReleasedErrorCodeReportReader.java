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
     * @throws ErrorCodeReportReader.ReadException
     */
    public ErrorCodeReport readReport(final ReleasedErrorCodeReport releasedReport)
            throws ErrorCodeReportReader.ReadException {
        final ErrorCodeReport report = new ErrorCodeReportReader().readReport(releasedReport.getErrorCodeReport());
        return addInfosFromReleaseToReportIfEmpty(releasedReport, report);
    }

    private ErrorCodeReport addInfosFromReleaseToReportIfEmpty(ReleasedErrorCodeReport releasedReport,
            ErrorCodeReport report) {
        ErrorCodeReport reportWithName = report;
        if (report.getProjectName() == null || report.getProjectName().isBlank()) {
            reportWithName = report.withProjectName(releasedReport.getProjectName());
        }
        if (report.getProjectVersion() == null || report.getProjectVersion().isBlank()) {
            return reportWithName.withProjectVersion(releasedReport.getProjectVersion());
        } else {
            return reportWithName;
        }
    }
}
