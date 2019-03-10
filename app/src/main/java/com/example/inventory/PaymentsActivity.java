package com.example.inventory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toolbar;

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
    private SearchView _searchView;
    private PaymentsAdapter adapter;



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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();


        sampleData();
        populateTransactions();

        adapter = new PaymentsAdapter(transactions, getApplicationContext());
        recyclerView.setAdapter(adapter);


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
                InventoryContract.InventoryEntry.COLUMN_TRANS_LONG + " DESC";


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

    /** Creates all the menu options for the toolbar (the search button). */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_payments, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        _searchView = (SearchView) searchItem.getActionView();

        _searchView.setMaxWidth(Integer.MAX_VALUE);

        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                adapter.getFilter().filter(text);
                return false;
            }
        });

        // Will set the search query to the most recent search, if present.
        _searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prevFilter = retrievePrevQuery(getApplicationContext());
                _searchView.setQuery(prevFilter, false);
            }
        });

        ImageView closeButton = _searchView.findViewById(R.id.search_close_btn);

        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _searchView.setQuery(null, false);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("prev", null);
                editor.apply();
            }
        });

        return true;
    }


    /** Retrieves the most recent query from SharedPreference. */
    public static String retrievePrevQuery(Context c) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        String prevFilter = sharedPref.getString("prev", null);
        return prevFilter;
    }
}
