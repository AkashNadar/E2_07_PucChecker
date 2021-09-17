package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void UserSignIn(View view) {
        Intent SignIn = new Intent(MainActivity.this, ActivitySignIn.class);
        startActivity(SignIn);
    }


    public void UserSignUp(View view) {
        Intent SignUp = new Intent(MainActivity.this, ActivitySignUp.class);
        startActivity(SignUp);
    }
}