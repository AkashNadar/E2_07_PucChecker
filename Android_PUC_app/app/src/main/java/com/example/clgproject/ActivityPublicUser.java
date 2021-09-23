package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPublicUser extends AppCompatActivity {

    Button addProfile,signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user);

        addProfile = findViewById(R.id.addProfileId);
        signOut = findViewById(R.id.signOutId);

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(ActivityPublicUser.this, ActivityProfile.class);
                startActivity(toProfile);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ActivityPublicUser.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}