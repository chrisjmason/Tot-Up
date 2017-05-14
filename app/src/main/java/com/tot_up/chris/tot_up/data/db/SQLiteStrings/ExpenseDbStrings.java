package com.tot_up.chris.tot_up.data.db.SQLiteStrings;

public class ExpenseDbStrings {

    public static final String EXPENSE_TABLE_NAME = "expenses";
    public static final String COL_EXPENSE_ID = "_id";
    public static final String COL_EXPENSE_CATEGORY = "expense_category";
    public static final String COL_EXPENSE_PRICE = "expense_price";
    public static final String COL_EXPENSE_DATE = "expense_date";
    public static final String COL_EXPENSE_IMAGE = "expense_image";

    public static final String CREATE_EXPENSE_DB_SQL = "CREATE TABLE " + EXPENSE_TABLE_NAME + " (" +
            COL_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_EXPENSE_CATEGORY + " TEXT, " +
            COL_EXPENSE_PRICE + " TEXT, " +
            COL_EXPENSE_DATE + " DATE, " +
            COL_EXPENSE_IMAGE + " TEXT );";
}
