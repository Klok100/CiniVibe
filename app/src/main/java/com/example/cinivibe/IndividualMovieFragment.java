package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class IndividualMovieFragment extends Fragment {

    private MovieRecyclerView movie;
    private String title;
    private String description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_individual_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        movie = bundle.getParcelable("movie");

        title = movie.getTitle();
        description = movie.getDescription();

        TextView titleView = view.findViewById(R.id.cinivibeTitle);
        titleView.setText(title);

        TextView descriptionView = view.findViewById(R.id.movieDescriptions);
        descriptionView.setText(description);

    }
}
