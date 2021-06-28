package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the error code reports from the GitHub releases.
 */
public class ErrorReportCollector {

    /**
     * Collect the error code reports from the GitHub releases.
     * 
     * @return list of error code reports
     */
    public List<ReleasedErrorCodeReport> collectReports() {
        final List<ReleaseReference> releases = new ErrorReportFinder().findErrorReports();
        final ErrorReportDownloader downloader = new ErrorReportDownloader();
        final List<ReleasedErrorCodeReport> releasedReports = new ArrayList<>();
        for (final ReleaseReference release : releases) {
            final Path report = downloader.downloadReportIfNotExists(release);
            releasedReports.add(new ReleasedErrorCodeReport(report, release.repository(), release.version()));
        }
        return releasedReports;
    }
}
