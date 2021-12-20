package com.exasol.errorcodecatalog.loader;

import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorCodeReportReader;

public class ReleasedErrorCodeReportReader {
    public ErrorCodeReport readReport(final ReleasedErrorCodeReport releasedReport)
            throws ErrorCodeReportReader.ReadException {
        final ErrorCodeReport report = new ErrorCodeReportReader().readReport(releasedReport.errorCodeReport());
        return addInfosFromReleaseToReportIfEmpty(releasedReport, report);
    }

    private ErrorCodeReport addInfosFromReleaseToReportIfEmpty(ReleasedErrorCodeReport releasedReport,
            ErrorCodeReport report) {
        ErrorCodeReport reportWithName = report;
        if (report.getProjectName() == null || report.getProjectName().isBlank()) {
            reportWithName = report.withProjectName(releasedReport.projectName());
        }
        if (report.getProjectVersion() == null || report.getProjectVersion().isBlank()) {
            return reportWithName.withProjectVersion(releasedReport.projectVersion());
        } else {
            return reportWithName;
        }
    }
}
