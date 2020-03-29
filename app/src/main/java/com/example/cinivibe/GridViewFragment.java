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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_view, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<MovieRecyclerView> items = new ArrayList<>();
        // passing onMovieListener interface to constructor of CustomAdapter
        CustomAdapter adapter = new CustomAdapter(this.getContext(), items, this);
        RecyclerView recyclerView = view.findViewById(R.id.rvNumbers);

        // recyclerView fills with each individual movie in a horizontal layout
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 28; i++) {
            items.add(new MovieRecyclerView(R.drawable.cinivibe_logo, "Parasite"));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMovieClick(int position) {
        IndividualMovieFragment nextFrag = new IndividualMovieFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}
