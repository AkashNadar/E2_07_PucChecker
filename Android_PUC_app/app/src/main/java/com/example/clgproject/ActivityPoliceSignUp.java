package com.example.clgproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ActivityPoliceSignUp extends AppCompatActivity {

    EditText name,mb_no,email,pass,policeId;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_sign_up);

        name = findViewById(R.id.pName);
        mb_no = findViewById(R.id.pNumber);
        email = findViewById(R.id.pEmailid);
        pass = findViewById(R.id.pPass);
        policeId = findViewById(R.id.policeIdView);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


    }

    private boolean CheckAllFields() {

        String nameVal = name.getText().toString().trim();
        String phnoVal = mb_no.getText().toString().trim();
        // String checkPhno = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        String emailVal = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passVal = pass.getText().toString().trim();
        String policeIdVal = policeId.getText().toString().trim();

        if (nameVal.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        }


        if (phnoVal.isEmpty()) {
            mb_no.setError("Field cannot be empty");
            return false;
        } else if (mb_no.length() != 10) {
            mb_no.setError("Enter a valid mobile number");
            return false;
        }


        if (emailVal.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!emailVal.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        }

        if (policeIdVal.isEmpty()) {
            policeId.setError("Field cannot be empty");
            return false;
        }

        if (passVal.isEmpty()) {
            pass.setError("Password cannot be empty");
            return false;
        } else if (passVal.length() < 8) {
            pass.setError("Password must be minimum 8 characters");
            return false;

        }



        PoliceUserDB policeUserDB = new PoliceUserDB(nameVal,phnoVal,emailVal,policeIdVal,passVal);
        FirebaseDatabase.getInstance().getReference("PoliceUser").child(phnoVal).setValue(policeUserDB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){


                    //redirect to signin layout
                    Intent intent = new Intent(ActivityPoliceSignUp.this, ActivityAdmin.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(ActivityPoliceSignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });

//        mAuth.createUserWithEmailAndPassword(emailVal, passVal)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            PoliceUserDB user = new PoliceUserDB(nameVal,phnoVal,emailVal,policeIdVal,passVal);
//
//                            FirebaseDatabase.getInstance().getReference("PoliceUser")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//
//
//                                        //redirect to admin layout
//                                        Intent intent = new Intent(ActivityPoliceSignUp.this, ActivityAdmin.class);
//                                        startActivity(intent);
//
//                                    }else{
//                                        Toast.makeText(ActivityPoliceSignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }else {
//                            Toast.makeText(ActivityPoliceSignUp.this, "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        return true;
    }


    public void Create(View view) {

        if (CheckAllFields()){
            Toast.makeText(ActivityPoliceSignUp.this, "User have been Registered Sucessfully!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityPoliceSignUp.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();

        }
    }

    public void Back(View view) {
    }
}