package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridViewFragment extends Fragment implements CustomAdapter.OnMovieListener{

    private ArrayList<MovieRecyclerView> now_playing;
    private ArrayList<MovieRecyclerView> upcoming;
    private ArrayList<MovieRecyclerView> action;
    private ArrayList<MovieRecyclerView> romance;
    private ArrayList<MovieRecyclerView> horror;
    private ArrayList<MovieRecyclerView> comedy;
    private ArrayList<MovieRecyclerView> sci_fi;
    private ArrayList<MovieRecyclerView> genre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_view, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        now_playing = bundle.getParcelableArrayList("now_playing");
        upcoming = bundle.getParcelableArrayList("upcoming");
        action = bundle.getParcelableArrayList("action");
        romance = bundle.getParcelableArrayList("romance");
        horror = bundle.getParcelableArrayList("horror");
        comedy = bundle.getParcelableArrayList("comedy");
        sci_fi = bundle.getParcelableArrayList("sci_fi");

        if (bundle.getBoolean("genreCheck")) {
            onMovieClick(MainHomeScreenActivity.movie);
        }

        final RecyclerView recyclerView = view.findViewById(R.id.rvNumbers);
        final CustomAdapter adapter;

        if (now_playing != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), now_playing, this);
            genre = now_playing;
        }

        else if (upcoming != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), upcoming, this);
            genre = upcoming;
        }

        else if (action != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), action, this);
            genre = action;
        }

        else if (romance != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), romance, this);
            genre = romance;
        }

        else if (horror != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), horror, this);
            genre = horror;
        }

        else if (comedy != null){
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), comedy, this);
            genre = comedy;
        }

        else {
            // passing onMovieListener interface to constructor of CustomAdapter
            adapter = new CustomAdapter(this.getContext(), sci_fi, this);
            genre = sci_fi;
        }

        MainHomeScreenActivity.genre = genre;


        // recyclerView fills with each individual movie in a horizontal layout
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Sets the onClickListener for the Spinner to sort the list of movies
        Spinner sortingSpinner = view.findViewById(R.id.sortingSpinner);
        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Sort from A-Z")){

                    sortAZ();

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                }

                if (selectedItem.equals("Sort by Rating")){

                    sortRating();

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    // Sorts the movies by their title
    public void sortAZ(){

        int min = 0;
        MovieRecyclerView temp = null;
        for (int i = 0; i < genre.size() - 1; i++)
        {
            min = i;
            for (int j = i + 1; j < genre.size(); j++)
            {
                if (genre.get(min).getTitle().compareTo(genre.get(j).getTitle()) > 0)
                {
                    min = j;
                }
            }

            temp = genre.get(min);
            genre.set(min, genre.get(i));
            genre.set(i, temp);
        }
    }

    // Sorts the movies by their ratings
    public void sortRating(){

        int min = 0;
        MovieRecyclerView temp = null;
        for (int i = 0; i < genre.size() - 1; i++)
        {
            min = i;
            for (int j = i + 1; j < genre.size(); j++)
            {
                if (genre.get(min).getRating() < genre.get(j).getRating())
                {
                    min = j;
                }
            }

            temp = genre.get(min);
            genre.set(min, genre.get(i));
            genre.set(i, temp);
        }
    }

    @Override
    public void onMovieClick(int position) {

        if (position > -1) {
            MainHomeScreenActivity.movie = position;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", MainHomeScreenActivity.genre.get(MainHomeScreenActivity.movie));

        IndividualMovieFragment nextFrag = new IndividualMovieFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}
