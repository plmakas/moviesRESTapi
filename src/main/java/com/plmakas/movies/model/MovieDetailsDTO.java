package com.plmakas.movies.model;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsDTO {

    public MovieDetailsDTO() {
    }

    public MovieDetailsDTO(MovieDetails movieDetails, List<MovieComment> movieComments) {
        this.id = movieDetails.getId();
        this.title = movieDetails.getTitle();
        this.description = movieDetails.getDescription();

        comments = new ArrayList<>();
        movieComments.forEach(comment -> {
            comments.add(new Comment(comment.getUsername(), comment.getMessage()));
        });
    }

    private long id;

    private String title;

    private String description;

    private List<Comment> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private class Comment {

        public Comment() {
        }

        public Comment(String username, String message) {
            this.username = username;
            this.message = message;
        }

        private String username;

        private String message;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
