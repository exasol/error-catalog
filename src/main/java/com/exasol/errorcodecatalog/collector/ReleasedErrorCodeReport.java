package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;

/**
 * This record describes releases that contain an error-code-report.
 */
public final class ReleasedErrorCodeReport {
    private final Path errorCodeReport;
    private final String projectName;
    private final String projectVersion;

    public ReleasedErrorCodeReport(final Path errorCodeReport, final String projectName, final String projectVersion) {
        this.errorCodeReport = errorCodeReport;
        this.projectName = projectName;
        this.projectVersion = projectVersion;
    }

    public Path errorCodeReport() {
        return this.errorCodeReport;
    }

    public String projectName() {
        return this.projectName;
    }

    public String projectVersion() {
        return this.projectVersion;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        final var that = (ReleasedErrorCodeReport) obj;
        return java.util.Objects.equals(this.errorCodeReport, that.errorCodeReport)
                && java.util.Objects.equals(this.projectName, that.projectName)
                && java.util.Objects.equals(this.projectVersion, that.projectVersion);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(this.errorCodeReport, this.projectName, this.projectVersion);
    }

    @java.lang.Override
    public String toString() {
        return "ReleasedErrorCodeReport[" + "errorCodeReport=" + this.errorCodeReport + ", " + "projectName="
                + this.projectName + ", " + "projectVersion=" + this.projectVersion + ']';
    }
}
