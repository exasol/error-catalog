package com.exasol.errorcodecatalog.collector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ErrorReportCollectorTest {
    @Test
    void test(@TempDir final Path tempDir) throws IOException {
        final List<ReleasedErrorCodeReport> reports = new ErrorReportCollector(tempDir).collectReports();
        final ReleasedErrorCodeReport errorCodeReport = reports.stream()
                .filter(report -> report.getProjectName().equals("error-code-crawler-maven-plugin")
                        && report.getProjectVersion().equals("0.5.0"))
                .findAny().orElseThrow();
        assertThat(Files.readString(errorCodeReport.getErrorCodeReport()),
                containsString("error-code-crawler-maven-plugin"));
    }
}