package com.tot_up.chris.tot_up.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.tot_up.chris.tot_up.data.db.SQLiteStrings.CategoryDbStrings;
import com.tot_up.chris.tot_up.data.db.SQLiteStrings.ExpenseDbStrings;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tot_up.chris.tot_up.data.db.SQLiteStrings.CategoryDbStrings.*;
import static com.tot_up.chris.tot_up.data.db.SQLiteStrings.ExpenseDbStrings.*;


public class DbHelper extends SQLiteOpenHelper implements DbInterface {
    private static DbHelper instance;

    private static final String DATABASE_NAME = "TotUpDb.db";
    private static final int DATABASE_VERSION = 6;

    private DbHelper(Context context){
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
        db.execSQL(" DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public List<Category> getCategoryList() {
        SQLiteDatabase database = getWritableDatabase();
        String[] rowArray = new String[]{COL_CATEGORY_ID, COL_CATEORY_NAME, COL_CATEGORY_DATE};
        Cursor cursor = database.query(CATEGORY_TABLE_NAME, rowArray, null, null, null, null, null, null);
        return cursorToCategoryList(cursor);
    }

    @Override
    public boolean addCategory(Category category) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CATEORY_NAME, category.getName());
        contentValues.put(COL_CATEGORY_DATE, category.getDate());
        return (database.insert(CATEGORY_TABLE_NAME, null, contentValues) > 0);
    }

    @Override
    public boolean deleteCategory(int position) {
        SQLiteDatabase database = getWritableDatabase();
        List<Integer> idList = getIdList(CATEGORY_TABLE_NAME, COL_CATEGORY_ID, null);
        int idToDelete = idList.get(position);
        return (database.delete(CATEGORY_TABLE_NAME, COL_CATEGORY_ID + "=" + idToDelete, null) >0);
    }

    @Override
    public Category getCategory(int position) {
        List<Category> categoryList = getCategoryList();
        return categoryList.get(position);
    }


    @Override
    public List<Expense> getExpenseList(String categoryName) {
        SQLiteDatabase database = getWritableDatabase();
        String[] rowArray = new String[]{COL_EXPENSE_CATEGORY, COL_EXPENSE_PRICE, COL_EXPENSE_DATE, COL_EXPENSE_IMAGE};
        String where = COL_EXPENSE_CATEGORY + " = '" + categoryName + "'";

        try{
            Cursor cursor = database.query(EXPENSE_TABLE_NAME, rowArray, where, null, null, null, null, null);
            return cursorToExpenseList(cursor);
        }catch (SQLiteException ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean addExpense(String categoryName, Expense expense) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EXPENSE_CATEGORY, categoryName);
        contentValues.put(COL_EXPENSE_PRICE, expense.getDecimalPrice().toString());
        contentValues.put(COL_EXPENSE_DATE, expense.getDate());
        contentValues.put(COL_EXPENSE_IMAGE, expense.getImageSrc());
        return (database.insert(EXPENSE_TABLE_NAME, null, contentValues) > 0);
    }

    @Override
    public boolean deleteExpense(String categoryName, int position) {
        SQLiteDatabase database = getWritableDatabase();
        List<Integer> idList = getIdList(EXPENSE_TABLE_NAME, COL_EXPENSE_ID, categoryName);
        int idToDelete = idList.get(position);
        String whereClause = COL_EXPENSE_CATEGORY + " = '" + categoryName + "'" + " AND " + COL_EXPENSE_ID + " = '" + String.valueOf(idToDelete) +"'";
        return (database.delete(EXPENSE_TABLE_NAME, whereClause, null) > 0);
    }

    @Override
    public Expense getExpense(String categoryName, int position) {
        List<Expense> expenseList = getExpenseList(categoryName);
        return expenseList.get(position);
    }

    @Override
    public String getExpenseTotalSince(String categoryName, String expenseFromDate) {
        SQLiteDatabase database = getWritableDatabase();
        String[] rowArray = new String[]{COL_EXPENSE_PRICE};
        String where = COL_EXPENSE_CATEGORY + " = '" + categoryName + "'" + " AND " + COL_EXPENSE_DATE + " >= " + "'" + expenseFromDate + "'";

        try{
            Cursor cursor = database.query(EXPENSE_TABLE_NAME, rowArray, where, null, null, null, null);
            return cursorToExpenseTotal(cursor);
        }catch (SQLiteException ex){
            ex.printStackTrace();
            return "0.00";
        }
    }

    @Override
    public List<Category> getCategoryListWithTotals(String totalFromDate) {
        return addTotalsForCategoryList(getCategoryList(), totalFromDate);
    }

    @Override
    public Cursor getTableCursor(String table, String dateFrom) {
        SQLiteDatabase database = getWritableDatabase();
        String[] rowArray = new String[]{COL_EXPENSE_PRICE, COL_EXPENSE_DATE};
        String where = COL_EXPENSE_CATEGORY + " = '" + table + "'" + " AND " + COL_EXPENSE_DATE + " >= " + "'" + dateFrom + "'";
        Cursor cursor;

        try{
            cursor = database.query(EXPENSE_TABLE_NAME, rowArray, where, null, null, null, null);
            return cursor;
        }catch (SQLiteException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private List<Category> cursorToCategoryList(Cursor cursor){
        List<Category> categoryList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEORY_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY_DATE));
            Category category = new Category(name, date);
            categoryList.add(category);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryList;
    }

    private List<Expense> cursorToExpenseList(Cursor cursor){
        List<Expense> expenseList = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPENSE_CATEGORY));
            String expensePrice = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPENSE_PRICE));
            String expenseDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPENSE_DATE));
            String expenseImage = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPENSE_IMAGE));
            Expense expense = new Expense(expensePrice, expenseDate, categoryName, expenseImage);
            expenseList.add(expense);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseList;
    }

    private String cursorToExpenseTotal(Cursor cursor){
        cursor.moveToFirst();
        BigDecimal totalPrice = new BigDecimal(0).setScale(2);

        while (!cursor.isAfterLast()){
            String expensePrice = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPENSE_PRICE));
            totalPrice = totalPrice.add(new BigDecimal(expensePrice).setScale(2));
            cursor.moveToNext();
        }

        cursor.close();
        return totalPrice.toString();
    }

    private List<Category> addTotalsForCategoryList(List<Category> categoryList, String expenseFromDate){
        for(Category category : categoryList){
            String categoryTotal = getExpenseTotalSince(category.getName(), expenseFromDate);
            category.setTotal(categoryTotal);
        }
        return categoryList;
    }

    private List<Integer> getIdList(String tableName, String idColumn, String categoryName){
        SQLiteDatabase database = getWritableDatabase();
        List<Integer> categoryIdList = new ArrayList<>();

        String where = null;
        if(tableName.equals(EXPENSE_TABLE_NAME)){
            where = COL_EXPENSE_CATEGORY + " = '" + categoryName + "'";
        }
        Cursor cursor = database.query(tableName, new String[]{idColumn}, where, null, null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(idColumn));
            categoryIdList.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        return categoryIdList;
    }
}
