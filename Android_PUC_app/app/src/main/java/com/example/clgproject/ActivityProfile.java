package com.example.clgproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityProfile extends AppCompatActivity {

    public static final int SELECT_PHOTO = 110;
    Button choose, addProfile, certificate;
    Uri uri;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        image = findViewById(R.id.pucImage);
        choose = findViewById(R.id.upload);
        addProfile = findViewById(R.id.addProfileId2);
        certificate = findViewById(R.id.download);


        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
            }
        });

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityProfile.this, "Profile Sucessfully Added ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityProfile.this, ActivityPublicUser.class);
                startActivity(intent);
            }
        });

        certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://vahan.parivahan.gov.in/puc/views/PucCertificate.xhtml";
                Intent cert = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(cert);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}