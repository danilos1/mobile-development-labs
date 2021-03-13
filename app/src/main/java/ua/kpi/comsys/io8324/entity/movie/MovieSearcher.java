package ua.kpi.comsys.io8324.entity.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieSearcher {
    private List<Movie> movies;

    @JsonProperty("Search")
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}