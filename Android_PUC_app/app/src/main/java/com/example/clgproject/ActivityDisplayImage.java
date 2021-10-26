package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityDisplayImage extends AppCompatActivity {

    ImageView pucimg;
    DBCarProfile DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        DB = new DBCarProfile(this);
        pucimg = findViewById(R.id.img);

        showimage();


    }

    private void showimage(){
        Intent dis = getIntent();
        String dispimg1 = dis.getStringExtra("noPlate");
        Cursor res = DB.getdatatolist(dispimg1);
        res.moveToFirst();
        byte[] userimage = res.getBlob(3);;
        Bitmap photo = convertByteArrayToBitmap(userimage);
        pucimg.setImageBitmap(photo);
    }

    private Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}