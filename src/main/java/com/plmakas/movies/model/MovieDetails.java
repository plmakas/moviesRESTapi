package com.plmakas.movies.model;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails {

    private long id;

    private String title;

    private String description;

    private List<MovieComment> comments = new ArrayList<>();

    public MovieDetails() {
    }

    public MovieDetails(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

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

    public List<MovieComment> getComments() {
        return comments;
    }
}
