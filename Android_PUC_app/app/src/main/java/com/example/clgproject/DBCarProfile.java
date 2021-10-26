package com.example.clgproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCarProfile extends SQLiteOpenHelper {

    public DBCarProfile(Context context) {
        super(context, "Driverdata1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table CarDetails(password TEXT, carname TEXT, carnoplate TEXT, puccertificate BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists CarDetails");
    }

    public Boolean insertcardata(String pwd, String carname, String carnoplate, byte[] imge)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", pwd);
        contentValues.put("carname", carname);
        contentValues.put("carnoplate", carnoplate);
        contentValues.put("puccertificate", imge);

//        contentValues.put("mobileno", puc);
        long result=DB.insert("CarDetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean deletedata (String cno)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CarDetails where carnoplate = ?", new String[]{cno});
        if (cursor.getCount() > 0) {
            long result = DB.delete("CarDetails", "carnoplate=?", new String[]{cno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Boolean updateuserdata(String name,  byte[] dob){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("puccertificate", dob);
        Cursor cursor = DB.rawQuery("Select * from CarDetails where carnoplate = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("CarDetails", contentValues, "carnoplate=? ", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}

    public Cursor getdata (String pwd)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CarDetails where password = ?", new String[]{pwd});
        return cursor;

    }

    public Cursor getdatatolist (String cn)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CarDetails where carnoplate = ?", new String[]{cn});
        return cursor;

    }

    public byte[] getImage(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select puccertificate from CarDetails where carnoplate = ?", new String[]{name});
        cursor.moveToNext();
        byte[] bitmap = cursor.getBlob(3);
        return bitmap;
    }

}
