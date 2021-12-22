package com.exasol.errorcodecatalog.collector;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import com.exasol.errorreporting.ExaError;

/**
 * This class downloads the error-code reports from the github releases.
 */
class ErrorReportDownloader {
    private final Path localRepo;

    /**
     * Create a new instance of {@link ErrorReportDownloader}.
     *
     * @param localRepo target directory for the error-code report files.
     */
    ErrorReportDownloader(final Path localRepo) {
        this.localRepo = localRepo;
        createLocalRepoIfNotExists();
    }

    private void createLocalRepoIfNotExists() {
        if (!Files.exists(this.localRepo)) {
            try {
                Files.createDirectories(this.localRepo);
            } catch (final IOException exception) {
                throw new IllegalStateException(
                        ExaError.messageBuilder("E-EC-2")
                                .message("Failed to create directory for storing error-code-reports.").toString(),
                        exception);
            }
        }
    }

    /**
     * Download the error-code-report of a given release to a local cache.
     * <p>
     * If the local cache already contains the report of the given release, this method returns the existing report.
     * </p>
     * 
     * @param release release
     * @return path of the local the error-code-report file
     */
    Path downloadReportIfNotExists(final ReleaseReference release) {
        try {
            return downloadReportIfNotExistsInternal(release);
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-3").message("Failed download error-code report.").toString(),
                    exception);
        }
    }

    private Path downloadReportIfNotExistsInternal(final ReleaseReference release) throws IOException {
        final Path repoPath = this.localRepo.resolve(release.getRepository());
        if (!Files.exists(repoPath)) {
            Files.createDirectories(repoPath);
        }
        final Path reportPath = repoPath.resolve(release.getVersion() + ".json");
        if (!Files.exists(reportPath)) {
            downloadReport(release, reportPath);
        }
        return reportPath;
    }

    private void downloadReport(final ReleaseReference release, final Path reportPath) throws IOException {
        final URL reportUrl = new URL(release.getErrorReportUrl());
        try (final InputStream inputStream = reportUrl.openStream();
                final ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
                final FileOutputStream fileOutputStream = new FileOutputStream(reportPath.toFile())) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }
}
