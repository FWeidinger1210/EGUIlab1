package com.fweidinger.egui_lab1.models;

import android.provider.BaseColumns;

/**
 * Implements models for the TipHistory SQL database.
 */
public class ModelTipHistory {

    /**
     * constructor is set to private to prevent users from accidentally instantiating this class.
     */
    private ModelTipHistory() {
    }

    /**
     * TipEntry implements the Columns for the SQL Database  by implementing the BaseColumns interface
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

        public static final String TABLE_NAME = "tiphistory";
        public static final String COLUMN_NAME_TIP = "tip";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_LOCATION = "location";
    }

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TipEntry.TABLE_NAME + " (" +
                    TipEntry._ID + " INTEGER PRIMARY KEY," +
                    TipEntry.COLUMN_NAME_LOCATION + " TEXT," +
                    TipEntry.COLUMN_NAME_DATE + " TEXT," +
                    TipEntry.COLUMN_NAME_TIP + " FLOAT)";


}
