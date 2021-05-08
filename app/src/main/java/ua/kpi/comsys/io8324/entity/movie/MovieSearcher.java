package ua.kpi.comsys.io8324.entity.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieSearcher {
    private List<Movie> movies;
    private int totalResults;
    private String response;
    private String error;

    @JsonProperty("Search")
    public List<Movie> getMovies() {
        return movies;
    }

    @JsonProperty
    public int getTotalResults() {
        return totalResults;
    }

    @JsonProperty("Response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("Error")
    public String getError() {
        return error;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}