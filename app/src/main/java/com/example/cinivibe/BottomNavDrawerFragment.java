package com.example.cinivibe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomNavDrawerFragment extends BottomSheetDialogFragment {

    public static ArrayList<String> extraMenuNames;
    public NavigationView navigationView;
    public View view;
    public static SharedPreferences sharedPreferences;
    private static Context context = null;

    public BottomNavDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bottom_nav_drawer, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment

        context = getActivity();
        sharedPreferences = MainHomeScreenActivity.getSharedPreferences();
        fillArraylist();

        navigationView = view.findViewById(R.id.navigationView);

        hideItem(extraMenuNames, navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            // get object of menuItem selected
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.favoritesMenu:
                        Toast.makeText(getActivity(), "Open Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wishlistMenu:
                        Toast.makeText(getActivity(), "Open Wishlist", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.addMenu:
                        Intent intent = new Intent(getActivity(), CustomPopupActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });
    }

    //    https://stackoverflow.com/questions/31954993/hide-a-navigation-drawer-menu-item-android
    public void hideItem(ArrayList<String> extraMenuNames, NavigationView navigationView) {
        Menu nav_Menu = navigationView.getMenu();
        if (extraMenuNames.isEmpty()) {
            nav_Menu.findItem(R.id.extra1BottomMenu).setVisible(false);
            nav_Menu.findItem(R.id.extra2BottomMenu).setVisible(false);
            nav_Menu.findItem(R.id.extra3BottomMenu).setVisible(false);
        }
        else {
            if (extraMenuNames.size() == 1){
                nav_Menu.findItem(R.id.extra1BottomMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2BottomMenu).setVisible(false);
                nav_Menu.findItem(R.id.extra3BottomMenu).setVisible(false);
            }
            else if (extraMenuNames.size() == 2) {
                nav_Menu.findItem(R.id.extra1BottomMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2BottomMenu).setTitle(extraMenuNames.get(1));
                nav_Menu.findItem(R.id.extra3BottomMenu).setVisible(false);
            }
            else {
                nav_Menu.findItem(R.id.extra1BottomMenu).setTitle(extraMenuNames.get(0));
                nav_Menu.findItem(R.id.extra2BottomMenu).setTitle(extraMenuNames.get(1));
                nav_Menu.findItem(R.id.extra3BottomMenu).setTitle(extraMenuNames.get(2));
            }
        }
    }


    public void fillArraylist() {

        if (extraMenuNames == null) {
            extraMenuNames = new ArrayList<>();
        }

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Set", null);


        if (json.equalsIgnoreCase("[null]")) {
            System.out.println("rip");
            Toast.makeText(context,"There is something wrong",Toast.LENGTH_LONG).show();
        } else {

            Type type = new TypeToken<List<String>>() {
            }.getType();
            extraMenuNames = gson.fromJson(json, type);
        }

    }

}
