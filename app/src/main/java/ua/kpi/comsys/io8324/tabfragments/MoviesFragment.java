package ua.kpi.comsys.io8324.tabfragments;

import android.content.Intent;
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

public class MoviesFragment extends Fragment implements MovieAdapter.OnMovieListener {
    private RecyclerView movieListView;
    private List<Movie> movieList;

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
        this.movieList = Movies.getMoviesFromJsonData(inputStream);
        MovieAdapter movieAdapter = new MovieAdapter(view.getContext(), movieList, this);
        movieListView.setAdapter(movieAdapter);
        movieListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onMovieClickListener(int position) {
//        Intent intent = new Intent(this.getContext(), MovieInfoFragment.class);
//        intent.putExtra("movie", movieList.get(position).toString());
//        startActivity(intent);
        Log.d("position: ", position+"");
    }
}