# Exasol Error Catalog 0.2.1, released 2023-??-??

Code name:

## Summary

## Features

* #46: Improved log output to see progress

## Bugfixes

* #37: Removed discontinued repository maven.exasol.com

## Refactoring

* #44: Replaced project specific notification webhook with org wide secret
* #43: Removed workaround for project-keeper problem

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-code-model-java:2.1.1` to `2.1.3`
* Updated `com.exasol:error-reporting-java:0.4.1` to `1.0.1`
* Updated `info.picocli:picocli:4.6.3` to `4.7.6`
* Added `jakarta.json:jakarta.json-api:2.1.3`

### Runtime Dependency Updates

* Added `org.eclipse.parsson:parsson:1.1.7`

### Test Dependency Updates

* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `3.17.2`
* Updated `org.hamcrest:hamcrest:2.2` to `3.0`
* Updated `org.junit.jupiter:junit-jupiter-engine:5.9.0` to `5.11.3`
* Updated `org.junit.jupiter:junit-jupiter-params:5.9.0` to `5.11.3`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.2`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `4.4.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.17`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.7.1`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.5.1`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.4.2`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.3.1`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.9.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.5.1`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.0.0` to `3.5.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.6.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.17.1`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `2.2.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `4.0.0.4121`
