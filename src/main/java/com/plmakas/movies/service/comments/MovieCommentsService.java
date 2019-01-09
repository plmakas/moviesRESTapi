package com.plmakas.movies.service.comments;

import com.plmakas.movies.model.MovieComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieCommentsService {

    private Logger logger = LoggerFactory.getLogger(MovieCommentsService.class);

    private Map<Long, List<MovieComment>> fallbackCache;

    private WebClient commentsClient;

    public MovieCommentsService(@Value("${moviecomments.host}") final String commentsUri) {
        commentsClient = WebClient.create(commentsUri);
        fallbackCache = new ConcurrentHashMap<>();
    }

    public Mono<List<MovieComment>> findMovieComments(long movieid) {

        return commentsClient
                .get()
                .uri("/comments/" + movieid)
                .exchange()
                .doOnNext(this::handleErrorResponse)
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(MovieComment.class))
                .doOnError(this::handleParsingError)
                .collectList()
                .doOnNext(movieComments -> fallbackCache.put(movieid, movieComments))
                .onErrorReturn(fallbackReturn(movieid));
    }

    private void handleErrorResponse(ClientResponse clientResponse) {
        if(clientResponse.statusCode().isError()) {
            logger.error("Failed to get movie comments from backend comment service, status code: " + clientResponse.statusCode());
        }
    }

    private void handleParsingError(Throwable throwable) {
        logger.error("Failed to parse movie comments backend service response, " + throwable.getMessage(), throwable);
    }

    private List<MovieComment> fallbackReturn(long movieid) {
        return Optional
                .ofNullable(fallbackCache.get(movieid))
                .orElse(new ArrayList<>());
    }
}
