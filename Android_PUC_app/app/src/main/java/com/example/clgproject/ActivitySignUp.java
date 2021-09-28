package com.example.clgproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ActivitySignUp extends AppCompatActivity {


    EditText name,mb_no,email,pass,conf_pass;

    RadioGroup radioGroup;
    RadioButton radioButton;
//    String radioText = checkButton();
    String radioText = "Public";

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.nameEt);
        mb_no = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.emailEt);
        pass = findViewById(R.id.passEt);
        conf_pass = findViewById(R.id.confPassEt);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);


    }

    //Validation


    private boolean CheckAllFields(){
        String nameVal = name.getText().toString().trim();
        String phnoVal = mb_no.getText().toString().trim();
       // String checkPhno = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        String emailVal = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passVal = pass.getText().toString().trim();
        String confPassVal = conf_pass.getText().toString().trim();
        String radioText = checkButton();


        if(nameVal.isEmpty()){
            name.setError("Field cannot be empty");
            return false;
        }


        if(phnoVal.isEmpty()){
            mb_no.setError("Field cannot be empty");
            return false;
        }else if (mb_no.length() != 10){
            mb_no.setError("Enter a valid mobile number");
            return false;
        }


        if(emailVal.isEmpty()){
            email.setError("Field cannot be empty");
            return false;
        }else if (!emailVal.matches(checkEmail)){
            email.setError("Invalid Email!");
            return false;
        }

        if(passVal.isEmpty()){
            pass.setError("Password cannot be empty");
            return false;
        }else if (passVal.length() < 8){
            pass.setError("Password must be minimum 8 characters");
            return false;
        }

        if(confPassVal.isEmpty()){
            conf_pass.setError("Field cannot be empty");
            return false;
        }else if (!passVal.equals(confPassVal)){
            conf_pass.setError("Password Not Matching");
            return false;
        }

        //FireBase

        HashMap<String, String> userMap = new HashMap<>();

        userMap.put("Name", nameVal);
        userMap.put("Ph_no", phnoVal);
        userMap.put("Email", emailVal);
        userMap.put("Password", passVal);
        userMap.put("Radio", radioText);

        root.push().setValue(userMap);
        return true;
    }


    // RadioButton text

    public String checkButton() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioId);
        return radioButton.getText().toString();

    }

    public void SignUpBack(View view) {
        Intent back = new Intent(ActivitySignUp.this, MainActivity.class);
        startActivity(back);
    }

    public void SignUp(View view) {


        //Checking the Internet Connection

        if(!isConnected(this)) {
            showCustomDialog();
        }
        else {
            
            if (CheckAllFields()){
                Intent signIn = new Intent(ActivitySignUp.this, ActivitySignIn.class);
                startActivity(signIn);
                Toast.makeText(getBaseContext(),"SignUp completed Please login",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();

        }

    }

    //   Check the internet Connection
    private boolean isConnected(ActivitySignUp activitySignUp) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;

        }
        else
            return false;

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignUp.this);
        builder.setMessage("Please connect to the internet to proceed futher")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


}