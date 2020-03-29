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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainHomeScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Creates a global drawer object
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        // Creates a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Adds the OnClickListener for the Home Button
        toolbar.findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MainHomeScreenFragment()).commit();
            }
        });

        // Adds the OnClickListener for the Search Button
        toolbar.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        // Gets the custom Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Fills the fragment container with the Main Home Screen Fragment at the start of this activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainHomeScreenFragment()).commit();

    }

    // Replaces the fragment container with the Grid View Fragment, when the See All button is pressed
    public void onSeeAll(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new GridViewFragment()).commit();
    }

    // Replaces the fragment container with the Main Home Screen Fragment, when the back button is pressed
    // Question: How to we want the back button to actually function? Does it just take the user to the page they were previously on?
    public void onBackButton(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainHomeScreenFragment()).commit();
    }

    // This controls the navigation within the side navigation bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){

            // When one of the icons is pressed, replace the fragment container with the customized Grid View Fragment

            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GridViewFragment()).commit();
                break;

            case R.id.nav_wishlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GridViewFragment()).commit();
                break;

            case R.id.nav_completed:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GridViewFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // Opens the side navigation bar
    public void onOpenPressed(View v) {
            drawer.openDrawer(GravityCompat.START);
    }

    // Closes the side navigation bar
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
