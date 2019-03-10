package com.example.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_COST;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_DATE;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_DESC;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_LABEL;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_LONG;
import static com.example.inventory.InventoryContract.InventoryEntry.COLUMN_TRANS_NAME;
import static com.example.inventory.InventoryContract.InventoryEntry.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory_data.db";



    public DatabaseHelper(Context context) {
        // pass name (pft_test_records.db) and version number (1) as super
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        // execSQL actually creates the schema of the db we defined in the
        // method above
        database.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TRANS_NAME + " TEXT, "
                + COLUMN_TRANS_DESC + " TEXT, "
                + COLUMN_TRANS_COST + " TEXT, "
                + COLUMN_TRANS_LABEL + " TEXT, "
                + COLUMN_TRANS_DATE + " TEXT, "
                + COLUMN_TRANS_LONG + "TEXT)");

    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        // drops table if exists, and then calls onCreate which implements
        // our new schema
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public void onDowngrade(SQLiteDatabase database, int oldVersion,
                            int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }


    public static ContentValues insertTransaction(String name, String desc, String cost, String label, String date, Date d) {
        ContentValues value = new ContentValues();

        value.put(COLUMN_TRANS_NAME, name);
        value.put(COLUMN_TRANS_DESC, desc);
        value.put(COLUMN_TRANS_COST, cost);
        value.put(COLUMN_TRANS_LABEL, label);
        value.put(COLUMN_TRANS_DATE, date);
        value.put(COLUMN_TRANS_LONG, d.getTime());
        return value;


    }



}
