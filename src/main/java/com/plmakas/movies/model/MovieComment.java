package com.plmakas.movies.model;

public class MovieComment {

    private long movieid;

    private String username;

    private String message;

    public long getMovieid() {
        return movieid;
    }

    public MovieComment() {
    }

    public void setMovieid(long movieid) {
        this.movieid = movieid;
    }

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
