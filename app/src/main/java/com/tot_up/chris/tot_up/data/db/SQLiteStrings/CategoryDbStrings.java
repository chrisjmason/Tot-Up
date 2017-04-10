package com.tot_up.chris.tot_up.data.db.SQLiteStrings;

public class CategoryDbStrings {

    public static final String CATEGORY_TABLE_NAME = "categories";
    public static final String COL_CATEGORY_ID = "_id";
    public static final String COL_CATEORY_NAME = "category_name";
    public static final String COL_CATEGORY_DATE = "category_date";

    public static final String CREATE_CATEGORY_DB_SQL = "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
            COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CATEORY_NAME + " TEXT," +
            COL_CATEGORY_DATE + " DATE );";

}
