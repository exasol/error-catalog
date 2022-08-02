package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;

/**
 * This record describes releases that contain an error code report.
 */
public final class ReleasedErrorCodeReport {
    private final Path errorCodeReport;
    private final String projectName;
    private final String projectVersion;

    /**
     * Create a new instance of a {@link ReleasedErrorCodeReport}.
     * @param errorCodeReport error code report in the release
     * @param projectName name of the project the report was released in
     * @param projectVersion version of the project that was released
     */
    public ReleasedErrorCodeReport(final Path errorCodeReport, final String projectName, final String projectVersion) {
        this.errorCodeReport = errorCodeReport;
        this.projectName = projectName;
        this.projectVersion = projectVersion;
    }

    /**
     * Get the error code report.
     *
     * @return error code report
     */
    public Path getErrorCodeReport() {
        return errorCodeReport;
    }

    /**
     * Get the name of the project the error code report was released in.
     *
     * @return name of the project the report was released in
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Get the version of the project that was released.
     *
     * @return project version
     */
    public String getProjectVersion() {
        return projectVersion;
    }
}
