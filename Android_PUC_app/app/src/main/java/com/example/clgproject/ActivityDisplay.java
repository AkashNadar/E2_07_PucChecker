package com.example.clgproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ActivityDisplay extends AppCompatActivity {

    TextView crnametxt, crnotxt;
    Button finish, updatepro, deletepro;
    DBCarProfile DB;
    ImageView imagepuc;
    int upimg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        crnametxt = findViewById(R.id.namel);
        crnotxt = findViewById(R.id.contactl);
        finish = findViewById(R.id.close);
        deletepro = findViewById(R.id.delpro);
        updatepro = findViewById(R.id.updpro);
        DB = new DBCarProfile(this);
        imagepuc = findViewById(R.id.pucimage);

        Intent i = getIntent();
        String contact = i.getStringExtra("carnoplate");
        Cursor res = DB.getdatatolist(contact);
        if (res.getCount() == 0) {
            Toast.makeText(ActivityDisplay.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            res.moveToFirst();
            String crname = res.getString(1);
            crnametxt.setText(crname);
        }
//
        crnotxt.setText(contact);
        showimage();

//        finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        updatepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCamera();
                upimg = 1;
            }
        });

        deletepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkudeletedata = DB.deletedata(contact);
                if(checkudeletedata==true)
                    Toast.makeText(ActivityDisplay.this, "Car Profile Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ActivityDisplay.this, "Car Profile Not Deleted", Toast.LENGTH_SHORT).show();
                Intent drhomescreeen = new Intent(getApplicationContext(), ActivityPublicUser.class);
                startActivity(drhomescreeen);
//                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upimg==1){
                    Toast.makeText(ActivityDisplay.this, "Image Updated", Toast.LENGTH_SHORT).show();
                }
//                else{
//                    Toast.makeText(Display.this, "Image not updated", Toast.LENGTH_SHORT).show();
//                }
                Bitmap bitmap = ((BitmapDrawable) imagepuc.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                byte[] covertimg =  byteArrayOutputStream.toByteArray();
                Boolean checkupdatedata = DB.updateuserdata(contact,covertimg);
//                if(checkupdatedata==true)
//                    Toast.makeText(Display.this, "Entry Updated", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(Display.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void takeCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 1);
    }

    private byte[] convertimage(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    imagepuc.setImageBitmap(bitmapImage);
//                    byte[] bytesPP = convertimage(pucimage);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    imagepuc.setImageURI(selectedImageUri);
//                    byte[] bytesPP = convertimage(pucimage);
                }
                break;
        }
    }

    private void showimage(){
        Intent i = getIntent();
        String contact = i.getStringExtra("carnoplate");
        Cursor res = DB.getdatatolist(contact);
        res.moveToFirst();
        byte[] userimage = res.getBlob(3);;
        Bitmap photo = convertByteArrayToBitmap(userimage);
        imagepuc.setImageBitmap(photo);
    }

    private Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}