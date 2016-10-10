package com.tot_up.chris.tot_up.data.db.sqlitestrings;

public class CategoryDbStrings {

    public static final String TABLE_NAME = "categories";
    public static final String ID = "_id";
    public static final String COL_NAME = "category_name";
    public static final String COL_DATE = "category_date";

    public static final String CREATE_CATEGORY_DB_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT," +
            COL_DATE + " DATE );";
}
