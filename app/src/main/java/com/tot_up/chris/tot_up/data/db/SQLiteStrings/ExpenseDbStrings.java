package com.tot_up.chris.tot_up.data.db.sqlitestrings;

public class ExpenseDbStrings {

    static final String TABLE_NAME = "expenses";
    static final String ID = "_id";
    static final String COL_EXPENSE_CATEGORY = "expense_category";
    static final String COL_PRICE = "expense_price";
    static final String COL_DATE = "expense_date";
    static final String COL_IMAGE = "expense_image";

    public static final String CREATE_EXPENSE_DB_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COL_EXPENSE_CATEGORY + " TEXT," +
            COL_PRICE + " TEXT, " +
            COL_DATE + " DATE, " +
            COL_IMAGE + " TEXT );";
}
