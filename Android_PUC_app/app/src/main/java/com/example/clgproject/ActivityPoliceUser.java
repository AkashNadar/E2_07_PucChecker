package com.example.clgproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ActivityPoliceUser extends AppCompatActivity {

    public static final int CAMERA_PER_CODE = 101;
    public static final int CAMERA_REQ_CODE = 102;
    ImageView imageView;
    TextView textView;
    Bitmap image;
    Button signOut, detect;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_user);
        imageView = findViewById(R.id.imageId);
        textView = findViewById(R.id.textView);
        signOut = findViewById(R.id.signOutPolice);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent main = new Intent(ActivityPoliceUser.this, MainActivity.class);
                startActivity(main);
            }
        });

        detect = findViewById(R.id.imageTOText);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTextFromImage();
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void clickPlate(View view) {
        //image to text

        

        // Checking Camera Permission
        
        askCameraPermission();

        



    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PER_CODE);
        }else
            openCamera();
            textView.setText("");
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PER_CODE) {
            // true than user has given permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
//        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
//        Open the camera
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraActivityResultLauncher.launch(camera);
            startActivityForResult(camera, CAMERA_REQ_CODE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQ_CODE){
            image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);

        }
    }

    private void detectTextFromImage() {

        if(imageView.getDrawable() == null){
            Toast.makeText(this, "No image found to detect", Toast.LENGTH_SHORT).show();
        }else {


            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(image);
            FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
            firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    displayTextFromImage(firebaseVisionText);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ActivityPoliceUser.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }





    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {

        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if (blockList.size() == 0){
            Toast.makeText(this, "No Text Found In The Image", Toast.LENGTH_SHORT).show();
        }else {
            for(FirebaseVisionText.Block block : firebaseVisionText.getBlocks()){
                String text = block.getText();
                textView.setText(text);
            }
        }
    }

}