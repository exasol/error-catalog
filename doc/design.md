# Design of the Error Catalog

This is the technical design document of the Error Catalog Project. The design fulfills the requirements we described in [system_requirements.md](system_requirements.md).

## Modules

The error catalog consists of the following main parts:

* Crawler: Collects error_code_report.json (JSON) files and stores them locally or in AWS, ideally in a folder with timestamp
* Parser: Processes the stored .json files and turns them into documentation
* Monitor: Reports if the process of assembling the central error code catalog fails

This documentation will then be transferred to a static web server somewhere.

Both will be implemented in java because of ease of use and setup and will be run using GitHub actions with options to run and debug both parts locally as well. All steps will run once nightly, in succession, resulting in updated documentation every day.

## The crawler aka 'collector'

`dsn~collecting-error-lists-from-github-projects~1`

The crawler uses the GitHub REST API to crawl the Exasol organization public repositories and find all 'error_code_report.json` files. An internal list will be made and these files will then in a following step be downloaded. We plan on checking the version tag(s) per repository and if these increase fetch the new JSON file(s), and store these with the version tag (and possibly a timestamp) appended at the end of the JSON filenames.

-> TEST suggestion; see for the whole organization and specify a minimum to compare against. -> TEST suggestion ; specify a collection -> see if we get the path to the JSON(s).

Covers:

* [`req~collecting-error-lists-from-github-projects~1`](system_requirements.md#collecting-error-lists-from-github-projects)

## The parser

First we considered a template engine but currently we're opting for a HTML generator as a 'middle ground' approach to generating the pages/content.

### Content Design Separation

`dsn~content-design-separation~1`

We split the CSS into a separate file.

Covers:

* [`req~content-design-separation~1`](system_requirements.md#content--design-separation)

### Content Design

`dsn~corporate-design~1`

After a brief discussion we currently opt to use a minimal CSS style that will be hosted in a separate CSS file as well as a small logo. We might switch to a subset of bootstrap or something similar if this is required later on.

Empiric results for error catalog of project-keeper-maven-plugin containing 69
entries as of Thu July, 7th 2022:

| Size HTML | Size raw text | Overhead | Item |
| --- | ---| --- | --- |
| 4324 | 824 | 81% | [complete catalogue](https://error-catalog.exasol.com/projects/project-keeper-maven-plugin.html) |
| 528 | 245 | 54% | [Single entry E-PK-24](https://error-catalog.exasol.com/error-codes/e-pk-24.html) |

Covers:

* [`req~corporate-design~1`](system_requirements.md#corporate-design)
* [`qs~page-overhead-limit~1`](system_requirements.md#page-overhead-limit)

### Usable without javascript

`dsn~usable-without-javascript~1`

We will not depend on any javascript for any of the pages' functionality.

Covers:

* [`req~usable-without-javascript~1`](system_requirements.md#usable-without-javascript)

### View With Any Browser

`dsn~view-with-any-browser~1`

The CSS will be adequately responsive so any browsers on any devices can view the content. The styles will be kept to an absolute minimum.

TODO: We will manually need to test this so a test requirement of some kind on multiple browsers?

Also look into providing readability for visually impaired or other users.

Covers:

* [`req~view-with-any-browser~1`](system_requirements.md#best-viewed-with-any-browser)

### Project Entry Point

`dsn~projects-entry-point~1`

We will generate an overview page that lists an overview of all the public projects that incorporate the error handling parsers (and generate JSON files). Then per project there will be a listing of all error messages of that project.
(One repository/project could be generated from multiple JSON files.)

Covers:

* [`req~projects-entry-point~1`](system_requirements.md#projects-entry-point)

### Error detail pages

`dsn~error-details~1`

There will be a generated page for every error message.

Covers:

* [`req~error-details~1`](system_requirements.md#error-details)
* [`req~finding-an-entry-by-code~1`](system_requirements.md#finding-an-entry-by-code)

### Catalog Entry Origin Details

`dsn~catalog-entry-origin-details~1`

This will be tricky to accomplish since we only have locally generated .json files per project 'since the local crawler implementation'. So our history will only start from that point onwards. As it is now we plan to just store files with an incremental version based on the tag. The date is not really useful. So for a next step, after we succesfully generate the documentation we'll look into introducing the 'added on' and 'deprecated / removed' on so and so versions in the generated documentation.

Covers:

* [`req~catalog-entry-origin-details~1`](system_requirements.md#catalog-entry-origin-details)

### Finding entries by error code

`dsn~finding-an-entry-by-code~1`

Name pages as error codes.

#### Next step, search:

A next step for a followup release will be to add a search engine, maybe full text search. Some custom code/logic to analyse (and separate) the error messages and navigate to the right page immediately should also be looked into.

Covers:

* [`req~finding-an-entry-by-code~1`](system_requirements.md#finding-an-entry-by-code)

## Monitor
`dsn~monitoring-the-catalog-generation~1`

The monitor is a GitHub action that posts a notification to the owner of the error catalog in case of any failure.

Covers:

* [`req~monitoring-the-catalog-generation~1`](system_requirements.md#monitoring-the-catalog-generation)
