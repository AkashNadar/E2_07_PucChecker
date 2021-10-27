package com.example.clgproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityProfile extends AppCompatActivity {

    public static final int SELECT_PHOTO = 110;
    Button choose, addProfile, certificate;
    Uri uri;
    ImageView image;

    DBCarProfile DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EditText crname = (EditText) findViewById(R.id.carname);
        EditText crno = (EditText) findViewById(R.id.noplate);

        image = findViewById(R.id.pucImage);
        choose = findViewById(R.id.upload);
        addProfile = findViewById(R.id.addProfileId2);
//        certificate = findViewById(R.id.download);

        DB = new DBCarProfile(this);


        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, SELECT_PHOTO);

            takeCamera();


            }
        });

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ActivityProfile.this, "Profile Sucessfully Added ", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ActivityProfile.this, ActivityPublicUser.class);
//                startActivity(intent);

                if (crname.getText().toString().equals("")){
                    Toast.makeText(ActivityProfile.this, "Enter car name", Toast.LENGTH_SHORT).show();
                }
                else if (crno.getText().toString().equals("")){
                    Toast.makeText(ActivityProfile.this, "Enter car number plate", Toast.LENGTH_SHORT).show();
                }
                else {
                    String carname = crname.getText().toString();
                    String carno = crno.getText().toString();

                    Intent i = getIntent();
                    String pawd = i.getStringExtra("phno");
//                    Toast.makeText(Drprofile.this, pawd, Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
                    byte[] covertimg =  byteArrayOutputStream.toByteArray();

                    Boolean checkinsertdata = DB.insertcardata(pawd,carname,carno,covertimg);
                    if (checkinsertdata == true) {

                        Toast.makeText(ActivityProfile.this, "Record created successfully successfully", Toast.LENGTH_SHORT).show();
                        Intent drhomescreeen = new Intent(getApplicationContext(), ActivityPublicUser.class);
                        startActivity(drhomescreeen);
//
                    } else
                        Toast.makeText(ActivityProfile.this, "Account not registered", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        certificate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "https://vahan.parivahan.gov.in/puc/views/PucCertificate.xhtml";
//                Intent cert = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(cert);
//            }
//        });
    }

    //    Camera
    private void takeCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 1);
    }

    private byte[] convertimage(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    image.setImageBitmap(bitmapImage);
                    byte[] bytesPP = convertimage(image);
                }
                break;

        }
    }

//------------------------------------------------------------
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
//            uri = data.getData();
//            try{
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                image.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }





}