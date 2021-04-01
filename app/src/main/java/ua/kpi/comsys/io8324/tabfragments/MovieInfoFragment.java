package ua.kpi.comsys.io8324.tabfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.kpi.comsys.io8324.R;
import ua.kpi.comsys.io8324.entity.movie.Movie;

public class MovieInfoFragment extends Fragment {
    private Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);
        this.movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");

        return view;
    }
}