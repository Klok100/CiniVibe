package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StreamsHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams_home);
    }

    public void streamsListView(View v){
        Intent intent = new Intent(this, StreamsListViewActivity.class);
        startActivity(intent);
    }
}
