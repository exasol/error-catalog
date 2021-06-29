package com.exasol.errorcodecatalog.renderer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

class ProjectTest {
    private static final ErrorMessageDeclaration ERROR_CODE_1 = ErrorMessageDeclaration.builder().identifier("E-TEST-1")
            .build();
    private static final ErrorMessageDeclaration ERROR_CODE_2 = ErrorMessageDeclaration.builder().identifier("E-TEST-2")
            .build();
    private static final ErrorCodeReport PROJECT_A = new ErrorCodeReport("a", "1.0.0",
            List.of(ERROR_CODE_1, ERROR_CODE_2));
    private static final ErrorCodeReport PROJECT_A_V2 = new ErrorCodeReport("a", "2.0.0", List.of(ERROR_CODE_1));
    private static final ErrorCodeReport PROJECT_B = new ErrorCodeReport("b", "1.0.0", Collections.emptyList());

    @Test
    void testGroupByProject() {
        final List<Project> projects = Project.groupByProject(List.of(PROJECT_A, PROJECT_A_V2, PROJECT_B));
        final List<String> projectNames = projects.stream().map(Project::getProjectName).collect(Collectors.toList());
        assertThat(projectNames, Matchers.containsInAnyOrder("a", "b"));
    }

    @Test
    void testLatestVersion() {
        final Project projectA = new Project("a", List.of(PROJECT_A, PROJECT_A_V2));
        assertThat(projectA.latestVersion(), equalTo("2.0.0"));
    }

    @Test
    void testGetActiveErrorCodes() {
        final Project projectA = new Project("a", List.of(PROJECT_A, PROJECT_A_V2));
        final List<String> identifiers = projectA.getActiveErrorCodes().stream().map(ErrorCodeVersions::getIdentifier)
                .collect(Collectors.toList());
        assertThat(identifiers, Matchers.contains(ERROR_CODE_1.getIdentifier()));
    }

    @Test
    void testGetDeprecatedErrorCodes() {
        final Project projectA = new Project("a", List.of(PROJECT_A, PROJECT_A_V2));
        final List<String> identifiers = projectA.getDeprecatedErrorCodes().stream()
                .map(ErrorCodeVersions::getIdentifier).collect(Collectors.toList());
        assertThat(identifiers, Matchers.contains(ERROR_CODE_2.getIdentifier()));
    }
}
