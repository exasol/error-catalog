# Exasol Error Catalog

Exasol's "Error Catalog" (short "EC") is a project that collects individual error lists from project repositories and generates a catalog that can be viewed with a web browser.

This catalog contains the error codes, descriptions and mitigation information for all scanned projects.

## Local Usage

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