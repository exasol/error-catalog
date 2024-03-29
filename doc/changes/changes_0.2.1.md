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
* Updated `info.picocli:picocli:4.6.3` to `4.7.5`
* Added `jakarta.json:jakarta.json-api:2.1.3`

### Runtime Dependency Updates

* Added `org.eclipse.parsson:parsson:1.1.5`

### Test Dependency Updates

* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `3.15.6`
* Updated `org.junit.jupiter:junit-jupiter-engine:5.9.0` to `5.10.1`
* Updated `org.junit.jupiter:junit-jupiter-params:5.9.0` to `5.10.1`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.2`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `3.0.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.2.3`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.0.0` to `3.1.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.16.2`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `1.6.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`
