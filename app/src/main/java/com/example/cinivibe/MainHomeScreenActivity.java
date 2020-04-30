package com.example.cinivibe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainHomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Creates a global drawer object
    private DrawerLayout drawer;
    public static final String EXTRA_NAME = "name";
    public static ArrayList<String> extraMenuNames;
    public IndividualMovieFragment individualMovieFragment;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        individualMovieFragment = new IndividualMovieFragment();
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        loadArraylist();
        updateArraylist();

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
        hideItem(extraMenuNames,navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (getIntent().getStringExtra("individualMovieFrag") != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    individualMovieFragment).commit();
        }
        else {
            // Fills the fragment container with the Main Home Screen Fragment at the start of this activity
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainHomeScreenFragment()).commit();
        }
    }

    public void loadArraylist() {

        if (extraMenuNames == null) {
            extraMenuNames = new ArrayList<>();
            sharedPreferences.edit().clear().apply();
        }

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Set", "");


        if (json.isEmpty()) {
            Toast.makeText(MainHomeScreenActivity.this,"There is something wrong",Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> arrPackageData = gson.fromJson(json, type);
            for (int i = 0; i < arrPackageData.size(); i++) {
                extraMenuNames.set(i, arrPackageData.get(i));
            }
            extraMenuNames.remove(0);
        }

    }

    public void updateArraylist() {


        extraMenuNames.add(getIntent().getStringExtra(this.EXTRA_NAME));
        Gson gson = new Gson();
        String json = gson.toJson(extraMenuNames);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Set",json );
        editor.commit();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("extraMenuNames", extraMenuNames);
        individualMovieFragment.setArguments(bundle);
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
            case R.id.extra1SideMenu:
                break;
            case R.id.extra2SideMenu:
                break;
            case R.id.extra3SideMenu:
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

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void hideItem(ArrayList<String> extraMenuNames, NavigationView navigationView) {
        Menu nav_Menu = navigationView.getMenu();
        if (extraMenuNames.isEmpty()) {
            nav_Menu.findItem(R.id.extra1SideMenu).setVisible(false);
            nav_Menu.findItem(R.id.extra2SideMenu).setVisible(false);
            nav_Menu.findItem(R.id.extra3SideMenu).setVisible(false);
        }
        else {
            if (extraMenuNames.size() == 1){
                nav_Menu.findItem(R.id.extra1SideMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2SideMenu).setVisible(false);
                nav_Menu.findItem(R.id.extra3SideMenu).setVisible(false);
            }
            else if (extraMenuNames.size() == 2) {
                nav_Menu.findItem(R.id.extra1SideMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2SideMenu).setTitle(extraMenuNames.get(1));
                nav_Menu.findItem(R.id.extra3SideMenu).setVisible(false);
            }
            else {
                nav_Menu.findItem(R.id.extra1SideMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2SideMenu).setTitle(extraMenuNames.get(1));
                nav_Menu.findItem(R.id.extra3SideMenu).setTitle(extraMenuNames.get(2));
            }
        }
    }

}
