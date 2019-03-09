package com.example.inventory;

import android.provider.BaseColumns;

public class InventoryContract {

    public static final class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "Inventory";
        public static final String COLUMN_TRANS_NAME = "Name";
        public static final String COLUMN_TRANS_DESC = "Description";
        public static final String COLUMN_TRANS_LABEL = "Label";
        public static final String COLUMN_TRANS_COST = "Cost";
        public static final String COLUMN_TRANS_DATE = "Date";
        public static final String COLUMN_TRANS_LONG = "Date_long";


    }
}
