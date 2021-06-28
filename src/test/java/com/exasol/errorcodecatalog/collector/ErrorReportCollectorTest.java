package com.exasol.errorcodecatalog.collector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;

class ErrorReportCollectorTest {
    @Test
    void test() throws IOException {
        final List<ReleasedErrorCodeReport> reports = new ErrorReportCollector().collectReports();
        final ReleasedErrorCodeReport errorCodeReport = reports.stream()
                .filter(report -> report.projectName().equals("error-code-crawler-maven-plugin")
                        && report.projectVersion().equals("0.5.0"))
                .findAny().orElseThrow();
        assertThat(Files.readString(errorCodeReport.errorCodeReport()),
                containsString("error-code-crawler-maven-plugin"));
    }
}