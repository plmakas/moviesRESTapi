package com.plmakas.movies.service.details.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieDetailsResponseException extends ResponseStatusException {

    public MovieDetailsResponseException(HttpStatus t) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Movie details service returned unexpected status code " + t);
    }
}
