package com.exasol.errorcodecatalog.collector;

import com.exasol.errorreporting.ExaError;

public class GithubTokenReader {
    public GithubToken readTokenFromEnv() {
        final String githubToken = System.getenv("GITHUB_TOKEN");
        if (githubToken == null || githubToken.isBlank()) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-EC-9").message("Missing required environment variable GITHUB_TOKEN.")
                            .mitigation("Please provide a github token.").toString());
        }
        return new GithubToken(githubToken);
    }
}
