package ua.kpi.comsys.io8324.tabfragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import ua.kpi.comsys.io8324.MovieAddFormActivity;
import ua.kpi.comsys.io8324.MovieInfoActivity;
import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;
import ua.kpi.comsys.io8324.entity.movie.Movies;
import ua.kpi.comsys.io8324.utils.AssetHelper;
import ua.kpi.comsys.io8324.utils.MovieAdapter;

public class MoviesFragment extends Fragment implements MovieAdapter.OnMovieListener {
    private static final String TAG = "MoviesFragment";

    private static MovieAdapter movieAdapter;
    private RecyclerView movieListView;
    private static List<Movie> movieList;
    private static List<Movie> filteredMovieList;
    private EditText searchEditText;
    public CharSequence searchText = "";

    private ItemTouchHelper.SimpleCallback itemTouchHCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            filteredMovieList = movieAdapter.getActualFilteredList();

            int adapterPosition = viewHolder.getAdapterPosition();

            Log.d(TAG, String.format("adapterPosition: %d", adapterPosition));

            Toast.makeText(getContext(), String.format("%s movie was removed successfully",
                    filteredMovieList.get(adapterPosition).getTitle()), Toast.LENGTH_LONG).show();

            Log.d(TAG, String.format("filter movie list: %s", filteredMovieList.toString()));

            removeDataFromFilteredMovieList(adapterPosition);
            movieAdapter.notifyDataSetChanged();
        }
    };

    private void removeDataFromFilteredMovieList(int adapterPosition) {
        Movie removedMovie = filteredMovieList.get(adapterPosition);
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getImdbID().equals(removedMovie.getImdbID())) {
                movieList.remove(i);
                break;
            }
        }
        filteredMovieList.remove(adapterPosition);
    }

    public static void addToMovieList(Movie movie) {
        movieList.add(movie);
        filteredMovieList = movieList;
        movieAdapter.notifyDataSetChanged();
    }

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

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);

        searchEditText = view.findViewById(R.id.search_movie_bar);

        InputStream fileInputStream = null;
        try {
            fileInputStream = AssetHelper.getInputStreamFromFile(view.getContext(), "json/movie_list.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        movieList = Movies.getMoviesFromJsonData(fileInputStream);
        filteredMovieList = movieList;

        movieAdapter = new MovieAdapter(view.getContext(), movieList, this);
        movieListView.setAdapter(movieAdapter);
        movieListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        new ItemTouchHelper(itemTouchHCallback).attachToRecyclerView(movieListView);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                movieAdapter.getFilter().filter(s);
                filteredMovieList = movieAdapter.getActualFilteredList();
                searchText = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredMovieList = movieAdapter.getActualFilteredList();
            }
        });

        fabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getContext(), MovieAddFormActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMovieClickListener(int position) {
        Intent intent = new Intent(this.getContext(), MovieInfoActivity.class);

        filteredMovieList = movieAdapter.getActualFilteredList();
        String filePath = "";
        try {
            filePath = "movie_data/".concat(filteredMovieList.get(position)
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