package com.example.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {

    private EditText nameT;
    private EditText recepT;
    private EditText descT;
    private EditText costT;
    private EditText dateT;
    private EditText labelT;
    private Button buyB;

    /**
     * SQL-related variables.
     */
    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        nameT = findViewById(R.id.nameET);
        recepT = findViewById(R.id.recepientET);
        descT = findViewById(R.id.descET);
        costT = findViewById(R.id.costET);
        dateT = findViewById(R.id.dateET);
        labelT = findViewById(R.id.labelET);
        buyB = findViewById(R.id.addbutton);

        // SQL Database Insertion
        _dbHelper = new DatabaseHelper(this);
        // Get the database. If it does not exist, this is where it will
        // also be created.
        _db = _dbHelper.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        _dbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbutton:
                String header = getHeader();
                String description = getDesc();
                String date = getDate();
                String cost = getCost();
                String label = getLabel();
                if (header.equals("") || description.equals("") || date.equals("") || cost.equals("")) {
                    return;
                }
                Date d = convertToDate(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(0, 2)), Integer.parseInt(date.substring(3, 5)));
                ContentValues values = DatabaseHelper.insertTransaction(header, description, cost, label, date, d);
                long newRowId = _db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
                Intent i = new Intent(AddTransaction.this, PaymentsActivity.class);
                startActivity(i);
        }

    }

    public String getHeader() {
        // Check if there's a valid merchant name.
        String header_one = nameT.getText().toString();
        String header_two = recepT.getText().toString();
        String header = header_one + " -> " + header_two;
        if (header_one.equals("") || header_two.equals("")) {
            Toast.makeText(AddTransaction.this, "Please enter a valid name & recepient.", Toast.LENGTH_LONG).show();
            return "";
        }
        return header;
    }

    public String getDesc() {
        // Check if there's a valid description.
        String description = descT.getText().toString();
        if (description == null || description.equals("")) {
            Toast.makeText(AddTransaction.this, "Please enter a description.", Toast.LENGTH_LONG).show();
            return "";
        }
        return description;
    }

    public String getDate() {
        // Check if there's a valid event date.
        String date = dateT.getText().toString();
        if (date.equals("")) {
            Toast.makeText(AddTransaction.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
            return "";
        }
        return date;
    }

    public static Date convertToDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        // Must subtract month by 1 because months start at 1 for Jan but indexing starts at 0.
        c.set(year, month - 1, day);
        return c.getTime();
    }

    public String getCost() {
        // Check if there's a valid cost.
        String cost = costT.getText().toString();
        if (cost.equals("")) {
            Toast.makeText(AddTransaction.this, "Please enter a cost amount.", Toast.LENGTH_LONG).show();
            return "";
        }
        return cost;
    }

    public String getLabel() {
        // Check if there's a valid description.
        String label = labelT.getText().toString();
        if (label == null || label.equals("")) {
            Toast.makeText(AddTransaction.this, "Please enter a description.", Toast.LENGTH_LONG).show();
            return "";
        }
        return label;
    }

}

