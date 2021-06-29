package com.exasol.errorcodecatalog.renderer;

import java.util.*;

import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * This class groups the reports of different versions of the same error-code.
 */
class ErrorCodeVersions {
    private final Map<ProjectVersion, ErrorMessageDeclaration> declarations;

    /**
     * Create a new instance of {@link ErrorCodeVersions}.
     * 
     * @param declarations error declarations of the same error code
     */
    ErrorCodeVersions(final Map<ProjectVersion, ErrorMessageDeclaration> declarations) {
        this.declarations = declarations;
    }

    /**
     * Get the identifier of the error-code.
     * 
     * @return identifier
     */
    String getIdentifier() {
        return this.declarations.values().iterator().next().getIdentifier();
    }

    /**
     * Get the latest version of the error-code.
     * 
     * @return latest project version that contained this error code
     */
    String getLatestVersionNumber() {
        final List<ProjectVersion> sortedVersions = getSortedVersions();
        return sortedVersions.get(sortedVersions.size() - 1).toString();
    }

    ErrorMessageDeclaration getVersion(final String versionNumber) {
        return this.declarations.get(new ProjectVersion(versionNumber));
    }

    public Map<ProjectVersion, ErrorMessageDeclaration> getDeclarations() {
        return this.declarations;
    }

    private List<ProjectVersion> getSortedVersions() {
        final List<ProjectVersion> projectVersions = new ArrayList<>(this.declarations.keySet());
        Collections.sort(projectVersions);
        return projectVersions;
    }
}
