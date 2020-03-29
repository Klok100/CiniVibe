package com.example.cinivibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void login(View v){
        Intent intent = new Intent(this, MainHomeScreenActivity.class);
        startActivity(intent);
    }

    public void signUp(View v){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
