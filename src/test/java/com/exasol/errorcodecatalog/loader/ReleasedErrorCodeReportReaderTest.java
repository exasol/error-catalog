package com.exasol.errorcodecatalog.loader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.exasol.errorcodecatalog.collector.ReleasedErrorCodeReport;
import com.exsol.errorcodemodel.*;

class ReleasedErrorCodeReportReaderTest {

    @Test
    void testReadReportWithNameAndVersion(@TempDir Path tempDir) throws ErrorCodeReportReader.ReadException {
        final ErrorCodeReport reportWithInfo = new ErrorCodeReport("my-project", "1.0.0", Collections.emptyList());
        final Path reportAsFile = tempDir.resolve("reportWithInfo.json");
        new ErrorCodeReportWriter().writeReport(reportWithInfo, reportAsFile);
        final ErrorCodeReport report = new ReleasedErrorCodeReportReader()
                .readReport(new ReleasedErrorCodeReport(reportAsFile, "a", "0.0.0"));
        assertAll(//
                () -> assertThat(report.getProjectName(), Matchers.equalTo("my-project")),
                () -> assertThat(report.getProjectVersion(), Matchers.equalTo("1.0.0"))//
        );
    }

    @Test
    void testReadReportWithoutNameAndVersion(@TempDir Path tempDir)
            throws ErrorCodeReportReader.ReadException, IOException {
        final Path reportAsFile = tempDir.resolve("reportWithInfo.json");
        Files.writeString(reportAsFile,
                "{\"$schema\":\"https://schemas.exasol.com/error_code_report-1.0.0.json\", \"errorCodes\": [] }");
        final ErrorCodeReport report = new ReleasedErrorCodeReportReader()
                .readReport(new ReleasedErrorCodeReport(reportAsFile, "a", "0.0.0"));
        assertAll(//
                () -> assertThat(report.getProjectName(), Matchers.equalTo("a")),
                () -> assertThat(report.getProjectVersion(), Matchers.equalTo("0.0.0"))//
        );
    }
}