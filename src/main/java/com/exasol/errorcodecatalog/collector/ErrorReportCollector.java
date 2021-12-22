package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the error code reports from the GitHub releases.
 */
public class ErrorReportCollector {
    private final Path localRepository;
    private final GithubToken githubToken;

    /**
     * Create a new instance of {@link ErrorReportCollector}.
     *
     * @param localRepository directory for the local error-report json file repository
     * @param githubToken     GitHub token
     */
    public ErrorReportCollector(final Path localRepository, GithubToken githubToken) {
        this.localRepository = localRepository;
        this.githubToken = githubToken;
    }

    /**
     * Collect the error code reports from the GitHub releases.
     * 
     * @return list of error code reports
     */
    public List<ReleasedErrorCodeReport> collectReports() {
        final List<ReleaseReference> releases = new ErrorReportFinder(githubToken).findErrorReports();
        final ErrorReportDownloader downloader = new ErrorReportDownloader(this.localRepository, githubToken);
        final List<ReleasedErrorCodeReport> releasedReports = new ArrayList<>();
        for (final ReleaseReference release : releases) {
            final Path report = downloader.downloadReportIfNotExists(release);
            releasedReports.add(new ReleasedErrorCodeReport(report, release.getRepository(), release.getVersion()));
        }
        return releasedReports;
    }
}
