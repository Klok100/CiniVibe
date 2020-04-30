package com.example.cinivibe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class IndividualMovieFragment extends Fragment {

    public BottomNavDrawerFragment bottomNavFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_individual_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavFragment = new BottomNavDrawerFragment();
                bottomNavFragment.show(getActivity().getSupportFragmentManager(), "TAG");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new IndividualMovieFragment()).commit();
            }
        });

    }
}