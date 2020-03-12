package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MovieTheatersListViewActivity extends AppCompatActivity implements CustomAdapter.OnMovieListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_theaters_list_view);


        ArrayList<MovieRecyclerView> items = new ArrayList<>();
        // passing onMovieListener interface to constructor of CustomAdapter
        CustomAdapter adapter = new CustomAdapter(this, items, this);
        //RecyclerView recyclerView = findViewById(R.id.rvNumbers);

        // recyclerView fills with each individual movie in a horizontal layout
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        //recyclerView.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            items.add(new MovieRecyclerView(R.drawable.cinivibe_logo, "Parasite"));
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onMovieClick(int position) {

    }

    public void showMovie(View v){
        Intent intent = new Intent(this, IndividualMovieActivity.class);
        startActivity(intent);
    }

    public void moviesActivityy(View v){
        //Intent intent = new Intent(this, GridViewActivity.class);
        //startActivity(intent);
    }
}
