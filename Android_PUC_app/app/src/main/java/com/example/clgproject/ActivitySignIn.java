package com.example.clgproject;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySignIn extends ActivitySignUp {

    private static final String TAG = "SignIn";

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
//        String radioText = checkButton();
        Intent publicUser = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
        Intent policeUser = new Intent(ActivitySignIn.this, ActivityPoliceUser.class);

        //Checking the Internet Connection
        if(!isConnected(this)){
            Log.d(TAG, "isConnected: "+ isConnected(this));
            showCustomDialog();
        }
        else if (name.equals("Akash") && key.equals("1234")){
            if (radioText == "Public"){

                startActivity(publicUser);
                Toast.makeText(getBaseContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
            }
            else{

                startActivity(policeUser);
                Toast.makeText(getBaseContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
            }


        }
        else if (name.equals("Akash") || key.equals("1234")){
            Toast.makeText(getBaseContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(),"Wrong Username and Password",Toast.LENGTH_SHORT).show();
        }


    }



    //   Check the internet Connection
    private boolean isConnected(ActivitySignIn activitySignIn) {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignIn.this);
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

    public void LogInBack(View view) {
        Intent start = new Intent(ActivitySignIn.this, MainActivity.class);
        startActivity(start);
    }
}
