package ua.kpi.comsys.io8324.entity.movie;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MovieInfo getMovieInfo(InputStream inputStream) {
        MovieInfo movieInfo = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            movieInfo = objectMapper.readValue(inputStream, MovieInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(movieInfo).orElse(new MovieInfo());
    }
}
