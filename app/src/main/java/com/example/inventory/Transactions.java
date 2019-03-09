package com.example.inventory;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transactions {
    private String name;
    private String desc;
    private String cost;
    private String label;
    private Date date;
    private long id;

    public Transactions(long loc_id, String loc_name, String loc_desc, String loc_cost, String loc_label, Date loc_date) {
        id = loc_id;
        name = loc_name;
        desc = loc_desc;
        cost = loc_cost;
        label = loc_label;
        date = loc_date;
    }

    public long getID() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getCost() {
        Double new_cost = Double.parseDouble(cost);
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        String xCost = "Cost: " + defaultFormat.format(new_cost);
        return xCost;
    }

    public String getLabel() {
        return label;
    }

    public String getDate() {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

}
