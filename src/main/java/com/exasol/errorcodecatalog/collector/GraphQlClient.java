package com.exasol.errorcodecatalog.collector;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import javax.json.*;

import com.exasol.errorreporting.ExaError;

/**
 * Client for queries to the GitHub GraphQl API.
 */
public class GraphQlClient {
    private static final String EDGES = "edges";
    private static final String ERRORS = "errors";
    private final int pageSize;
    private final GithubToken githubToken;
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10)).build();

    /**
     * Create a new instance of {@link GraphQlClient}.
     *
     * @param githubToken GitHub token
     */
    public GraphQlClient(final GithubToken githubToken) {
        this(githubToken, 100);
    }

    /**
     * Create a new instance of {@link GraphQlClient}.
     *
     * @param githubToken GitHub token
     * @param pageSize    page size of the requests
     */
    GraphQlClient(final GithubToken githubToken, final int pageSize) {
        this.githubToken = githubToken;
        this.pageSize = pageSize;
    }

    private static String quote(final String nextPage) {
        if (nextPage.contains("\"")) {
            throw new IllegalStateException(ExaError.messageBuilder("F-EC-15")
                    .message("String contained illegal char \".").ticketMitigation().toString());
        }
        return "\"" + nextPage + "\"";
    }

    /**
     * List all public repos of the Exasol integration team.
     * 
     * @return repository names
     */
    public List<String> listExasolIntegrationRepos() {
        final List<String> result = new ArrayList<>();
        final Paginator paginator = new Paginator();
        while (paginator.hasNextPage()) {
            final String queryTemplate = getResourceAsString("listIntegrationReposQuery.gql");
            final String query = queryTemplate.replace("$cursor$", paginator.nextCursor()).replace("$pageSize$",
                    String.valueOf(this.pageSize));
            final JsonObject response = runGraphQlQuery(query);
            try {
                final JsonObject search = response.getJsonObject("search");
                paginator.update(search.getJsonObject("pageInfo"));
                result.addAll(readReposFromPage(search));
            } catch (final NullPointerException exception) {
                throw new IllegalStateException(
                        ExaError.messageBuilder("E-EC-16").message("Failed to parse GitHub response.").toString(),
                        exception);
            }
        }
        return result;
    }

    private List<String> readReposFromPage(final JsonObject search) {
        final List<String> result = new ArrayList<>();
        for (final JsonValue edge : search.getJsonArray(EDGES)) {
            final JsonObject repo = edge.asJsonObject().getJsonObject("node");
            if (!repo.getBoolean("isPrivate")) {
                final String name = repo.getString("name");
                result.add(name);
            }
        }
        return result;
    }

    /**
     * Get the error code reports for all releases of a repository.
     * 
     * @param repository repository name in the Exasol organisation
     * @return released reports
     */
    public List<ReleaseReference> getReleaseArtifact(final String repository) {
        final List<ReleaseReference> result = new ArrayList<>();
        final Paginator paginator = new Paginator();
        while (paginator.hasNextPage()) {
            final String query = getReleaseArtifactQuery(repository, paginator);
            final JsonObject response = runGraphQlQuery(query);
            try {
                final JsonObject releases = response.getJsonObject("repository").getJsonObject("releases");
                paginator.update(releases.getJsonObject("pageInfo"));
                result.addAll(readReleasesPage(repository, releases));
            } catch (final NullPointerException | ClassCastException exception) {
                throw new IllegalStateException(
                        ExaError.messageBuilder("E-EC-14").message("Failed to parse GitHub response.").toString(),
                        exception);
            }
        }
        return result;
    }

    private String getReleaseArtifactQuery(final String repository, final Paginator paginator) {
        final String queryTemplate = getResourceAsString("releaseArtifactsQuery.gql");
        return queryTemplate.replace("$cursor$", paginator.nextCursor()).replace("$project$", quote(repository))
                .replace("$pageSize$", String.valueOf(this.pageSize));
    }

    private List<ReleaseReference> readReleasesPage(final String repository, final JsonObject releases) {
        final List<ReleaseReference> result = new ArrayList<>();
        for (final JsonValue edge : releases.getJsonArray(EDGES)) {
            final JsonObject release = edge.asJsonObject().getJsonObject("node");
            if (!release.getBoolean("isDraft")) {
                final JsonArray assetEdges = release.getJsonObject("releaseAssets").getJsonArray(EDGES);
                if (!assetEdges.isEmpty()) {
                    final String url = assetEdges.get(0).asJsonObject().getJsonObject("node").getString("url");
                    final String tag = release.getJsonObject("tag").getString("name");
                    result.add(new ReleaseReference(repository, tag, url));
                }
            }
        }
        return result;
    }

    private String getResourceAsString(final String resourceName) {
        try (final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8);
        } catch (final IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-EC-10")
                    .message("Failed to get resource {{resource}}.", resourceName).ticketMitigation().toString(),
                    exception);
        }
    }

    /**
     * Run a graphql query.
     * 
     * @param query query
     * @return result as JSON
     */
    JsonObject runGraphQlQuery(final String query) {
        try {
            final byte[] requestBody = createRequestBody(query);
            final HttpRequest request = createHttpRequest(requestBody);
            final HttpResponse<InputStream> response = this.httpClient.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            final JsonObject responseJson = parseResponse(response);
            if (responseJson.containsKey(ERRORS) && !responseJson.isNull(ERRORS)) {
                final String errorMessage = new String(toJson(responseJson.get(ERRORS)), StandardCharsets.UTF_8);
                throw new IllegalStateException(ExaError.messageBuilder("E-EC-17")
                        .message("Graphql query returned error: {{error}}.", errorMessage).toString());
            }
            return responseJson.getJsonObject("data");
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-11").message("Failed to run graphql query.").toString(), exception);
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-12").message("Interrupted while running graphql query.").toString(),
                    exception);
        }
    }

    private HttpRequest createHttpRequest(final byte[] requestBody) {
        final HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofByteArray(requestBody);
        return HttpRequest.newBuilder().POST(publisher).uri(URI.create("https://api.github.com/graphql"))
                .setHeader("User-Agent", "error catalog")
                .setHeader("Authorization", "bearer " + this.githubToken.getToken()).build();
    }

    private byte[] createRequestBody(final String query) {
        final JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
        requestBuilder.add("query", query);
        final JsonObject requestJson = requestBuilder.build();
        return toJson(requestJson);
    }

    private JsonObject parseResponse(final HttpResponse<InputStream> response) throws IOException {
        try (final InputStream inputStream = response.body();
                final JsonReader jsonReader = Json.createReader(inputStream)) {
            return jsonReader.readObject();
        }
    }

    private byte[] toJson(final JsonValue requestJson) {
        try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            try (final JsonWriter writer = Json.createWriter(buffer)) {
                writer.write(requestJson);
            }
            return buffer.toByteArray();
        } catch (final IOException exception) {
            throw new UncheckedIOException(
                    ExaError.messageBuilder("F-EC-13").message("Failed to serialize to JSON.").toString(),
                    exception);
        }
    }

    private static class Paginator {
        private boolean morePages = true;
        private String nextPage = null;

        public boolean hasNextPage() {
            return morePages;
        }

        private void update(final JsonObject pageInfo) {
            this.morePages = pageInfo.getBoolean("hasNextPage");
            if (this.morePages) {
                this.nextPage = pageInfo.getString("endCursor");
            }
        }

        private String nextCursor() {
            if (this.nextPage == null) {
                return "null";
            } else {
                return quote(this.nextPage);
            }
        }
    }
}
