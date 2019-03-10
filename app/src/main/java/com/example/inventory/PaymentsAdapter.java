package com.example.inventory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder> {
    private static final String TAG = "PaymentsAdapter";
    private ArrayList<Transactions> transactions;
    private ArrayList<Transactions> newTransactions;
    private Context _context;

    PaymentsAdapter(ArrayList<Transactions> loc_transactions, Context context) {
        transactions = loc_transactions;
        _context = context;
    }

    @Override
    public PaymentsViewHolder onCreateViewHolder(ViewGroup parent, int viewtypes) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new PaymentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentsViewHolder holder, int index) {
        holder.bind(index);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    public Filter getFilter() {
        return searchFilter;
    }

    /** Creates a search filter based on the search query. */
    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Transactions> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(transactions);
            } else {
                String prefix = constraint.toString().toLowerCase().trim();
                for (Transactions p : transactions) {
                    if (p.getDesc().toLowerCase().startsWith(prefix) || p.getDesc().startsWith(prefix)) {
                        filteredList.add(p);
                    }
                }
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(_context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("prev", constraint.toString().toLowerCase().trim());
                editor.apply();
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /** Updates RecyclerView with filtered results. */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newTransactions.clear();
            newTransactions.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class PaymentsViewHolder extends RecyclerView.ViewHolder {
        TextView name_trans;
        TextView desc_trans;
        TextView label_trans;
        TextView date_trans;
        TextView cost_trans;
        Button delete_trans;

        public PaymentsViewHolder(View v) {
            super(v);
            name_trans = v.findViewById(R.id.trans_name);
            desc_trans = v.findViewById(R.id.trans_desc);
            label_trans = v.findViewById(R.id.trans_label);
            date_trans = v.findViewById(R.id.trans_date);
            cost_trans = v.findViewById(R.id.trans_cost);
            delete_trans = v.findViewById(R.id.delButton);

        }

        public void bind(int index) {
            Transactions t = transactions.get(index);
            name_trans.setText(t.getName());
            desc_trans.setText(t.getDesc());
            cost_trans.setText(t.getCost());
            label_trans.setText(t.getLabel());
            date_trans.setText(t.getDate());

        }

    }
}
