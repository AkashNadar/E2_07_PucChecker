package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyReceiver myReceiver = new MyReceiver();
    TextView policeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        policeText = findViewById(R.id.policeView);
        policeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,ActivityPoliceSignIn.class);
                startActivity(intent);
            }
        });

    }

    public void UserSignIn(View view) {
        Intent SignIn = new Intent(MainActivity.this, ActivitySignIn.class);
        startActivity(SignIn);

    }


    public void UserSignUp(View view) {
        Intent SignUp = new Intent(MainActivity.this, ActivitySignUp.class);
        startActivity(SignUp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myReceiver);
    }
}