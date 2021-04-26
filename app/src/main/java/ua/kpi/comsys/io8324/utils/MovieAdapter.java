package ua.kpi.comsys.io8324.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String TAG = "MovieAdapter";

    private LayoutInflater inflater;
    private final List<Movie> movieList;
    private List<Movie> filteredMovieList;
    private OnMovieListener onMovieListener;

    public List<Movie> getActualFilteredList() {
        return filteredMovieList;
    }

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieListener onMovieListener) {
        this.movieList = movieList;
        this.inflater = LayoutInflater.from(context);
        this.onMovieListener = onMovieListener;
        this.filteredMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = filteredMovieList.get(position);
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
        return filteredMovieList.size();
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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence sequence) {
                String key = sequence.toString();
                if (key.isEmpty()) {
                    filteredMovieList = movieList;
                    Log.d(TAG, "filteredMovieList is empty: "+filteredMovieList);
                } else {
                    List<Movie> fltMovieList = new ArrayList<>();
                    for (Movie movie: movieList) {
                        if (movie.getTitle().contains(key)) {
                            fltMovieList.add(movie);
                        }
                    }
                    filteredMovieList = fltMovieList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovieList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence sequence, FilterResults results) {
                filteredMovieList = (List<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
