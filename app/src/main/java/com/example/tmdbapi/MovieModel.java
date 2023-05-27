package com.example.tmdbapi;

public class MovieModel {
    private String MovieName;
    private String Overview;
    private String posterPath;

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    private String PosterPath;

}
