package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class IndividualMovieFragment extends Fragment {

    private MovieRecyclerView movie;
    private String image;
    private String title;
    private String description;
    private String genreListView;
    private int [] genres;
    private float rating;
    private String releaseDate;
    private boolean genreCheck;

//    public BottomNavDrawerFragment bottomNavFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_individual_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        // Gets individual movie data
        movie = bundle.getParcelable("movie");
//        genreCheck = bundle.getBoolean("genreCheck");

        // Gets movie title and description
        image = "https://image.tmdb.org/t/p/w185/" + movie.getImage();
        title = movie.getTitle();
        description = movie.getDescription();
        genres = movie.getArray();
        rating = (float) movie.getRating();
        releaseDate = movie.getReleaseDate();

        ImageView imageView = view.findViewById(R.id.movieImage);
        Picasso.with(getContext()).load(image).into(imageView);

        // Displays movie title
        final TextView titleView = view.findViewById(R.id.cinivibeTitle);
        titleView.setText(title);

        // Displays movie description
        final TextView descriptionView = view.findViewById(R.id.movieDescriptions);
        descriptionView.setText(description);

        // Displays the list of genres for the movie
        final TextView genreView = view.findViewById(R.id.genreList);
        genreListView = "Genres:";
        for (int i = 0; i < genres.length; i++){
            if (genres[i] == 28){
                genreListView += " Action,";
            }

            if (genres[i] == 10749){
                genreListView += " Romance,";
            }

            if (genres[i] == 27){
                genreListView += " Horror,";
            }

            if (genres[i] == 35){
                genreListView += " Comedy,";
            }

            if (genres[i] == 878){
                genreListView += " Sci-Fi,";
            }
        }

        if (String.valueOf(genreListView.charAt(genreListView.length() - 1)).equals(",")){
            genreListView = genreListView.substring(0, genreListView.length() - 1);
        }

        genreView.setText(genreListView);

        // Sets the rating on the Rating Bar out of 10
        final RatingBar mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingBar.setRating(rating);

        // Displays the release date for the movie
        final TextView releaseDateView = view.findViewById(R.id.releaseDate);
        releaseDateView.setText(releaseDate);


        final FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                BottomNavDrawerFragment bottomNavFragment = new BottomNavDrawerFragment();
                IndividualMovieFragment individualMovieFragment = new IndividualMovieFragment();

                bundle.putParcelable("movie", movie);
                individualMovieFragment.setArguments(bundle);
                bottomNavFragment.show(getActivity().getSupportFragmentManager(), "TAG");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        individualMovieFragment).commit();
            }
        });


    }
}
