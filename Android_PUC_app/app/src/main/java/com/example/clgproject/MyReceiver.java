 package com.example.clgproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

 public class MyReceiver extends BroadcastReceiver {

     private static final String Tag = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String actionString = intent.getAction();
        Toast.makeText(context, actionString, Toast.LENGTH_LONG).show();

        boolean isOn = intent.getBooleanExtra("state", false);
//        Log.d(Tag, "onReceive: Aeroplane mode is On: "+ isOn);
    }
}