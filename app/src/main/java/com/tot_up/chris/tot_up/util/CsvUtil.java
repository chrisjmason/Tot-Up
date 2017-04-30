package com.tot_up.chris.tot_up.util;


import android.database.Cursor;
import android.util.Log;

import com.opencsv.CSVWriter;
import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.db.SQLiteStrings.ExpenseDbStrings;
import com.tot_up.chris.tot_up.data.model.Category;

import java.io.FileWriter;
import java.io.IOException;

import static com.tot_up.chris.tot_up.data.db.SQLiteStrings.CategoryDbStrings.COL_CATEGORY_DATE;
import static com.tot_up.chris.tot_up.data.db.SQLiteStrings.CategoryDbStrings.COL_CATEGORY_NAME;

public class CsvUtil {
    private CsvFileUtil csvFileUtil;

    public CsvUtil(CsvFileUtil csvFileUtil) {
        this.csvFileUtil = csvFileUtil;
    }

    public boolean makeCSV(Cursor cursor, String tableName){
        FileWriter fileWriter = csvFileUtil.getFileWriter(tableName);

        if(fileWriter == null){
            return false;
        }

        CSVWriter csvWriter = new CSVWriter(fileWriter);

        try{
            String[] titleArray = {tableName, "", ""};
            csvWriter.writeNext(titleArray);

            String[] colTitArray = {"Date", "Expense Cost"};
            csvWriter.writeNext(colTitArray);

            while (cursor.moveToNext()) {
                String[] arrStr = {cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDbStrings.COL_EXPENSE_PRICE)), cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDbStrings.COL_EXPENSE_DATE))};
                csvWriter.writeNext(arrStr);
            }
            csvWriter.close();
            cursor.close();
            return true;

        } catch (IOException ex){
            ex.printStackTrace();
            return false;
        }

    }
}
