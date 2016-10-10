package com.tot_up.chris.tot_up.data.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tot_up.chris.tot_up.data.db.sqlitestrings.CategoryDbStrings;
import com.tot_up.chris.tot_up.data.db.sqlitestrings.ExpenseDbStrings;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.ArrayList;
import java.util.List;

import static com.tot_up.chris.tot_up.data.db.sqlitestrings.CategoryDbStrings.COL_DATE;
import static com.tot_up.chris.tot_up.data.db.sqlitestrings.CategoryDbStrings.COL_NAME;
import static com.tot_up.chris.tot_up.data.db.sqlitestrings.CategoryDbStrings.ID;
import static com.tot_up.chris.tot_up.data.db.sqlitestrings.CategoryDbStrings.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper implements DbInterface {
    static DbHelper instance;

    static final String DATABASE_NAME = "TotUpDb.db";
    static final int DATABASE_VERSION = 3;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryDbStrings.CREATE_CATEGORY_DB_SQL);
        db.execSQL(ExpenseDbStrings.CREATE_EXPENSE_DB_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    @Override
    public List<Category> getCategoryList() {
        SQLiteDatabase database = getWritableDatabase();
        String[] rowArray = new String[]{ID, COL_NAME, COL_DATE};
        Cursor cursor = database.query(TABLE_NAME, rowArray, null, null, null, null, null, null);
        List<Category> categoryList = cursorToCategoryList(cursor);
        cursor.close();
        return categoryList;
    }

    @Override
    public boolean addCategory(Category category) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, category.getName());
        contentValues.put(COL_DATE, category.getDate());
        long count = database.insert(TABLE_NAME, null, contentValues);
        return (count>0);

    }

    @Override
    public boolean deleteCategory(int position) {
        SQLiteDatabase database = getWritableDatabase();
        List<Integer> idList = getIdList();
        int idToDelete = idList.get(position);
        long count = database.delete(TABLE_NAME, ID + "=" + idToDelete, null);
        return (count>0);
    }

    @Override
    public Category getCategory(int position) {
        SQLiteDatabase database = getWritableDatabase();
        List<Category> categoryList = getCategoryList();
        return categoryList.get(position);
    }


    @Override
    public List<Expense> getExpenseList(String categoryName) {
        return null;
    }

    @Override
    public boolean addExpense(String categoryName, Expense expense) {
        return false;
    }

    @Override
    public boolean deleteExpense(String categoryName, int position) {
        return false;
    }

    @Override
    public Expense getExpense(String categoryName, int position) {
        return null;
    }

    private List<Category> cursorToCategoryList(Cursor cursor){
        List<Category> categoryList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
            Category category = new Category(name, date);
            categoryList.add(category);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryList;
    }

    private List<Integer> getIdList(){
        SQLiteDatabase database = getWritableDatabase();
        List<Integer> categoryIdList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, new String[]{ID}, null, null, null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            categoryIdList.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryIdList;
    }
}
