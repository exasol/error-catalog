package com.exasol.errorcodecatalog.collector;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import javax.json.*;

import com.exasol.errorreporting.ExaError;

import lombok.Getter;

/**
 * Client for queries to the GitHub GraphQl API.
 */
public class GraphQlClient {
    private static final String EDGES = "edges";
    private final GithubToken githubToken;
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10)).build();

    /**
     * Create a new instance of {@link GraphQlClient}.
     * 
     * @param githubToken GitHub token
     */
    public GraphQlClient(GithubToken githubToken) {
        this.githubToken = githubToken;
    }

    private static String quote(String nextPage) {
        if (nextPage.contains("\"")) {
            throw new IllegalStateException(ExaError.messageBuilder("F-EC-15")
                    .message("String contained illegal char \".").ticketMitigation().toString());
        }
        return "\"" + nextPage + "\"";
    }

    public List<String> listRepos() {
        final List<String> result = new ArrayList<>();
        Paginator paginator = new Paginator();
        while (paginator.isHasNextPage()) {
            final String queryTemplate = getResourceAsString("listIntegrationReposQuery.gql");
            final String query = queryTemplate.replace("$cursor$", paginator.nextCursor());
            final JsonObject response = runGraphQlQuery(query);
            try {
                final JsonObject search = response.getJsonObject("search");
                paginator.update(search.getJsonObject("pageInfo"));
                result.addAll(readReposFromPage(search));
            } catch (NullPointerException exception) {
                throw new IllegalStateException(
                        ExaError.messageBuilder("E-EC-16").message("Failed to parse GitHub response.").toString(),
                        exception);
            }
        }
        return result;
    }

    private List<String> readReposFromPage(JsonObject search) {
        final List<String> result = new ArrayList<>();
        for (JsonValue edge : search.getJsonArray(EDGES)) {
            final JsonObject repo = edge.asJsonObject().getJsonObject("node");
            if (!repo.getBoolean("isPrivate")) {
                String name = repo.getString("name");
                result.add(name);
            }
        }
        return result;
    }

    public List<ReleaseReference> getReleaseArtifact(String repository) {
        final List<ReleaseReference> result = new ArrayList<>();
        final Paginator paginator = new Paginator();
        while (paginator.isHasNextPage()) {
            final String query = getReleaseArtifactQuery(repository, paginator);
            final JsonObject response = runGraphQlQuery(query);
            try {
                final JsonObject releases = response.getJsonObject("repository").getJsonObject("releases");
                paginator.update(releases.getJsonObject("pageInfo"));
                result.addAll(readReleasesPage(repository, releases));
            } catch (NullPointerException | ClassCastException exception) {
                throw new IllegalStateException(
                        ExaError.messageBuilder("E-EC-14").message("Failed to parse GitHub response.").toString(),
                        exception);
            }
        }
        return result;
    }

    private String getReleaseArtifactQuery(String repository, Paginator paginator) {
        final String queryTemplate = getResourceAsString("releaseArtifactsQuery.gql");
        return queryTemplate.replace("$cursor$", paginator.nextCursor()).replace("$project$", quote(repository));
    }

    private List<ReleaseReference> readReleasesPage(String repository, JsonObject releases) {
        final List<ReleaseReference> result = new ArrayList<>();
        for (JsonValue edge : releases.getJsonArray(EDGES)) {
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

    private String getResourceAsString(String resourceName) {
        try (final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-EC-10")
                    .message("Failed to get resource {{resource}}.", resourceName).ticketMitigation().toString(),
                    exception);
        }
    }

    private JsonObject runGraphQlQuery(String query) {
        try {
            final byte[] requestBody = createRequestBody(query);
            HttpRequest request = createHttpRequest(requestBody);
            final HttpResponse<InputStream> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            return parseResponse(response);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-11").message("Failed to run graphql query.").toString(), exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-EC-12").message("Interrupted while running graphql query.").toString(),
                    exception);
        }
    }

    private HttpRequest createHttpRequest(byte[] requestBody) {
        final HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofByteArray(requestBody);
        return HttpRequest.newBuilder().POST(publisher).uri(URI.create("https://api.github.com/graphql"))
                .setHeader("User-Agent", "error catalog").setHeader("Authorization", "bearer " + githubToken.getToken())
                .build();
    }

    private byte[] createRequestBody(String query) {
        final JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
        requestBuilder.add("query", query);
        final JsonObject requestJson = requestBuilder.build();
        return toJson(requestJson);
    }

    private JsonObject parseResponse(HttpResponse<InputStream> response) throws IOException {
        try (final InputStream inputStream = response.body();
                final JsonReader jsonReader = Json.createReader(inputStream)) {
            final JsonObject responseJson = jsonReader.readObject();
            return responseJson.getJsonObject("data");
        }
    }

    private byte[] toJson(JsonObject requestJson) {
        try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            try (final JsonWriter writer = Json.createWriter(buffer)) {
                writer.write(requestJson);
            }
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(
                    ExaError.messageBuilder("F-EC-13").message("Exception while serializing to JSON.").toString(),
                    exception);
        }
    }

    private static class Paginator {
        @Getter
        private boolean hasNextPage = true;
        private String nextPage = null;

        private void update(JsonObject pageInfo) {
            hasNextPage = pageInfo.getBoolean("hasNextPage");
            if (hasNextPage) {
                nextPage = pageInfo.getString("endCursor");
            }
        }

        private String nextCursor() {
            if (nextPage == null) {
                return "null";
            } else {
                return quote(nextPage);
            }
        }
    }
}
