package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;

class ErrorCodeVersionsTest {
    private static final ErrorMessageDeclaration ERROR_CODE = ErrorMessageDeclaration.builder().identifier("E-TEST-1")
            .build();

    @Test
    void testGetLatestVersion() {
        final var versions = Map.of(new ProjectVersion("2.0.0"), ERROR_CODE, new ProjectVersion("1.0.0"), ERROR_CODE);
        assertThat(new ErrorCodeVersions(versions).getLatestVersionNumber(), equalTo("2.0.0"));
    }
}