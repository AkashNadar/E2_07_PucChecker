package com.example.clgproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ActivityPoliceSignIn extends AppCompatActivity {

    TextView uEmail, uPass;//uEmail means Inputing phone No

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        uEmail = (TextView) findViewById(R.id.uname);
        uPass = (TextView) findViewById(R.id.upass);

        reference = FirebaseDatabase.getInstance().getReference("PoliceUser");
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null)
//            userID = user.getUid();
//        else{
//            Toast.makeText(this, "No Account Exists in the DataBase", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(ActivityPoliceSignIn.this, MainActivity.class);
//            startActivity(intent);
//        }




        final Button signIn = (Button) findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected(ActivityPoliceSignIn.this)){
//            Log.d(TAG, "isConnected: "+ isConnected(this));
                    showCustomDialog();
                }
//
                else {
                    performLogin();
                }
            }
        });

        final Button menu = (Button) findViewById(R.id.BackButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPoliceSignIn.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isConnected(ActivityPoliceSignIn activityPoliceSignIn) {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPoliceSignIn.this);
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

    private void performLogin() {
        String mail = uEmail.getText().toString().trim();
        String key = uPass.getText().toString().trim();

        if(mail.isEmpty()){
            uEmail.setError("Email is Required!");
            uEmail.requestFocus();
            return;
        }

//        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
//            uEmail.setError("Please enter a valid Email");
//            uEmail.requestFocus();
//            return;
//        }

        if(key.isEmpty()){
            uPass.setError("Password is Required!");
            uPass.requestFocus();
            return;
        }

        if(key.length() < 8){
            uPass.setError("Min password length is 8 characters!");
            uPass.requestFocus();
            return;
        }

        Query checkUser = reference.orderByChild("phoneNo").equalTo(mail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    uEmail.setError(null);

                    String passFromDB = snapshot.child(mail).child("pass").getValue(String.class);

                    if(passFromDB.equals(key)){

                        Intent intent = new Intent(ActivityPoliceSignIn.this, ActivityPoliceUser.class);
                        startActivity(intent);
                        Toast.makeText(ActivityPoliceSignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        uPass.setError("Wrong Password!");
                        uPass.requestFocus();
                    }
                }else{
                    uEmail.setError("No Such User Exist");
                    uEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                PoliceUserDB policeProfile = snapshot.getValue(PoliceUserDB.class);
//
//                if (policeProfile != null){
//                    String policeEmail = policeProfile.email;
//                    String policePass = policeProfile.pass;
//
//                    if (mail.equals(policeEmail) && key.equals(policePass)){
//                        Intent intent = new Intent(ActivityPoliceSignIn.this, ActivityPoliceUser.class);
//                        startActivity(intent);
//                        Toast.makeText(ActivityPoliceSignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(ActivityPoliceSignIn.this, "Account Do not Exists!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });







    }




}