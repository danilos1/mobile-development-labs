package ua.kpi.comsys.io8324.tabfragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.Movies;
import ua.kpi.comsys.io8324.utils.MovieAdapter;

public class MoviesFragment extends Fragment {
    private RecyclerView movieListView;

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

        InputStream inputStream = null;
        try {
            inputStream = getContext().getAssets().open("json/movie_list.txt");
            System.out.println(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MovieAdapter movieAdapter = new MovieAdapter(view.getContext(), Movies.getMoviesFromJsonData(inputStream));
        movieListView.setAdapter(movieAdapter);
        movieListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}