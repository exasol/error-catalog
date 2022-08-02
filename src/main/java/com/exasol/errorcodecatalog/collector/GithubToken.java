package com.exasol.errorcodecatalog.collector;

/**
 * Wrapper for GitHub tokens.
 */
public class GithubToken {
    private final String token;

    /**
     * Create a new instance of a {@link GithubToken}.
     *
     * @param token value representing the token
     */
    public GithubToken(final String token) {
        this.token = token;
    }

    /**
     * Get the token value.
     *
     * @return token value
     */
    public String getToken() {
        return token;
    }
}
