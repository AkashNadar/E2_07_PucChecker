package com.example.clgproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ActivityPublicUser extends AppCompatActivity {

    Button addProfile,signOut;
    ListView userlist;
    DBCarProfile DB;
    ArrayList<String> listitem;
    ArrayAdapter adapter;
    TextView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user);

        addProfile = findViewById(R.id.addProfileId);
        signOut = findViewById(R.id.signOutId);

        listitem = new ArrayList<>();
        userlist = findViewById(R.id.disp);
        DB = new DBCarProfile(this);

        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapInt = new Intent(ActivityPublicUser.this, ActivityMap.class);
                Toast.makeText(ActivityPublicUser.this, "Getting Locations", Toast.LENGTH_SHORT).show();
                startActivity(mapInt);
            }
        });

        viewdata();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text1 = userlist.getItemAtPosition(i).toString();

                Intent s = getIntent();
                String phNo = s.getStringExtra("phno");
                Cursor res = DB.getdatatolist(text1.substring(18,28));

                if (res.getCount() == 0) {
                    Toast.makeText(ActivityPublicUser.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                while(res.moveToNext()) {
                    Intent next = new Intent(getApplicationContext(), ActivityDisplay.class);
//                    next.putExtra("carname ",crname);
//                    res.moveToNext();

                    next.putExtra("carnoplate",res.getString(2));
                    startActivity(next);
//                    Toast.makeText(Display.this, "Name:" + res.getString(0) + "\n"+"Contact No:" + res.getString(1) + "\n"+"Date of Birth:" + res.getString(2) + "\n", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Display.this, "Contact No:" + res.getString(1) + "\n", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Display.this, "Date of Birth:" + res.getString(2) + "\n", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(ActivityPublicUser.this, ActivityProfile.class);
                Intent s = getIntent();
                String phno = s.getStringExtra("phno");
                toProfile.putExtra("phno", phno);
                startActivity(toProfile);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent mainActivity = new Intent(ActivityPublicUser.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }



    private void viewdata() {
        Intent i = getIntent();
        String pwdtxt = i.getStringExtra("phno");
        Cursor cursor = DB.getdata(pwdtxt);

        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                listitem.add("Car number plate: "+cursor.getString(2));
            }
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listitem);
            userlist.setAdapter(adapter);
        }
    }
}