package com.exasol.errorcodecatalog.renderer;

import java.util.*;
import java.util.stream.Collectors;

import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * A project is a group of error-code-reports that have the have the same project-name.
 */
class Project {
    private final String projectName;
    private final Map<ProjectVersion, ErrorCodeReport> reportsGroupedByVersion;
    private final List<ErrorCodeVersions> errorCodeVersions;

    /**
     * Create a new instance of {@link Project}.
     * 
     * @param projectName project name
     * @param reports     reports of this project
     */
    Project(final String projectName, final List<ErrorCodeReport> reports) {
        this.projectName = projectName;
        this.reportsGroupedByVersion = new HashMap<>();
        for (final ErrorCodeReport report : reports) {
            final ProjectVersion version = new ProjectVersion(report.getProjectVersion());
            this.reportsGroupedByVersion.put(version, report);
        }
        this.errorCodeVersions = generateErrorCodeVersions(reports);
    }

    static List<Project> groupByProject(final List<ErrorCodeReport> reports) {
        final Map<String, List<ErrorCodeReport>> reportsGroupedByProjectName = groupReportsByProjectName(reports);
        final List<Project> projects = new ArrayList<>(reportsGroupedByProjectName.size());
        for (final Map.Entry<String, List<ErrorCodeReport>> entry : reportsGroupedByProjectName.entrySet()) {
            projects.add(new Project(entry.getKey(), entry.getValue()));
        }
        return projects;
    }

    private static Map<String, List<ErrorCodeReport>> groupReportsByProjectName(
            final List<ErrorCodeReport> loadedReports) {
        final Map<String, List<ErrorCodeReport>> reportsGroupedByProjectName = new HashMap<>();
        for (final ErrorCodeReport loadedReport : loadedReports) {
            final String projectName = loadedReport.getProjectName();
            reportsGroupedByProjectName.putIfAbsent(projectName, new ArrayList<>());
            reportsGroupedByProjectName.get(projectName).add(loadedReport);
        }
        return reportsGroupedByProjectName;
    }

    private List<ErrorCodeVersions> generateErrorCodeVersions(final List<ErrorCodeReport> reports) {
        final var groupedByIdAndVersion = groupByIdAndVersion(reports);
        final List<ErrorCodeVersions> errorCodes = new ArrayList<>(groupedByIdAndVersion.size());
        for (final Map.Entry<String, Map<ProjectVersion, ErrorMessageDeclaration>> entry : groupedByIdAndVersion
                .entrySet()) {
            errorCodes.add(new ErrorCodeVersions(entry.getValue()));
        }
        return errorCodes;
    }

    private Map<String, Map<ProjectVersion, ErrorMessageDeclaration>> groupByIdAndVersion(
            final List<ErrorCodeReport> reports) {
        final Map<String, Map<ProjectVersion, ErrorMessageDeclaration>> codeGroupedByIdAndVersion = new HashMap<>();
        for (final ErrorCodeReport report : reports) {
            final ProjectVersion version = new ProjectVersion(report.getProjectVersion());
            for (final ErrorMessageDeclaration declaration : report.getErrorMessageDeclarations()) {
                final String id = declaration.getIdentifier();
                codeGroupedByIdAndVersion.putIfAbsent(id, new HashMap<>());
                codeGroupedByIdAndVersion.get(id).put(version, declaration);
            }
        }
        return codeGroupedByIdAndVersion;
    }

    String getProjectName() {
        return this.projectName;
    }

    String latestVersion() {
        final List<ProjectVersion> sortedVersions = getSortedVersions();
        return sortedVersions.get(sortedVersions.size() - 1).toString();
    }

    List<ErrorCodeVersions> getActiveErrorCodes() {
        final String latestVersion = latestVersion();
        return this.errorCodeVersions.stream().filter(code -> code.getLatestVersionNumber().equals(latestVersion))
                .collect(Collectors.toList());
    }

    List<ErrorCodeVersions> getDeprecatedErrorCodes() {
        final String latestVersion = latestVersion();
        return this.errorCodeVersions.stream().filter(code -> !code.getLatestVersionNumber().equals(latestVersion))
                .collect(Collectors.toList());
    }

    List<ErrorCodeVersions> getErrorCodes() {
        return this.errorCodeVersions;
    }

    private List<ProjectVersion> getSortedVersions() {
        final List<ProjectVersion> projectVersions = new ArrayList<>(this.reportsGroupedByVersion.keySet());
        Collections.sort(projectVersions);
        return projectVersions;
    }
}
