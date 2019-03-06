package com.example.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_COST;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_DATE;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_DESC;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_LABEL;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_NAME;
import static com.example.inventory.InventoryContract.InventoryEntry.TABLE_NAME;

public class DatabaseHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory_data.db";

    public DatabaseOpenHelper openHelper;
    public SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    protected class DatabaseOpenHelper extends SQLiteOpenHelper {
        DatabaseOpenHelper(Context context) {
            // pass name (pft_test_records.db) and version number (1) as super
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase database) {
            // execSQL actually creates the schema of the db we defined in the
            // method above
            database.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                    + COLUMN_TRANS_NAME + " TEXT, "
                    + COLUMN_TRANS_DESC + " TEXT, "
                    + COLUMN_TRANS_COST + " INTEGER, "
                    + COLUMN_TRANS_LABEL + " INTEGER, "
                    + COLUMN_TRANS_DATE + " TEXT)");

        }

        public void onUpgrade(SQLiteDatabase database, int oldVersion,
                              int newVersion) {
            // drops table if exists, and then calls onCreate which implements
            // our new schema
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(database);
        }

        public void insertTransaction(String name,String desc, String cost, String label, String date){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues value= new ContentValues();

            value.put(COLUMN_TRANS_NAME, name);
            value.put(COLUMN_TRANS_DESC,desc);
            value.put(COLUMN_TRANS_COST,cost);
            value.put(COLUMN_TRANS_LABEL,label);
            value.put(COLUMN_TRANS_DATE,date);
            db.insert(TABLE_NAME,null,value);


        }

        public void deleteTransaction(String name) {
            database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANS_NAME + "= '" + name + "'");
        }

        public void onDowngrade(SQLiteDatabase database, int oldVersion,
                                int newVersion) {
            onUpgrade(database, oldVersion, newVersion);
        }
    }
}
