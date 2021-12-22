package com.exasol.errorcodecatalog.collector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;

import java.util.List;

import org.junit.jupiter.api.Test;

class GraphQlClientTest {
    private static final GithubToken GITHUB_TOKEN = new GithubTokenReader().readTokenFromEnv();

    @Test
    void testListRepos() {
        final List<String> result = new GraphQlClient(GITHUB_TOKEN).listExasolIntegrationRepos();
        assertThat(result, hasItem("error-catalog"));
    }

    @Test
    void testGetReleaseArtifact() {
        final List<ReleaseReference> result = new GraphQlClient(GITHUB_TOKEN)
                .getReleaseArtifact("s3-document-files-virtual-schema");
        final ReleaseReference s3Release = result.stream()
                .filter(release -> release.getRepository().equals("s3-document-files-virtual-schema")
                        && release.getVersion().equals("1.1.0"))
                .findAny().orElseThrow();
        assertThat(s3Release.getErrorReportUrl(), startsWith("https://objects.githubusercontent.com/"));
    }
}