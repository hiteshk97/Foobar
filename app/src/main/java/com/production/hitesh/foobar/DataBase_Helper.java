package com.production.hitesh.foobar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hitesh on 3/30/18.
 */

public class DataBase_Helper extends SQLiteOpenHelper {
    public static  final String database_name = "message.db";
    public static  String table_name="android_metadata";


    public DataBase_Helper(Context context,String table_name,int version) {
        super(context, database_name, null,version);
        this.table_name = table_name;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        onCreate(sqLiteDatabase);

        version++;

    }

    public DataBase_Helper(Context context) {
        super(context,database_name, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ table_name + "(From_ Text,message Text,to_ Text,time_ Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("delete table if exists "+table_name);
        onCreate(sqLiteDatabase);

    }

    //inserting data
    public boolean inserData(String from,String message,String to,String time,String table_name){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("From_",from);
        contentValues.put("message",message);
        contentValues.put("to_",to);
        contentValues.put("time_",time);
        long result = sqLiteDatabase.insert(table_name,null,contentValues);
        if (result>0){
            return true;
        }
        else {
            return false;
        }

    }

    //get data
    public Cursor getData(String table_name,int i){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor cursor=null;
        if (i==0){
        cursor =sqLiteDatabase.rawQuery("select * from "+table_name,null);
        }
        else if (i==1){
            cursor =sqLiteDatabase.rawQuery("select * from "+table_name,null);
            cursor.moveToLast();

        }
        return cursor;

    }

    //get recent chats(table name)
    public Cursor getTable_name(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        return cursor;
    }


    //check db
    public boolean checkDb(){
        SQLiteDatabase checkDB=null;
        try {checkDB = SQLiteDatabase.openDatabase("/data/data/com.production.hitesh.sender/databases/message", null,
                SQLiteDatabase.OPEN_READONLY);
            checkDB.close();


        }
        catch (Exception e){

        }
        return checkDB != null;

    }
}
