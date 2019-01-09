package com.plmakas.movies.service.details.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MovieDetailsParsingExcpetion extends ResponseStatusException {

    public MovieDetailsParsingExcpetion(Throwable t) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error parsing response from backend movie details service: " + t.getMessage(), t);
    }

}
