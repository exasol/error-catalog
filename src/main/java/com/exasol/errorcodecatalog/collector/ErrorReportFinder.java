package com.exasol.errorcodecatalog.collector;

import java.util.ArrayList;
import java.util.List;

/**
 * This class finds error-code reports on the GitGub releases.
 */
class ErrorReportFinder {
    private final GraphQlClient graphQlClient;

    /**
     * Create a new instance of {@link ErrorReportFinder}.
     * 
     * @param githubToken GitHub token
     */
    ErrorReportFinder(GithubToken githubToken) {
        this.graphQlClient = new GraphQlClient(githubToken);
    }

    /**
     * Find error-code reports on the GitGub releases.
     * 
     * @return error-code-report descriptions
     */
    List<ReleaseReference> findErrorReports() {
        final List<ReleaseReference> result = new ArrayList<>();
        for (String repo : graphQlClient.listExasolIntegrationRepos()) {
            result.addAll(graphQlClient.getReleaseArtifact(repo));
        }
        return result;
    }
}
