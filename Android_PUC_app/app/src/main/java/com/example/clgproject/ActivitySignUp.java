package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActivitySignUp extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    }

    public void page(View view) {
        int selectedID = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedID);
        String radioText = radioButton.getText().toString();


    }

    public void SignUpBack(View view) {
        Intent back = new Intent(ActivitySignUp.this, MainActivity.class);
        startActivity(back);
    }

    public void SignUp(View view) {
        Intent signin = new Intent(ActivitySignUp.this, ActivitySignIn.class);
        startActivity(signin);
        Toast.makeText(getBaseContext(),"SignUp completed Please login",Toast.LENGTH_SHORT).show();
    }
}