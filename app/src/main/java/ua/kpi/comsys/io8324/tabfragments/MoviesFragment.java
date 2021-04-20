package ua.kpi.comsys.io8324.tabfragments;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import ua.kpi.comsys.io8324.MovieInfoActivity;
import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;
import ua.kpi.comsys.io8324.entity.movie.Movies;
import ua.kpi.comsys.io8324.utils.AssetHelper;
import ua.kpi.comsys.io8324.utils.MovieAdapter;

public class MoviesFragment extends Fragment implements MovieAdapter.OnMovieListener {
    private RecyclerView movieListView;
    private List<Movie> movieList;
    private static final String TAG = "MoviesFragment";

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        this.movieListView = view.findViewById(R.id.movieListView);
        InputStream fileInputStream = null;
        try {
            fileInputStream = AssetHelper.getInputStreamFromFile(view.getContext(), "json/movie_list.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.movieList = Movies.getMoviesFromJsonData(fileInputStream);
        MovieAdapter movieAdapter = new MovieAdapter(view.getContext(), movieList, this);
        movieListView.setAdapter(movieAdapter);
        movieListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMovieClickListener(int position) {
        Intent intent = new Intent(this.getContext(), MovieInfoActivity.class);

        String filePath = "";
        try {
            filePath = "movie_data/".concat(movieList.get(position)
                    .getImdbID())
                    .concat(".txt");

            InputStream fileInputStream = AssetHelper.getInputStreamFromFile(
                    Objects.requireNonNull(getContext()), filePath
            );

            MovieInfo movieInfo = Movies.getMovieInfo(fileInputStream);
            intent.putExtra("movieInfo", movieInfo);
            startActivity(intent);

        } catch (FileNotFoundException e) {
            Log.e(TAG, String.format("cannot find specified file '%s'", filePath));
            Toast.makeText(getContext(), "There is no found info about this movie",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}