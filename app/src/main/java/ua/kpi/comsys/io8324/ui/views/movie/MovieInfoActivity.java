package ua.kpi.comsys.io8324.ui.views.movie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;

public class MovieInfoActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        MovieInfo movieInfo = ((MovieInfo) getIntent().getSerializableExtra("movieInfo"));

        ImageView posterView = findViewById(R.id.movieImageView);
        Picasso.get().load(movieInfo.getPoster()).into(posterView);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView yearTextView = findViewById(R.id.yearTextView);
        TextView genreTextView = findViewById(R.id.genreTextView);
        TextView directorTextView = findViewById(R.id.directorTextView);
        TextView actorsTextView = findViewById(R.id.actorsTextView);
        TextView countryTextView = findViewById(R.id.countryTextView);
        TextView langTextView = findViewById(R.id.langTextView);
        TextView prodTextView = findViewById(R.id.prodTextView);
        TextView releasedTextView = findViewById(R.id.releasedTextView);
        TextView runtimeTextView = findViewById(R.id.runtimeTextView);
        TextView awardsTextView = findViewById(R.id.awardsTextView);
        TextView ratingTextView = findViewById(R.id.ratingTextView);
        TextView plotTextView = findViewById(R.id.plotTextView);

        titleTextView.setText(movieInfo.getTitle());
        yearTextView.setText(movieInfo.getYear());
        genreTextView.setText(movieInfo.getGenre());
        directorTextView.setText(movieInfo.getDirector());
        actorsTextView.setText(movieInfo.getActors());
        countryTextView.setText(movieInfo.getCountry());
        langTextView.setText(movieInfo.getLang());
        prodTextView.setText(movieInfo.getProd());
        releasedTextView.setText(movieInfo.getReleased());
        runtimeTextView.setText(movieInfo.getRuntime());
        awardsTextView.setText(movieInfo.getAwards());
        ratingTextView.setText(movieInfo.getRating());
        plotTextView.setText(movieInfo.getPlot());
    }
}