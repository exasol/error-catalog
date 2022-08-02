package com.exasol.errorcodecatalog.collector;


/**
 * This class represents the reference to a release of an error code report in a repository.
 */
final class ReleaseReference {
    private final String repository;
    private final String version;
    private final String errorReportUrl;

    /**
     * Create a new instance of a {@link ReleaseReference}.
     *
     * @param repository repository the release can be found in
     * @param version version behind the release
     * @param errorReportUrl URL of the error report
     */
    public ReleaseReference(final String repository, final String version, final String errorReportUrl) {
        this.repository = repository;
        this.version = version;
        this.errorReportUrl = errorReportUrl;
    }

    /**
     * Get the repository the release can be found in.
     *
     * @return repository the release can be found in
     */
    public String getRepository() {
        return repository;
    }

    /**
     * Get the released version.
     *
     * @return released version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the URL of the error code report.
     *
     * @return URL of the error code report
     */
    public String getErrorReportUrl() {
        return errorReportUrl;
    }
}
