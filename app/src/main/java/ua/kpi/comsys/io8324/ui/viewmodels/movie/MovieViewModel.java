package ua.kpi.comsys.io8324.ui.viewmodels.movie;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.http.movie.MovieHttpRepository;
import ua.kpi.comsys.io8324.ui.adapter.MovieAdapter;

public class MovieViewModel extends AndroidViewModel {
    private static final String TAG = "MovieViewModel";
    private MutableLiveData<List<Movie>> movieList;
    private MovieAdapter movieAdapter;
    private static final MovieHttpRepository httpRepository = new MovieHttpRepository();

    private ItemTouchHelper.SimpleCallback itemTouchHCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int adapterPosition = viewHolder.getAdapterPosition();
            movieAdapter.notifyDataSetChanged();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MovieViewModel(@NonNull Application application,
                          MutableLiveData<List<Movie>> movieList,
                          MovieAdapter movieAdapter
    ) {
        super(application);
        this.movieAdapter = movieAdapter;
        this.movieList = movieList;
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Movie>> getMovieList() {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
        }

        return movieList;
    }

    public TextWatcher getMovieSearchTextListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    List<Movie> fetchedMovies = httpRepository.fetchMovieData(s.toString().trim());
                    movieList.setValue(fetchedMovies);
                } else {
                    movieList.setValue(new ArrayList<>());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}
