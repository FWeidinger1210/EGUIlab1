package com.fweidinger.egui_lab1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fweidinger.egui_lab1.data.models.ModelTipHistory;

/**
 * TipDbHelper inherits from SQLiteOpenHelper. It manages all access to the SQLiteDatabase.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * This string marks the database version. In case of changing the database schema or making other significant changes,
     * this should be increment.
     */
    public static final int DATABASE_VERSION = 4;
    /**
     * The name of the database in the file storage system.
     */
    public static final String DATABASE_NAME = "TipHistory.db";


    /**
     * Base Constructor, notifies of path to the database and its version
     * @param context the application context to use for locating paths to the database
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables happens:
     * This method executes the SQL Statement specified in the SQL_CREATE_ENTRIES of the ModelTipHistory
     * @param db the SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ModelTipHistory.getSqlCreateEntries());
    }


    /**
     * If the version of the database in changed, than all tables inside the database are dropped. and the onCreate method is
     * called to start of with a clean database, that holds the initial tables.
     * @param db the SQLiteDatabase
     * @param oldVersion the old version of the database
     * @param newVersion the new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ModelTipHistory.TipEntry.getTableName());
        onCreate(db);
    }

    /**
     * This method will insert a single entry into the database.
     * It uses contentValues to store the value of each row element.
     * @param tip       The tip amount calculated by the application
     * @param location  the location specified by the user
     * @param timestamp the date represented as unix ms
     * @return true: successful insertion, false: an error occurred
     */
    public boolean insertEntry(float tip, String location, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameTip(), tip);
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameLocation(), location);
        contentValues.put(ModelTipHistory.TipEntry.getColumnNameDate(), timestamp);
        return db.insert(ModelTipHistory.TipEntry.getTableName(), null, contentValues) >= 0;
    }

    /**
     * This method returns all columns in the tipHistory database in descending order of _ID.
     * (-> Latest entry will be returned first.)
     * @return a cursor object pointing before the first element found (index = -1)
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(ModelTipHistory.TipEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ModelTipHistory.TipEntry._ID + " DESC"
        );
    }

}

