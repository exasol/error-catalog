package com.exasol.errorcodecatalog.collector;

import java.nio.file.Path;

/**
 * This record describes releases that contain an error-code-report.
 */
public record ReleasedErrorCodeReport(Path errorCodeReport, String projectName, String projectVersion) {
}
