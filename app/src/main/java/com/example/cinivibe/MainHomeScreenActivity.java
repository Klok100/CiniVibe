package com.example.cinivibe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainHomeScreenActivity extends AppCompatActivity implements CustomAdapter.OnMovieListener, NavigationView.OnNavigationItemSelectedListener {

    // Creates a global drawer object
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        // Gets the custom Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainHomeScreenFragment()).commit();

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_theater:
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                         new IndividualMovieFragment()).commit();
                 break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onOpenPressed(View v) {
            drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}
