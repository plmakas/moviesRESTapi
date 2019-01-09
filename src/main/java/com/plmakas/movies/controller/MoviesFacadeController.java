package com.plmakas.movies.controller;

import com.plmakas.movies.model.MovieComment;
import com.plmakas.movies.model.MovieDetails;
import com.plmakas.movies.model.MovieDetailsDTO;
import com.plmakas.movies.service.comments.MovieCommentsService;
import com.plmakas.movies.service.details.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class MoviesFacadeController {

    private final MovieDetailsService movieDetailsService;

    private final MovieCommentsService movieCommentsService;

    @Autowired
    public MoviesFacadeController(MovieDetailsService movieDetailsService, MovieCommentsService movieCommentsService) {
        this.movieDetailsService = movieDetailsService;
        this.movieCommentsService = movieCommentsService;
    }

    @GetMapping(value = "/movies/{id}", produces = "application/json")
    public Mono<MovieDetailsDTO> getMovies(@PathVariable("id") long id) {
        return movieDetailsService
                .findMovieDetails(id)
                .zipWith(movieCommentsService.findMovieComments(id), MovieDetailsDTO::new);
    }

    @PostMapping(value = "/movies", produces = "application/json", consumes = "application/json")
    public ResponseEntity addMovieDetails(@RequestBody MovieDetails movieDetails, UriComponentsBuilder uriBuilder) {

        // send movieDetails to backend service; assuming success

        URI returnURI = uriBuilder.path("/movies/{id}").buildAndExpand(movieDetails.getId()).toUri();
        return ResponseEntity
                .created(returnURI)
                .body(returnURI);
    }

    @PostMapping(value = "/comments", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addMovieComment(@RequestBody MovieComment movieComment, UriComponentsBuilder uriBuilder) {

        // send movieComment to backend service; assuming success

        URI returnURI = uriBuilder.path("/comments/{id}").buildAndExpand(movieComment.getMovieid()).toUri();
        return ResponseEntity
                .created(returnURI)
                .body(returnURI);
    }

}
