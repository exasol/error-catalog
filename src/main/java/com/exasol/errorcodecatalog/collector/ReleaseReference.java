package com.exasol.errorcodecatalog.collector;

/**
 * This class describes a release with error-code-report.
 */
record ReleaseReference(String repository, String version, String errorReportUrl) {
}
