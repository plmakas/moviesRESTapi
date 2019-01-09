package com.plmakas.movies.service.details;

import com.plmakas.movies.model.MovieDetails;
import com.plmakas.movies.service.details.exception.MovieDetailsParsingExcpetion;
import com.plmakas.movies.service.details.exception.MovieDetailsResponseException;
import org.apache.kahadb.util.LFUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class MovieDetailsService {

    private LFUCache<Long, MovieDetails> cache;

    private WebClient movieDetailsClient;

    public MovieDetailsService(@Value("${moviedetails.host}") final String detailsUri) {
        movieDetailsClient = WebClient.create(detailsUri);
        cache = new LFUCache<>(3, 0.3f);
    }


    public Mono<MovieDetails> findMovieDetails(long id) {

        return Optional
                .ofNullable(cache.get(id))
                .map(Mono::just)
                .orElse(movieDetailsClient
                .get()
                .uri("/movies/" + id)
                .exchange()
                .doOnNext(this::handleErrorResponse)
                .flatMap(clientResponse -> clientResponse.bodyToMono(MovieDetails.class))
                .doOnNext(movieDetails -> cache.put(id, movieDetails))
                .onErrorMap(t -> !(t instanceof MovieDetailsResponseException), MovieDetailsParsingExcpetion::new));
    }

    private void handleErrorResponse(ClientResponse clientResponse) throws MovieDetailsResponseException {
        if(clientResponse.statusCode().isError()) {
            throw new MovieDetailsResponseException(clientResponse.statusCode());
        }

    }
}
