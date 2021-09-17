package com.example.clgproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignIn extends AppCompatActivity {

    EditText uname,upass;
    Button clk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        uname = (EditText) findViewById(R.id.uname);
        upass = (EditText) findViewById(R.id.upass);
        clk = (Button) findViewById(R.id.SignInButton);
    }

    public void SignIn(View view) {
        String name = uname.getText().toString();
        String key = upass.getText().toString();

        if (name.equals("Akash") && key.equals("1234")){
            Intent PublicUser = new Intent(ActivitySignIn.this, Docpage.class);
            startActivity(PublicUser);
            Toast.makeText(getBaseContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
        }
        else if (name.equals("Akash") || key.equals("1234")){
            Toast.makeText(getBaseContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(),"Wrong Username and Password",Toast.LENGTH_SHORT).show();
        }


    }

    public void LogInBack(View view) {
        Intent start = new Intent(ActivitySignIn.this, MainActivity.class);
        startActivity(start);
    }
}
