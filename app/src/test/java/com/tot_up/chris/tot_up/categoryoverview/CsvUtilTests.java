package com.tot_up.chris.tot_up.categoryoverview;

import android.database.Cursor;

import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.util.CsvFileUtil;
import com.tot_up.chris.tot_up.util.CsvUtil;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CsvUtilTests {

    @Mock
    CsvFileUtil csvFileUtil;
    @Mock
    FileWriter fileWriter;
    @Mock
    Cursor cursor;

    CsvUtil csvUtil;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        csvUtil = new CsvUtil(csvFileUtil);
    }

    @Test
    public void makeSpreadsheetCallsFileWriter_Success(){
        try{
            csvUtil.makeCSV(cursor, anyString());
        }catch(Exception ex){

        }
        verify(csvFileUtil).getFileWriter(anyString());
    }

}
