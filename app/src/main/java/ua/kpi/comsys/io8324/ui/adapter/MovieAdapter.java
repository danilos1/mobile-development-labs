package ua.kpi.comsys.io8324.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;
import ua.kpi.comsys.io8324.entity.movie.MovieInfo;
import ua.kpi.comsys.io8324.http.movie.MovieHttpRepository;
import ua.kpi.comsys.io8324.ui.views.movie.MovieInfoActivity;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String TAG = "MovieAdapter";
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context) {
        this.context = context;
        this.movieList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clear() {
        if (movieList == null) {
            return;
        }
        int size = movieList.size();
        movieList.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view, position -> {
            Intent intent = new Intent(context, MovieInfoActivity.class);
            MovieHttpRepository httpRepository = new MovieHttpRepository();
            String imdbID = movieList.get(position).getImdbID();

            MovieInfo movieInfo = httpRepository.fetchMovieInfoData(imdbID);

            intent.putExtra("movieInfo", movieInfo);
            viewGroup.getContext().startActivity(intent);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        String noPosterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png";
        String actualPoster = movie.getPoster();
        Picasso.get().load(actualPoster.equals("N/A") ? noPosterUrl : actualPoster)
                .placeholder(R.drawable.image_animation)
                .into(holder.movieImageView);

        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieYearTextView.setText(movie.getYear());
        holder.movieTypeTextView.setText(movie.getType());
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }

        return 0;
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
