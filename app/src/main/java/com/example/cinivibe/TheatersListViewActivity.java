package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TheatersListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters_list_view);
    }

    public void moviesListView(View v){
        Intent intent = new Intent(this, MovieTheatersListViewActivity.class);
        startActivity(intent);
    }
}
