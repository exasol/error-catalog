package com.exasol.errorcodecatalog.collector;

import static com.exasol.errorcodecatalog.collector.GithubTokenReader.readTokenFromEnv;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("integration")
class ErrorReportFinderIT {

    @Test
    void test() {
        final List<ReleaseReference> errorReports = new ErrorReportFinder(readTokenFromEnv()).findErrorReports();
        assertThat(errorReports, not(empty()));
    }
}