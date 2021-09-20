package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySignUp extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
//    String radioText = checkButton();
    String radioText = "Police";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
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

            Intent signin = new Intent(ActivitySignUp.this, ActivitySignIn.class);
            startActivity(signin);
            Toast.makeText(getBaseContext(),"SignUp completed Please login",Toast.LENGTH_SHORT).show();
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