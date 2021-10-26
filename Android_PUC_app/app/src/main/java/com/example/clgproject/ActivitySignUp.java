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
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ActivitySignUp extends AppCompatActivity {


    EditText name,mb_no,email,pass,conf_pass;

    RadioGroup radioGroup;
    RadioButton radioButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private DatabaseReference reference;
    boolean existCheck;

//    private FirebaseDatabase db = FirebaseDatabase.getInstance();
//    private DatabaseReference root = db.getReference().child("PublicUser");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.nameEt);
        mb_no = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.emailEt);
        pass = findViewById(R.id.passEt);
        conf_pass = findViewById(R.id.confPassEt);
//        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("PublicUser");


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
//        String radioText = checkButton();



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

        Query checkUser = reference.orderByChild("phoneNo").equalTo(phnoVal);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    existCheck = false;

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        if (existCheck == false){
            mb_no.setError("Phone no already Exists!");
            existCheck = true;
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
        PublicUserDB publicUserDB = new PublicUserDB(nameVal, phnoVal, emailVal, passVal);
        FirebaseDatabase.getInstance().getReference("PublicUser").child(phnoVal).setValue(publicUserDB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){


                    //redirect to signin layout
                    Intent signIn = new Intent(ActivitySignUp.this, ActivitySignIn.class);
                    startActivity(signIn);

                }else{
                    Toast.makeText(ActivitySignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });



        //FireBase
//        mAuth.createUserWithEmailAndPassword(emailVal, passVal)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            PublicUserDB user = new PublicUserDB(nameVal,phnoVal,emailVal,passVal);
//
//                            FirebaseDatabase.getInstance().getReference("PublicUser")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//
//
//                                        //redirect to signin layout
//                                        Intent signIn = new Intent(ActivitySignUp.this, ActivitySignIn.class);
//                                        startActivity(signIn);
//
//                                    }else{
//                                        Toast.makeText(ActivitySignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
//                        }else {
//                            Toast.makeText(ActivitySignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

//        HashMap<String, String> userMap = new HashMap<>();
//
//        userMap.put("Name", nameVal);
//        userMap.put("Ph_no", phnoVal);
//        userMap.put("Email", emailVal);
//        userMap.put("Password", passVal);
//        userMap.put("Radio", radioText);
//
//        root.push().setValue(userMap);


        return true;
    }


    // RadioButton text

//    public String checkButton() {
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = (RadioButton) findViewById(radioId);
//        return radioButton.getText().toString();
//
//    }

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
                Toast.makeText(ActivitySignUp.this, "User have been Registered Sucessfully!", Toast.LENGTH_LONG).show();
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