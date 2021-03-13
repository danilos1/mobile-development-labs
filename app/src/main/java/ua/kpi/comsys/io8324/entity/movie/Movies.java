package ua.kpi.comsys.io8324.entity.movie;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Movies {
    private static final Logger log = LoggerFactory.getLogger(Movies.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private Movies() {}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Movie> getMoviesFromJsonData(File jsonFile) {
        MovieSearcher searchResults = null;
        try {
            searchResults = mapper.readValue(jsonFile, MovieSearcher.class);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(searchResults.getMovies()).orElse(new ArrayList<>());
    }
}
