package ua.kpi.comsys.io8324.tabfragments;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import ua.kpi.comsys.io8324.MovieAddFormActivity;
import ua.kpi.comsys.io8324.MovieInfoActivity;
import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;
import ua.kpi.comsys.io8324.entity.movie.Movies;
import ua.kpi.comsys.io8324.utils.ActivityHelper;
import ua.kpi.comsys.io8324.utils.MovieAdapter;

import static com.ea.async.Async.await;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.apache.commons.io.IOUtils.DEFAULT_BUFFER_SIZE;

public class MoviesFragment extends Fragment implements MovieAdapter.OnMovieListener {
    private static final String TAG = "MoviesFragment";

    private static MovieAdapter movieAdapter;
    private RecyclerView movieListView;
    private static List<Movie> movieList;
    private EditText searchEditText;
    private String MOVIE_API_URL = "http://www.omdbapi.com/?apikey=7e9fe69e&s=";


    private ItemTouchHelper.SimpleCallback itemTouchHCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(getContext(), movieList, MoviesFragment.this);

        new ItemTouchHelper(itemTouchHCallback).attachToRecyclerView(movieListView);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 3) {
                    return;
                }
                new MovieAsyncTask().execute(MOVIE_API_URL +  s + "&page=1");
            }

            @Override
            public void afterTextChanged(Editable s) {
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

        movieList = movieAdapter.getActualFilteredList();
        String filePath = "";
        try {
            filePath = "movie_data/".concat(movieList.get(position)
                    .getImdbID())
                    .concat(".txt");

            InputStream fileInputStream = ActivityHelper.getInputStreamFromFile(
                    Objects.requireNonNull(getContext()), filePath
            );

            MovieInfo movieInfo = Movies.getMovieInfo(fileInputStream);
            intent.putExtra("movieInfo", movieInfo);
            startActivity(intent);

        } catch (FileNotFoundException e) {
            Log.e(TAG, String.format("cannot find specified file '%s'", filePath));
            Toast.makeText(getContext(), "There is no found info about this movie",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream data = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                data = urlConnection.getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return data;
        }


        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            movieAdapter.clear();
            movieList = Movies.getMoviesFromJsonData(inputStream);
            movieAdapter = new MovieAdapter(getContext(), movieList, MoviesFragment.this);
            movieListView.setAdapter(movieAdapter);
            movieListView.setLayoutManager(new LinearLayoutManager(getContext()));
            Log.i(TAG, "movielist "+movieList);
        }
    }
}