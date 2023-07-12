package com.example.recyclerview_contact_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class My_Database extends SQLiteOpenHelper {
    public My_Database(Context context) {
        super(context, "My_Contacts", null, 1);
        Log.d("AAA", "My_Database: Create Database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table ContactData(ID integer primary key autoincrement,NAME text,NUMBER text,IMGURI text)";
        db.execSQL(query);
        Log.d("AAA", "onCreate: Create table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(String name, String number, String imgPath) {
        String query="insert into ContactData(NAME,NUMBER,IMGURI)values('"+name+"','"+number+"','"+imgPath+"')";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
    }

    public Cursor ShowData() {
        String query="select * from ContactData";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }

    public void updateContact(int id, String name, String number) {
        String query="upDate ContactData set NAME='"+name+"',NUMBER='"+number+"'where ID="+id+"";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
    }

    public void DeleteData(int id) {
        String query="delete from ContactData where ID="+id+"";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
    }
}
