package com.plmakas.movies;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigurationTest {

    @LocalServerPort
    String port;

    private final String EMPTY_JSON = "{}";

    @Test
    public void testPostMovieDetailsForbidden() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/movies")
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("moviefan_user" + ":" + "password").getBytes(UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testPostMovieDetailsUnauthorized() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testPostMovieDetailsCreated() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/movies")
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("moviefan_admin" + ":" + "password").getBytes(UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testPostMovieCommentsForbidden() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/comments")
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("moviefan_none" + ":" + "password").getBytes(UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testPostMovieCommentsUnauthorized() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testPostMovieCommentsCreated() {

        ClientResponse clientResponse = WebClient
                .create("http://127.0.0.1:" + port)
                .post()
                .uri("/api/v1/comments")
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("moviefan_user" + ":" + "password").getBytes(UTF_8)))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(EMPTY_JSON))
                .exchange()
                .block();

        assertThat(clientResponse.statusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
