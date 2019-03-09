package com.example.inventory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PaymentsActivity extends AppCompatActivity {

    private static final String TAG = "Payments";
    private ArrayList<Transactions> transactions;


//    /** SQL-related variables. */
//    private DatabaseHelper.DatabaseOpenHelper dbHelper;
//    private SQLiteDatabase sql_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        transactions = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);

        sampleData();

        PaymentsAdapter adapter = new PaymentsAdapter(transactions, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        dbHelper = new DatabaseHelper.DatabaseOpenHelper(this);
//        sql_db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                InventoryContract.InventoryEntry.COLUMN_TRANS_NAME,
                InventoryContract.InventoryEntry.COLUMN_TRANS_DESC,
                InventoryContract.InventoryEntry.COLUMN_TRANS_COST,
                InventoryContract.InventoryEntry.COLUMN_TRANS_LABEL,
                InventoryContract.InventoryEntry.COLUMN_TRANS_DATE
        };

        String sortOrder =
                InventoryContract.InventoryEntry.COLUMN_TRANS_DATE + " DESC";


//        Cursor cursor = db.query(
//                InventoryContract.InventoryEntry.TABLE_NAME,   // The table to query
//                projection,                            // The array of columns to return (pass null to get all)
//                null,                         // The columns for the WHERE clause
//                null,                     // The values for the WHERE clause
//                null,                         // don't group the rows
//                null,                          // don't filter by row groups
//                sortOrder                             // The sort order
//        );
    }




    public void sampleData() {
        Transactions trans1 = new Transactions("Shaurya -> Taco Bell", "Hungry", "4.20", "Food", parseDate("2019-03-09"));
        Transactions trans2 = new Transactions("Paul -> Weaver", "Help", "6.69", "Food", parseDate("2019-03-07"));
        Transactions trans3 = new Transactions("Abhinav -> Moffitt", "Rent", "2800", "Food", parseDate("2019-03-07"));
        Transactions trans4 = new Transactions("Kanyes -> MLAB", "Plz", "50", "Food", parseDate("2019-03-08"));
        transactions.add(trans1);
        transactions.add(trans2);
        transactions.add(trans3);
        transactions.add(trans4);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
