package com.exasol.errorcodecatalog.collector;

import lombok.Data;

/**
 * This class describes a release with error-code-report.
 */
@Data
final class ReleaseReference {
    private final String repository;
    private final String version;
    private final String errorReportUrl;
}
