package com.exasol.errorcodecatalog.collector;

/**
 * This class describes a release with error-code-report.
 */
final class ReleaseReference {
    private final String repository;
    private final String version;
    private final String errorReportUrl;

    ReleaseReference(final String repository, final String version, final String errorReportUrl) {
        this.repository = repository;
        this.version = version;
        this.errorReportUrl = errorReportUrl;
    }

    public String repository() {
        return this.repository;
    }

    public String version() {
        return this.version;
    }

    public String errorReportUrl() {
        return this.errorReportUrl;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        final var that = (ReleaseReference) obj;
        return java.util.Objects.equals(this.repository, that.repository)
                && java.util.Objects.equals(this.version, that.version)
                && java.util.Objects.equals(this.errorReportUrl, that.errorReportUrl);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(this.repository, this.version, this.errorReportUrl);
    }

    @java.lang.Override
    public String toString() {
        return "ReleaseReference[" + "repository=" + this.repository + ", " + "version=" + this.version + ", "
                + "errorReportUrl=" + this.errorReportUrl + ']';
    }

}
