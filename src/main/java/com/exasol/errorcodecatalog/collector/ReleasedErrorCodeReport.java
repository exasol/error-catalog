package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;

import lombok.Data;

/**
 * This record describes releases that contain an error-code-report.
 */
@Data
public final class ReleasedErrorCodeReport {
    private final Path errorCodeReport;
    private final String projectName;
    private final String projectVersion;
}
