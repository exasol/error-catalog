# Exasol Error Catalog 0.2.0, released 2022-08-05

Code name: Source link

## Summary

Version 0.2.0 of the `exasol-error-catalog` adds a link to the sources in the error code detail view, sorts the projects in the overview list alphabetically and removes Lombok from the project.

We also added monitoring for the regular job that generates the error catalog.

## Features

* #12: Added monitoring for catalog generation job 
* #13: Added source code link
* #32: Sorted projects alphabetically

## Refactoring

* #26: Removed Lombok

## Bugfixes

* #35: Code detail page now only shows one code at a time

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-code-model-java:2.1.0` to `2.1.1`

### Test Dependency Updates

* Updated `nl.jqno.equalsverifier:equalsverifier:3.10` to `3.10.1`
* Updated `org.junit.jupiter:junit-jupiter-engine:5.8.2` to `5.9.0`
* Updated `org.junit.jupiter:junit-jupiter-params:5.8.2` to `5.9.0`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.4.6` to `2.5.0`
* Removed `org.projectlombok:lombok-maven-plugin:1.18.20.0`
