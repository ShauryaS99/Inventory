package com.example.inventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;


public class PaymentsActivity extends AppCompatActivity {

    private static final String TAG = "Payments";
    RecyclerView recyclerView = findViewById(R.id.paymentRecyclerView);
    private PaymentsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentsAdapter(null, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
