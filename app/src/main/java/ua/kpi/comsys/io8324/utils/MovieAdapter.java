package ua.kpi.comsys.io8324.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private LayoutInflater inflater;
    private final List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (!movie.getPoster().equals("")) {
            try {
                InputStream ims = inflater.getContext().getAssets().open(
                        "movie_posters/" + movie.getPoster());
                Drawable d = Drawable.createFromStream(ims, null);
                holder.movieImageView.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            holder.movieImageView.setImageResource(0);
        }
        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieYearTextView.setText(movie.getYear());
        holder.movieRatingTextView.setText(movie.getImdbID());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        protected ImageView movieImageView;
        protected TextView movieTitleTextView;
        protected TextView movieYearTextView;
        protected TextView movieRatingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.movieImageView = itemView.findViewById(R.id.movieImageView);
            this.movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            this.movieYearTextView = itemView.findViewById(R.id.movieYearTextView);
            this.movieRatingTextView = itemView.findViewById(R.id.movieRatingTextView);
        }
    }
}
