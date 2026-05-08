# Exasol Error Catalog 0.2.1, released 2026-05-08

Code name: Add timestamp to generated HTML pages

## Summary

This release adds timestamps to generated HTML pages.

## Features

* #46: Improved log output to see progress 
* #52: Generated HTML pages should contain generation timestamp

## Bugfixes

* #37: Removed discontinued repository maven.exasol.com

## Refactoring

* #44: Replaced project specific notification webhook with org wide secret
* #43: Removed workaround for project-keeper problem

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-code-model-java:2.1.1` to `2.1.4`
* Updated `com.exasol:error-reporting-java:0.4.1` to `1.0.2`
* Updated `info.picocli:picocli:4.6.3` to `4.7.7`
* Added `jakarta.json:jakarta.json-api:2.1.3`

### Runtime Dependency Updates

* Added `org.eclipse.parsson:parsson:1.1.7`

### Test Dependency Updates

* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `4.5`
* Updated `org.hamcrest:hamcrest:2.2` to `3.0`
* Added `org.junit.jupiter:junit-jupiter-api:6.0.3`
* Removed `org.junit.jupiter:junit-jupiter-engine:5.9.0`
* Updated `org.junit.jupiter:junit-jupiter-params:5.9.0` to `6.0.3`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.4`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `2.0.7`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `5.6.1`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.1`
* Added `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0`
* Removed `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-artifact-plugin:3.6.1`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.8.0`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.2.0` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.15.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.6.2`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.5.5`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.2` to `3.1.4`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-resources-plugin:3.3.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.12.1` to `3.21.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.5.5`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.0.0` to `3.6.3`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.7.3`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.21.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `2.3.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.14`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `5.5.0.6356`
