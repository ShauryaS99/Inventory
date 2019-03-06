package com.example.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder> {
    private static final String TAG = "PaymentsAdapter";
    private ArrayList<String> data;
    private Context _context;

    PaymentsAdapter(ArrayList<String> idk, Context context) {
        data = new ArrayList<>();
        _context = context;
    }

    @Override
    public PaymentsViewHolder onCreateViewHolder(ViewGroup parent, int viewtypes) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new PaymentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentsViewHolder holder, int index) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class PaymentsViewHolder extends RecyclerView.ViewHolder {
        TextView name_trans;
        TextView desc_trans;
        TextView label_trans;
        TextView date_trans;
        TextView cost_trans;

        public PaymentsViewHolder(View v) {
            super(v);
            name_trans = v.findViewById(R.id.trans_name);
            desc_trans = v.findViewById(R.id.trans_desc);
            label_trans = v.findViewById(R.id.trans_label);
            date_trans = v.findViewById(R.id.trans_date);
            cost_trans = v.findViewById(R.id.trans_cost);

        }

    }
}
