# Exasol Error Catalog 0.1.0, released 2022-07-08

Code name: Initial release

## Summary

Updated project structure incl. project-keeper 2.4.6 fixing broken links
checker.  Fixed requirements tracing issues reported by openfasttrace.

## Features

* #5: Added initial implementation
* #7: Added GitHub pages deployment
* #8: Added project pages
* #9: Added fonts locally
* #18: Added support for error-code-report 1.0.0 syntax

## Bug Fixes

* #20: Fixed catalog creation for reports with no mitigations
* #22: Updated broken links checker

## Dependency Updates

### Compile Dependency Updates

* Added `com.exasol:error-code-model-java:2.1.0`
* Added `com.exasol:error-reporting-java:0.4.1`
* Added `com.j2html:j2html:1.6.0`
* Added `info.picocli:picocli:4.6.3`

### Test Dependency Updates

* Added `nl.jqno.equalsverifier:equalsverifier:3.10`
* Added `org.hamcrest:hamcrest:2.2`
* Added `org.junit.jupiter:junit-jupiter-engine:5.8.2`
* Added `org.junit.jupiter:junit-jupiter-params:5.8.2`

### Plugin Dependency Updates

* Added `com.exasol:artifact-reference-checker-maven-plugin:0.4.0`
* Added `com.exasol:error-code-crawler-maven-plugin:1.1.1`
* Added `com.exasol:project-keeper-maven-plugin:2.4.6`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-assembly-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.10.1`
* Added `org.apache.maven.plugins:maven-deploy-plugin:2.7`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:3.2.2`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5`
* Added `org.codehaus.mojo:exec-maven-plugin:3.0.0`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.codehaus.mojo:versions-maven-plugin:2.10.0`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.5.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.projectlombok:lombok-maven-plugin:1.18.20.0`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`
