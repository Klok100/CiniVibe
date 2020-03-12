package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoviesHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_home);
    }

    public void moviesListView(View v){
        //Intent intent = new Intent(this, GridViewActivity.class);
        //startActivity(intent);
    }

    public void searchByTheater(View v){
        Intent intent = new Intent(this, TheatersListViewActivity.class);
        startActivity(intent);
    }
}
