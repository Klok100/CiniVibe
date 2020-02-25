package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainHomeScreenActivity extends AppCompatActivity implements CustomAdapter.OnMovieListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        ArrayList<MovieRecyclerView> items = new ArrayList<>();
        // passing onMovieListener interface to constructor of CustomAdapter
        CustomAdapter adapter = new CustomAdapter(this, items, this);
        RecyclerView recyclerView = findViewById(R.id.streamingMoviesRecView);

        // recyclerView fills with each individual movie in a horizontal layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            items.add(new MovieRecyclerView(R.drawable.cinivibe_logo, "Parasite"));
            adapter.notifyDataSetChanged();
        }

    }

    public void streamsActivity(View v) {
        Intent intent = new Intent(this, StreamsHomeActivity.class);
        startActivity(intent);
    }

    public void moviesActivity(View v){
        Intent intent = new Intent(this, MoviesHomeActivity.class);
        startActivity(intent);
    }

    // implemented method from class CustomAdapter to navigate to new activity
    // passes movie from position sent
    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, IndividualMovieActivity.class);
        // intent.putExtra("something", "something2");
        startActivity(intent);
    }
}
