package com.exasol.errorcodecatalog.collector;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ErrorReportDownloaderTest {
    private static final GithubToken GITHUB_TOKEN = new GithubTokenReader().readTokenFromEnv();
    @Test
    void test(@TempDir final Path tempDir) {
        final ReleaseReference release = new ReleaseReference("error-code-crawler-maven-plugin", "0.5.0",
                "https://github.com/exasol/error-code-crawler-maven-plugin/releases/download/0.5.0/error_code_report.json");
        new ErrorReportDownloader(tempDir, GITHUB_TOKEN).downloadReportIfNotExists(release);
        assertTrue(Files.exists(tempDir.resolve(Path.of("error-code-crawler-maven-plugin", "0.5.0.json"))));
    }
}