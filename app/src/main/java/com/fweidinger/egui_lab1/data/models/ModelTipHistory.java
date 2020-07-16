package com.fweidinger.egui_lab1.data.models;

import android.provider.BaseColumns;

/**
 * This class implements models for the TipHistory SQL database. It does not deal with the database logic itself, it only
 * serves as a means for cleaner abstractions purposes.
 */
public class ModelTipHistory {

    /**
     * Constructor is set to private to prevent users from accidentally instantiating this class.
     */
    private ModelTipHistory() {
    }

    /**
     * TipEntry defines the table that holds the information inside the SQLiteDatabase.
     * TipEntry implements the columns for the SQL database by implementing the BaseColumns interface.
     * The columns are:
     * 1. Tip
     * 2. Date
     * 3. Location
     */
    public static class TipEntry implements BaseColumns {
        public static String getTableName() {
            return TABLE_NAME;
        }

        public static String getColumnNameTip() {
            return COLUMN_NAME_TIP;
        }

        public static String getColumnNameDate() {
            return COLUMN_NAME_DATE;
        }

        public static String getColumnNameLocation() {
            return COLUMN_NAME_LOCATION;
        }

        /**
         * The name of the table that contains TipEntries.
         */
        public static final String TABLE_NAME = "tiphistory";
        /**
         * Column in the SQLiteDatabase holding the TIP
         */
        public static final String COLUMN_NAME_TIP = "tip";
        /**
         * Column in the SQLiteDatabase holding the DATE.
         */
        public static final String COLUMN_NAME_DATE = "date";
        /**
         * Column in the SQLiteDatabase holding the LOCATION.
         */
        public static final String COLUMN_NAME_LOCATION = "location";
    }

    /**
     * Getter for the SQL_CREATE_ENTRIES that define the table
     * @return SQL_CREATE_ENTRIES
     */
    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    /**
     * This String represents a single SQL statement that will enable a database client to create the necessary table and columns
     * to hold the entries implemented in this model class.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TipEntry.TABLE_NAME + " (" +
                    TipEntry._ID + " INTEGER PRIMARY KEY," +
                    TipEntry.COLUMN_NAME_LOCATION + " TEXT," +
                    TipEntry.COLUMN_NAME_DATE + " TEXT," +
                    TipEntry.COLUMN_NAME_TIP + " FLOAT)";

}
