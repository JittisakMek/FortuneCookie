package com.egci428.fortunecookie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mek on 16/11/2559.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    public static final String TABLE_COOKIE = "cookie_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMMENT= "count";

    private static final String DATABASE_NAME = "Cookie.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_COOKIE + "(" + COLUMN_ID  + " integer primary key autoincrement, " + COLUMN_DATE + " text," + COLUMN_COMMENT + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MySQLiteHelper","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE); //create new database
        Log.d("MySQLiteHelper","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COOKIE);
        onCreate(db);
    }

    public void insertData(MySQLiteHelper msh, String date, String comment){
        SQLiteDatabase db = msh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_COMMENT, comment);
        long result = db.insert(TABLE_COOKIE, null , contentValues);
        Log.d("MySQLiteHelper","one row inserted");
   }

//    public Cursor getInformation(MySQLiteHelper msh){
//        SQLiteDatabase SQ = msh.getReadableDatabase();
//        String[] allcolumns = {}
//    }
}