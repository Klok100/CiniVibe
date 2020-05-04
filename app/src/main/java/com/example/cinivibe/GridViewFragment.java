package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        RecyclerView recyclerView = view.findViewById(R.id.rvNumbers);
        CustomAdapter adapter;

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

        // recyclerView fills with each individual movie in a horizontal layout
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onMovieClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", genre.get(position));

        IndividualMovieFragment nextFrag = new IndividualMovieFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}
