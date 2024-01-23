package com.exasol.errorcodecatalog.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class finds error-code reports on the GitGub releases.
 */
class ErrorReportFinder {
    private static final Logger LOGGER = Logger.getLogger(ErrorReportFinder.class.getName());
    private final GraphQlClient graphQlClient;

    /**
     * Create a new instance of {@link ErrorReportFinder}.
     * 
     * @param githubToken GitHub token
     */
    ErrorReportFinder(final GithubToken githubToken) {
        this.graphQlClient = new GraphQlClient(githubToken);
    }

    /**
     * Find error-code reports on the GitGub releases.
     * 
     * @return error-code-report descriptions
     */
    List<ReleaseReference> findErrorReports() {
        final List<ReleaseReference> result = new ArrayList<>();
        int count = 0;
        final List<String> repos = graphQlClient.listExasolIntegrationRepos();
        for (final String repo : repos) {
            count++;
            final int finalCount = count;
            final List<ReleaseReference> releases = graphQlClient.getReleaseArtifact(repo);
            result.addAll(releases);
            LOGGER.info(() -> "Found " + releases.size() + " releases for '" + repo + "' (" + finalCount + "/"
                    + repos.size() + ")");
        }
        return result;
    }
}
