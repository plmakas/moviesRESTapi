package com.plmakas.movies.service.comments;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.plmakas.movies.model.MovieComment;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MoviesCommentsServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090);

    private MovieCommentsService movieCommentsService = new MovieCommentsService("http://127.0.0.1:8090");

    private final String CORRECT_RESOPNSE_JSON_WITH_2_COMMENTS = "[\n" +
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

    private final String INVALID_RESPONSE = "not a json";

    @Test
    public void testCorrectResponse() {
        stubFor(get(urlEqualTo("/comments/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(CORRECT_RESOPNSE_JSON_WITH_2_COMMENTS)));

        List<MovieComment> comments = movieCommentsService.findMovieComments(1).block();
        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    public void testEmptyListReturnedWhenBackendServerError() {
        stubFor(get(urlEqualTo("/comments/1"))
                .willReturn(aResponse()
                        .withStatus(500)));

        List<MovieComment> comments = movieCommentsService.findMovieComments(1).block();
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    public void testEmptyListReturnedWhenInvalidResponseFromBackend() {
        stubFor(get(urlEqualTo("/comments/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(INVALID_RESPONSE)));

        List<MovieComment> comments = movieCommentsService.findMovieComments(1).block();
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    public void testCachedCommentsReturnedWhenBackendServerError() {
        testCorrectResponse();

        stubFor(get(urlEqualTo("/comments/1"))
                .willReturn(aResponse()
                        .withStatus(500)));

        List<MovieComment> comments = movieCommentsService.findMovieComments(1).block();
        assertThat(comments.size()).isEqualTo(2);
    }


}
