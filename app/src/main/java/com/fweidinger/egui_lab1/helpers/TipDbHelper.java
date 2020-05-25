package com.fweidinger.egui_lab1.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fweidinger.egui_lab1.models.ModelTipHistory;

/**
 * TipDbHelper manages access to the TipHistory database.
 */
public class TipDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TipHistory.db";


    public TipDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ModelTipHistory.getSqlCreateEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ModelTipHistory.TipEntry.getTableName());
        onCreate(db);
    }

    /**
     * @param tip       The tip amount calculated by the application
     * @param location  the location specified by the user
     * @param timestamp the date represented as unix ms
     * @return true: succesfull insertion into the database
     */
    public boolean insertEntry(float tip, String location, int timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameTip(), tip);
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameLocation(), location);
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameDate(), timestamp);
        return db.insert(ModelTipHistory.TipEntry.getTableName(), null, contentValues) >= 0;
    }

    /**
     * @param id The id matching
     * @return a cursor pointing before the first element found (index = -1)
     */
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + ModelTipHistory.TipEntry.getTableName(), null);
    }

}

