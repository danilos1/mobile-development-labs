package ua.kpi.comsys.io8324.http.movie;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;

import static ua.kpi.comsys.io8324.entity.movie.Movies.getMovieInfo;
import static ua.kpi.comsys.io8324.entity.movie.Movies.getMoviesFromJsonData;

public class MovieHttpRepository {
    public static final String TAG = "MovieHttpRepository";
    private static final String API_KEY = "7e9fe69e";


    public Uri.Builder getBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.omdbapi.com")
                .appendQueryParameter("apikey", API_KEY);

        return builder;
    }

    public List<Movie> fetchMovieData(String search) {
        Uri.Builder builder = getBuilder().appendQueryParameter("s", search);
        String url = builder.build().toString();
        Log.i(TAG, String.format("Sending GET request to %s server", url));

        MovieAsyncTask fetchMovieAsync = new MovieAsyncTask();
        fetchMovieAsync.execute(url);

        List<Movie> fetchedMovies = new ArrayList<>();
        try {
            InputStream fetchedMoviesStream = fetchMovieAsync.get();
            fetchedMovies = getMoviesFromJsonData(fetchedMoviesStream);

        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Getting an error during sending GET request: "+e.getMessage());
        }

        return fetchedMovies;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MovieInfo fetchMovieInfoData(String imdbID) {
        Uri.Builder builder = getBuilder().appendQueryParameter("i", imdbID);
        String url = builder.build().toString();
        Log.i(TAG, String.format(
                "Sending GET request to %s server to fetch movie with id=%s",
                url, imdbID
        ));

        MovieInfoAsyncTask fetchMovieInfoAsync = new MovieInfoAsyncTask();
        fetchMovieInfoAsync.execute(url);

        MovieInfo movieInfo = null;
        try {
            InputStream movieInputStream = fetchMovieInfoAsync.get();
            movieInfo = getMovieInfo(movieInputStream);
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Getting an error during sending GET request: "+e.getMessage());
        }

        return movieInfo;
    }
}
