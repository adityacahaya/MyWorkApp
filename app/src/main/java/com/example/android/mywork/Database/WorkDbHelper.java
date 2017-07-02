package com.example.android.mywork.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME       = "worklist.db";
    private static final int    DATABASE_VERSION    = 1;

    public WorkDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WORKLIST_TABLE = "CREATE TABLE " +
                WorkContract.WorkEntry.TABLE_NAME       + " (" +
                WorkContract.WorkEntry._ID              + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WorkContract.WorkEntry.COLUMN_NAME_USER + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_DATE      + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_AKTIVITAS + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_KATEGORI  + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_NOTE      + " TEXT, " +
                WorkContract.WorkEntry.COLUMN_ID_USER   + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_LATITUDE  + " TEXT, " +
                WorkContract.WorkEntry.COLUMN_LONGITUDE + " TEXT" +
                "); ";
        db.execSQL(SQL_CREATE_WORKLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkContract.WorkEntry.TABLE_NAME);
        onCreate(db);
    }
}
