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
    private OnMovieListener onMovieListener;

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieListener onMovieListener) {
        this.movieList = movieList;
        this.inflater = LayoutInflater.from(context);
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view, onMovieListener);
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
        holder.movieTypeTextView.setText(movie.getType());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView movieImageView;
        protected TextView movieTitleTextView;
        protected TextView movieYearTextView;
        protected TextView movieTypeTextView;
        protected OnMovieListener onMovieListener;

        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);
            this.movieImageView = itemView.findViewById(R.id.movieImageView);
            this.movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            this.movieYearTextView = itemView.findViewById(R.id.movieYearTextView);
            this.movieTypeTextView = itemView.findViewById(R.id.movieTypeTextView);
            this.onMovieListener = onMovieListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClickListener(getAdapterPosition());
        }
    }

    public interface OnMovieListener {
        void onMovieClickListener(int position);
    }
}
