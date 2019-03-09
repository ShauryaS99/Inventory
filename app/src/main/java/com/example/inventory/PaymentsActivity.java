package com.example.inventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PaymentsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Payments";
    private ArrayList<Transactions> transactions;

    private FloatingActionButton addTrans;
    /** SQL-related variables. */
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

//    /** SQL-related variables. */
//    private DatabaseHelper.DatabaseOpenHelper dbHelper;
//    private SQLiteDatabase sql_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        transactions = new ArrayList<>();
        addTrans = findViewById(R.id.addtransaction);
        addTrans.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);

        sampleData();

        PaymentsAdapter adapter = new PaymentsAdapter(transactions, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        populateTransactions();


    }

    protected void populateTransactions() {
        String[] projection = {
                BaseColumns._ID,
                InventoryContract.InventoryEntry.COLUMN_TRANS_NAME,
                InventoryContract.InventoryEntry.COLUMN_TRANS_DESC,
                InventoryContract.InventoryEntry.COLUMN_TRANS_COST,
                InventoryContract.InventoryEntry.COLUMN_TRANS_LABEL,
                InventoryContract.InventoryEntry.COLUMN_TRANS_DATE,
                InventoryContract.InventoryEntry.COLUMN_TRANS_LONG
        };

        String sortOrder =
                InventoryContract.InventoryEntry.COLUMN_TRANS_LONG+ " DESC";


        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,   // The table to query
                projection,                            // The array of columns to return (pass null to get all)
                null,                         // The columns for the WHERE clause
                null,                     // The values for the WHERE clause
                null,                         // don't group the rows
                null,                          // don't filter by row groups
                sortOrder                             // The sort order
        );
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_TRANS_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_TRANS_DESC));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_TRANS_DATE));
            Log.d("Put the date", "date: " + date);
            Date d = AddTransaction.convertToDate(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(0, 2)), Integer.parseInt(date.substring(3, 5)));

            String cost = cursor.getString(
                    cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_TRANS_COST));
            String label = cursor.getString(
                    cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_TRANS_LABEL));
            Transactions p = new Transactions(id, name, description, cost, label, d);
            transactions.add(p);
        }
        cursor.close();
    }




    public void sampleData() {
        Transactions trans1 = new Transactions(36,"Shaurya -> Taco Bell", "Hungry", "4.20", "Food", parseDate("2019-03-09"));
        Transactions trans2 = new Transactions(25,"Paul -> Weaver", "Help", "6.69", "Food", parseDate("2019-03-07"));
        Transactions trans3 = new Transactions(20, "Abhinav -> Moffitt", "Rent", "2800", "Food", parseDate("2019-03-07"));
        Transactions trans4 = new Transactions(15, "Kanyes -> MLAB", "Plz", "50", "Food", parseDate("2019-03-08"));
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

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    /** Handles all the clicks. */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addtransaction:
                Intent i = new Intent(this, AddTransaction.class);
                startActivity(i);
        }
    }
}
