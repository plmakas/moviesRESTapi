package com.plmakas.movies.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class MoviesWireMockServer {

    private static final String MOVIE_DETAILS_1 = "{\n" +
            "\"id\": 1,\n" +
            "\"title\": \"Star Wars\",\n" +
            "\"description\": \"Star Wars description here\"\n" +
            "}";

    private static final String MOVIE_DETAILS_2 = "{\n" +
            "\"id\": 2,\n" +
            "\"title\": \"Shrek\",\n" +
            "\"description\": \"Shrek description here\"\n" +
            "}";

    private static final String MOVIE_DETAILS_3 = "{\n" +
            "\"id\": 3,\n" +
            "\"title\": \"Paw Patrol\",\n" +
            "\"description\": \"Paw Patrol description here\"\n" +
            "}";

    private static final String MOVIE_DETAILS_4 = "{\n" +
            "\"id\": 4,\n" +
            "\"title\": \"Ben10\",\n" +
            "\"description\": \"Ben10 description here\"\n" +
            "}";

    private static final String COMMENTS_1 = "[\n" +
            "  {\n" +
            "    \"movieid\": 1,\n" +
            "    \"username\": \"Movie_fan\",\n" +
            "    \"message\": \"Super movie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"movieid\": 1,\n" +
            "    \"username\": \"Movie_hater\",\n" +
            "    \"message\": \"Hate this movie\"\n" +
            "  }\n" +
            "]";

    private static final String COMMENTS_2 = "[\n" +
            "  {\n" +
            "    \"movieid\": 2,\n" +
            "    \"username\": \"Movie_fan\",\n" +
            "    \"message\": \"Great movie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"movieid\": 2,\n" +
            "    \"username\": \"Movie_hater\",\n" +
            "    \"message\": \"Bad movie\"\n" +
            "  }\n" +
            "]";

    private static final String COMMENTS_3 = "[\n" +
            "  {\n" +
            "    \"movieid\": 3,\n" +
            "    \"username\": \"Movie_fan\",\n" +
            "    \"message\": \"Love this movie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"movieid\": 3,\n" +
            "    \"username\": \"Movie_hater\",\n" +
            "    \"message\": \"Ugly movie\"\n" +
            "  }\n" +
            "]";

    private static final String COMMENTS_4 = "[\n" +
            "  {\n" +
            "    \"movieid\": 4,\n" +
            "    \"username\": \"Movie_fan\",\n" +
            "    \"message\": \"Nice movie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"movieid\": 4,\n" +
            "    \"username\": \"Movie_hater\",\n" +
            "    \"message\": \"Worst movie\"\n" +
            "  }\n" +
            "]";

    public static void runWireMock() {
        com.github.tomakehurst.wiremock.WireMockServer wireMockServer = new com.github.tomakehurst.wiremock.WireMockServer(options().port(8089)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/movies/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(5000) //to see cache working
                        .withHeader("Content-Type", "application/json")
                        .withBody(MOVIE_DETAILS_1)));

        wireMockServer.stubFor(get(urlEqualTo("/movies/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MOVIE_DETAILS_2)));

        wireMockServer.stubFor(get(urlEqualTo("/movies/3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MOVIE_DETAILS_3)));

        wireMockServer.stubFor(get(urlEqualTo("/movies/4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MOVIE_DETAILS_4)));



        wireMockServer.stubFor(get(urlEqualTo("/comments/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(COMMENTS_1)));

        wireMockServer.stubFor(get(urlEqualTo("/comments/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(COMMENTS_2)));

        wireMockServer.stubFor(get(urlEqualTo("/comments/3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(COMMENTS_3)));

        wireMockServer.stubFor(get(urlEqualTo("/comments/4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(COMMENTS_4)));
    }
}
