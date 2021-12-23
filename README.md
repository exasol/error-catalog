# Exasol Error Catalog

[![Build Status](https://github.com/exasol/error-catalog/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/error-catalog/actions/workflows/ci-build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aerror-catalog&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aerror-catalog)

Exasol's "Error Catalog" (short "EC") is a project that collects individual error lists from project repositories and generates a catalog that can be viewed with a web browser.

**You can view it at the [exasol error-catalog web site](https://exasol.github.io/error-catalog/).**

This catalog contains the error codes, descriptions and mitigation information for all scanned projects.

## Local Usage

Usually there is no need to generate the catalog. Instead, you can simply use the [online version](https://exasol.github.io/error-catalog/).

You can use this tool to generate an HTML error code catalog:

* [Configure GitHub credentials / token](https://github-api.kohsuke.org/index.html#Environmental_variables)
* Run `mvn compile exec:java`

Now you can find the catalog in `target/catalog`.

## Information For Users

* [Changelog](doc/changes/changelog.md)

## Information For Developers

* [System Requirements](doc/system_requirements.md)
  [Design Document](doc/design.md)
* [Dependencies](dependencies.md)