package com.example.clgproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.core.Tag;

import org.jetbrains.annotations.NotNull;

public class ActivitySignIn extends AppCompatActivity {

//    private static final String TAG = "SignIn";

    EditText uEmail, uPass;
    Button clk;
//    String radioText = "Public";
    String chRadio;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    FirebaseDatabase rootNode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        uEmail = (EditText) findViewById(R.id.uname);
        uPass = (EditText) findViewById(R.id.upass);
        clk = (Button) findViewById(R.id.SignInButton);

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("PublicUser");
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null)
//            userID = user.getUid();
//        else{
//            Toast.makeText(this, "No Account Exists in the DataBase", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(ActivitySignIn.this, MainActivity.class);
//            startActivity(intent);
//        }

    }

    public void SignIn(View view) {


//        String name = uname.getText().toString();
//        String key = upass.getText().toString();
//        String radioText = checkButton();
//        Intent publicUser = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
//        Intent policeUser = new Intent(ActivitySignIn.this, ActivityPoliceUser.class);

        //Checking the Internet Connection
        if(!isConnected(this)){
//            Log.d(TAG, "isConnected: "+ isConnected(this));
            showCustomDialog();
        }
//
        else {
            performLogin();
        }


    }

    private void performLogin() {
        String mail = uEmail.getText().toString().trim();
        String key = uPass.getText().toString().trim();

        if(mail.equals("admin") && key.equals("admin")){
            Intent intent = new Intent(ActivitySignIn.this, ActivityAdmin.class);
            startActivity(intent);
            Toast.makeText(ActivitySignIn.this, "You are in Admin Page", Toast.LENGTH_SHORT).show();
        }
        else{

            if(mail.isEmpty()){
            uEmail.setError("Email is Required!");
            uEmail.requestFocus();
            return;
            }

//            if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
//                uEmail.setError("Please enter a valid Email");
//                uEmail.requestFocus();
//                return;
//            }

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
                        String phoneNoFromDB = snapshot.child(mail).child("phoneNo").getValue(String.class);

                        if(passFromDB.equals(key)){

                            Intent intent = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
                            Intent intentProfile = new Intent(ActivitySignIn.this, ActivityProfile.class);
                            intent.putExtra("phno", phoneNoFromDB);
                            startActivity(intent);
                            Toast.makeText(ActivitySignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
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

//            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                    PublicUserDB publicProfile = snapshot.getValue(PublicUserDB.class);
//
//                    if (publicProfile != null){
//                        String publicEmail = publicProfile.email;
//                        String publicPass = publicProfile.pass;
//
//                        if (mail.equals(publicEmail) && key.equals(publicPass)){
//                            Intent intent = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
//                            startActivity(intent);
//                            Toast.makeText(ActivitySignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(ActivitySignIn.this, "Account Do not Exists!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                }
//            });



//            mAuth.signInWithEmailAndPassword(mail,key).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//
//                    if(task.isSuccessful()){
//
//                        Intent publicUser = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
//                        startActivity(publicUser);
//                        Toast.makeText(ActivitySignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Toast.makeText(ActivitySignIn.this, "Failed to SignIn! Please check your credentials", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });

        }

//        if(mail.isEmpty()){
//            uEmail.setError("Email is Required!");
//            uEmail.requestFocus();
//            return;
//        }
//
//        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
//            uEmail.setError("Please enter a valid Email");
//            uEmail.requestFocus();
//            return;
//        }
//
//        if(key.isEmpty()){
//            uPass.setError("Password is Required!");
//            uPass.requestFocus();
//            return;
//        }
//
//        if(key.length() < 8){
//            uPass.setError("Min password length is 8 characters!");
//            uPass.requestFocus();
//            return;
//        }
//
//
//        mAuth.signInWithEmailAndPassword(mail,key).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//
//                if(task.isSuccessful()){
//
//                    Intent publicUser = new Intent(ActivitySignIn.this, ActivityPublicUser.class);
//                    startActivity(publicUser);
//
//                }else {
//                    Toast.makeText(ActivitySignIn.this, "Failed to SignIn! Please check your credentials", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

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
