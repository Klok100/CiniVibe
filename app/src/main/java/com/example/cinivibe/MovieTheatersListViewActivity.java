package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MovieTheatersListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_theaters_list_view);
    }

    public void showMovie(View v){
        Intent intent = new Intent(this, IndividualMovieActivity.class);
        startActivity(intent);
    }
}
