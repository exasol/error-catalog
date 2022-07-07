# System Requirement Specification Exasol Test Container

## Introduction

Exasol's "Error Catalog" (short "EC") is a project that collects individual error lists from project repositories and generates a catalog that can be viewed with a web browser.

This catalog contains the error codes, descriptions and mitigation information for all scanned projects.

## About This Document

### Target Audience

The target audience are end-users, requirement engineers, software engineers and quality assurance. See section ["Stakeholders"](#stakeholders) for more details.

### Goal

The goal of Exasol's error catalog is to provide a central point where end users find information about errors Exasol software reports, what those errors mean in detail and how to solve the underlying problem.

### Quality Goals

EC's main quality goals are in descending order of importance:

1. User experience
1. Project coverage
1. Scalability

The main quality metric is how well the error catalog is received by the end users. How fast do they find what they need? Is it easy to get a mitigation? Performance also plays an important role for user experience. When users are searching the error catalog, they already have one thing to worry about and that is getting rid of the error. The system must be fast in order to be helpful.

Project coverage is the next important factor. The more projects we cover, the more valuable the error catalog becomes.

Finally the catalog must be able to grow with the covered projects without noticeable performance degradation.

### User Experience

`qg~user-experience~1`

Users find the error catalog helpful and convenient.

Comment:

Since "helpful" and "convenient" are in their nature subjective, we have to measure reaching this goal by conducting either a UX test or letting the users play with the error catalog and poll the experience.

Rationale:

Having to deal with errors is annoying. So the process of finding a solution must be as smooth and fast as possible to improve overall user experience of the product.

Needs: qs

## Stakeholders

### End Users

End users of EC are Exasol users who want to look up the meaning of errors they encounter and what they can do about them.

### Project Managers and Requirement Engineers

Project Managers and Requirement Engineers serve as the voice of the end users. They investigate the need of the end users and formulate the high-level user requirements.

### Software Engineers

The people writing the actual software need the user requirements as basis for the software design.

### Quality Engineers

Quality Engineers validate the process and the outcomes from user requirements to the end product.

## Terms and Abbreviations

The following list gives you an overview of terms and abbreviations commonly used in EC documents.

* Cause: The reason for the error.
* Error code: A well-formatted alphanumeric code with separators where each code uniquely identifies an error.
* Error message: The message that the application throwing the error displays to the user or writes into a log.
* Mitigation: A description of a way to fix the problem behind the error.
* Root Cause: In a chain of problems, the one that was the reason for all the others.

## Features

Features are the highest level requirements in this document that describe the main functionality of EC.

### Error Catalog Collection

`feat~error-catalog-collection~1`

The error catalog scans Exasol software projects for error lists and combines the individual lists into one.

Rationale:

The main benefit over individual list is that users have a central entry point for navigating error information.

Needs: req

### Error Catalog Presentation

`feat~error-catalog-presentation~1`

The error catalog presents the combined error in a central location viewable with any web browser.

Rationale:

This provides the minimum barrier since we can assume that every user has access to at least a web browser.

Needs: req

## Functional Requirements

In this section lists functional requirements from the user's perspective. The requirements are grouped by feature where they belong to a single feature.

### Error Catalog Collection

#### Collecting Error Lists from GitHub Projects

`req~collecting-error-lists-from-github-projects~1`

A crawler collects the error details from all our GitHub repositories daily.

Rationale:

We want to create the catalog completely automatic. No manual work involved.

Covers:

* [`feat~error-catalog-collection~1`](#error-catalog-collection)

Needs: dsn

### Error Catalog Presentation

#### Content / Design Separation

`req~content-design-separation~1`

Content and design of the error catalog are separated.

Rationale:

Corporate designs change independently of the content.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Corporate Design

`req~corporate-design~1`

The error catalog design matches our corporate design.

Rationale:

It looks more professional if the style is consistent.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Best Viewed With Any Browser

`req~view-with-any-browser~1`

Users can view the error catalog with any browser.

Rationale:

Portability and accessibility. Even a pure text browser should be enough, so that blind people can use a Braille interface.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Usable Without JavaScript

`req~usable-without-javascript~1`

The error catalog can be used with JavaScript disabled.

Rationale:
Some people disable JS. Some browsers don't offer JavaScript. At least the non-interactive part of the catalog must work as well.

This also allows showing excerpts inline in client tools that have a simple embedded browser.

Very useful in SQL clients for example!

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Project Repository Link in Catalog

Each error catalog item contains a link to the project repository.

Rationale:

This allows users to check the user guides and FAQs.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Catalog Entry Origin Details

`req~catalog-entry-origin-details~1`

Error catalog entries carry introduction date, project and version.

Rationale:

This helps finding out with which version of a software an error could be raised first.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Error Details

`req~error-details~1`

Error catalog item contains: code, description, zero or more causes and zero or more mititations

Rationale:

Code and description are self-explaining, cause information and mitigation are what make the details valuable for users.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Projects Entry point

`req~projects-entry-point~1`

The catalog has an entry point where all projects and their short tags are listed together with a link to the corresponding error list.

Rationale:

This allows navigating to errors by "topic" (i.e. project).

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

#### Finding an Entry by Code

`req~finding-an-entry-by-code~1`

Users can find error details by error code.

Rationale:

This is the most common way for quickly finding a solution.

Covers:

* [`feat~error-catalog-presentation~1`](#error-catalog-presentation)

Needs: dsn

## Non-functional Requirements

### User Experience

#### Page Overhead Limit

`qs~page-overhead-limit~1`

The pages in the catalog on average have no more than 50% overhead compared to raw text of the error elements.

Rationale:

We want the pages to load fast and be embeddable.

Covers:

* [`qg~user-experience~1`](#user-experience)

Needs: dsn