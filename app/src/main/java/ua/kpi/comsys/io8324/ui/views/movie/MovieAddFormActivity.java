package ua.kpi.comsys.io8324.ui.views.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;

import static android.widget.Toast.LENGTH_SHORT;

public class MovieAddFormActivity extends AppCompatActivity {
    public static final String TAG = "MovieAddFormActivity";

    private EditText titleEditText;
    private EditText typeEditText;
    private EditText yearEditText;
    private Button addButton;
    private TextView logMovies;

    private List<Movie> logMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_add_form);
        this.titleEditText = findViewById(R.id.titleEditText);
        this.typeEditText = findViewById(R.id.typeEditText);
        this.yearEditText = findViewById(R.id.yearEditText);
        this.addButton = findViewById(R.id.addMovieButton);
        this.logMovies = findViewById(R.id.logView);
        this.logMovieList = new ArrayList<>();

        addButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String type  = typeEditText.getText().toString();
            String year  = yearEditText.getText().toString();

            if (fieldAreValid(title, type, year)) {
                Movie movie = new Movie(title, year, "", type, "");
                MoviesFragment.addToMovieList(movie);
                titleEditText.setText("");
                typeEditText.setText("");
                yearEditText.setText("");
                logMovieList.add(movie);
                logMovies.append(notifyLogMovieList());
                Toast.makeText(getApplicationContext(),
                        String.format("%s was successfully added to the movie list \uD83D\uDE42", movie.getTitle()),
                        Toast.LENGTH_LONG
                ).show();
                addButton.setFocusable(true);
            }
        });
    }

    private String notifyLogMovieList() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("yyyy:dd:MM[HH:mm:ss]");
        String now = simpleDateFormat.format(new Date());
        Movie lastMovie = logMovieList.get(logMovieList.size() - 1);

        return String.format("%s:%s - %s year, %s type%n", now, lastMovie.getTitle(), lastMovie.getYear(), lastMovie.getType());
    }

    private boolean fieldAreValid(String title, String type, String year) {
        int parsedYear;

        if (title.isEmpty() || type.isEmpty() || year.isEmpty()) {
            Toast.makeText(getApplicationContext(), "One of the field is empty",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (title.length() < 3 || type.length() < 3) {
            Toast.makeText(getApplicationContext(), "Title or type field's size must be >= 3",
                    LENGTH_SHORT).show();
            return false;
        }

        try {
            parsedYear = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            Log.e(TAG, "cannot parse the year: "+e.getMessage());
            Toast.makeText(getApplicationContext(),
                    "Invalid specified year, expected numeric format"+year,
                    LENGTH_SHORT).show();
            return false;
        }

        if (parsedYear < 1895) {
            Toast.makeText(getApplicationContext(),
                    "The first movie was invented at 1895. I guess you made it mistake \uD83D\uDE42",
                    LENGTH_SHORT
            ).show();
            return false;
        }

        return true;
    }
}