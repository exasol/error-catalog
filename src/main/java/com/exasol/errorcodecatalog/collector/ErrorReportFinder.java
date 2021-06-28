package com.exasol.errorcodecatalog.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.*;

import com.exasol.errorreporting.ExaError;

/**
 * This class finds error-code reports on the GitGub releases.
 */
class ErrorReportFinder {
    /**
     * Find error-code reports on the GitGub releases.
     * 
     * @return error-code-report descriptions
     */
    List<ReleaseReference> findErrorReports() {
        try {
            return getIntegrationTeamsReleases();
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-1").message("Failed to list releases.").toString(), exception);
        }
    }

    private List<ReleaseReference> getIntegrationTeamsReleases() throws IOException {
        final List<ReleaseReference> result = new ArrayList<>();
        final GitHub github = GitHub.connect();
        final PagedSearchIterable<GHRepository> repos = github.searchRepositories().topic("exasol-integration")
                .q("org:exasol").list();
        for (final GHRepository repository : repos) {
            final String repositoryName = repository.getName();
            for (final GHRelease release : repository.listReleases()) {
                for (final GHAsset asset : release.listAssets()) {
                    if (asset.getName().equals("error_code_report.json"))
                        result.add(new ReleaseReference(repositoryName, release.getTagName(),
                                asset.getBrowserDownloadUrl()));
                }
            }
        }
        return result;
    }
}
