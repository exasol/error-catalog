package com.exasol.errorcodecatalog.collector;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import org.junit.jupiter.api.Test;

class ErrorReportFinderTest {
    private static final GithubToken GITHUB_TOKEN = new GithubTokenReader().readTokenFromEnv();
    @Test
    void test() {
        final List<ReleaseReference> errorReports = new ErrorReportFinder(GITHUB_TOKEN).findErrorReports();
        assertThat(errorReports, not(empty()));
    }
}