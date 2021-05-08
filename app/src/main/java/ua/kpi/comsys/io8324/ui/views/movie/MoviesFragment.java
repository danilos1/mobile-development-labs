package ua.kpi.comsys.io8324.ui.views.movie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.ui.viewmodels.movie.MovieViewModel;
import ua.kpi.comsys.io8324.ui.adapter.MovieAdapter;

public class MoviesFragment extends Fragment /*implements MovieAdapter.OnMovieListener*/ {

    private static final String TAG = "MoviesFragment";
    private MovieViewModel movieViewModel;


   /* private ItemTouchHelper.SimpleCallback itemTouchHCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            movieList = movieAdapter.getActualFilteredList();
            int adapterPosition = viewHolder.getAdapterPosition();
            Toast.makeText(getContext(), String.format("%s movie was removed successfully",
                    movieList.get(adapterPosition).getTitle()), Toast.LENGTH_LONG).show();

            removeDataFromFilteredMovieList(adapterPosition);
            movieAdapter.notifyDataSetChanged();
        }
    };

    private void removeDataFromFilteredMovieList(int adapterPosition) {
        Movie removedMovie = movieList.get(adapterPosition);
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getImdbID().equals(removedMovie.getImdbID())) {
                movieList.remove(i);
                break;
            }
        }
        movieList.remove(adapterPosition);
    }

    public static void addToMovieList(Movie movie) {
        movieList.add(movie);
        movieAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        FloatingActionButton addMovieButton = view.findViewById(R.id.fab_add);

        addMovieButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), MovieAddFormActivity.class);
            startActivity(intent);
        });

        EditText searchEditText = view.findViewById(R.id.search_movie_bar);
        RecyclerView movieListView = view.findViewById(R.id.movieListView);
        MovieAdapter movieAdapter = new MovieAdapter(getContext());

        movieListView.setAdapter(movieAdapter);
        movieListView.setLayoutManager(new LinearLayoutManager(getContext()));

        this.movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        searchEditText.addTextChangedListener(movieViewModel.getMovieSearchTextListener());

        LiveData<List<Movie>> movieLiveData = movieViewModel.getMovieList();
        movieLiveData.observe(getViewLifecycleOwner(), movieList -> {
            movieAdapter.clear();
            movieAdapter.setMovieList(movieList);
        });

        return view;
    }
}