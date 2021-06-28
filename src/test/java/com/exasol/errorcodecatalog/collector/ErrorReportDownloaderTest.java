package com.exasol.errorcodecatalog.collector;

import org.junit.jupiter.api.Test;

class ErrorReportDownloaderTest {
    @Test
    void test() {
        final ReleaseReference release = new ReleaseReference("error-code-crawler-maven-plugin", "0.5.0",
                "https://github.com/exasol/error-code-crawler-maven-plugin/releases/download/0.5.0/error_code_report.json");
        new ErrorReportDownloader().downloadReportIfNotExists(release);
    }
}