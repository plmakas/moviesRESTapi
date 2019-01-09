package com.plmakas.movies.service.details;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.plmakas.movies.model.MovieDetails;
import com.plmakas.movies.service.details.exception.MovieDetailsParsingExcpetion;
import com.plmakas.movies.service.details.exception.MovieDetailsResponseException;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MovieDetailsServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090);

    private MovieDetailsService movieDetailsService = new MovieDetailsService("http://127.0.0.1:8090");

    private final String CORRECT_RESOPNSE_JSON = "{\n" +
            "\"id\": 1,\n" +
            "\"title\": \"Star Wars\",\n" +
            "\"description\": \"Star Wars description here\"\n" +
            "}";
    private final String INVALID_RESPONSE = "not a json";

    @Test
    public void testCorrectResponse() {
        stubFor(get(urlEqualTo("/movies/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(CORRECT_RESOPNSE_JSON)));

        MovieDetails movieDetails = movieDetailsService.findMovieDetails(1).block();
        assertThat(movieDetails.getTitle()).isEqualTo("Star Wars");
    }

    @Test(expected = MovieDetailsResponseException.class)
    public void testCorrectExceptionWhenBackendServerError() {
        stubFor(get(urlEqualTo("/movies/1"))
                .willReturn(aResponse()
                        .withStatus(500)));

        movieDetailsService.findMovieDetails(1).block();
    }

    @Test(expected = MovieDetailsParsingExcpetion.class)
    public void testCorrectExceptionWhenInvalidResponseFromBackend() {
        stubFor(get(urlEqualTo("/movies/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(INVALID_RESPONSE)));

        movieDetailsService.findMovieDetails(1).block();
    }

    @Test
    public void testCachedResponseReturnedBySimulatingBackendServerError() {
        testCorrectResponse();

        stubFor(get(urlEqualTo("/movies/1"))
                .willReturn(aResponse()
                        .withStatus(500)));

        MovieDetails movieDetails = movieDetailsService.findMovieDetails(1).block();
        assertThat(movieDetails.getTitle()).isEqualTo("Star Wars");
    }

}

