package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Button addPolice = findViewById(R.id.addPoliceUser);
        addPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdmin.this, ActivityPoliceSignUp.class);
                startActivity(intent);
            }
        });

        final Button signOut = findViewById(R.id.signOutAdmin);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signOut = new Intent(ActivityAdmin.this, MainActivity.class);
                startActivity(signOut);
            }
        });
    }
}