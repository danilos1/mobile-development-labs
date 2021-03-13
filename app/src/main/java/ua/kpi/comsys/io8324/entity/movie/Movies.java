package ua.kpi.comsys.io8324.entity.movie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Movies {
    private static final ObjectMapper mapper = new ObjectMapper();

    private Movies() {}

    public static List<Movie> getMoviesFromJsonData(InputStream inputStream) {
        try {
            MovieSearcher searchResults = mapper.readValue(inputStream, MovieSearcher.class);
            return searchResults.getMovies();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
