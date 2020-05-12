package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomPopupActivity extends AppCompatActivity {

    public EditText newCollectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_popup);

        /*https://www.youtube.com/watch?v=eX-TdY6bLdg
        * shows to create a custom pop up and includes animation
        */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.575), (int)(height*0.2));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        newCollectionName = (EditText)findViewById(R.id.newCollectionNameEditText);

    }

    public void createClick(View view) {
        String name = newCollectionName.getText().toString();

        Intent intent = new Intent(this, MainHomeScreenActivity.class);
        intent.putExtra(MainHomeScreenActivity.EXTRA_NAME, name);
        intent.putExtra("individualMovieFrag", "individualMovieFrag");
        startActivity(intent);
    }

}
