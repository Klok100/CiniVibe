package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);
    }

    public void streamsActivity(View v) {
        Intent intent = new Intent(this, StreamsHomeActivity.class);
        startActivity(intent);
    }

    public void moviesActivity(View v){
        Intent intent = new Intent(this, MoviesHomeActivity.class);
        startActivity(intent);
    }
}
