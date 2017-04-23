package com.tot_up.chris.tot_up.util;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileUtil {


    public FileWriter getFileWriter(String tablename) {

        File exportDir = new File(Environment.getExternalStorageDirectory() + "/Tot-up/excel");
        exportDir.mkdirs();

        File file = new File(exportDir, tablename + "Expenses" + DateUtil.getTimestamp());
        FileWriter fileWriter;

        try {
            file.createNewFile();
            fileWriter = new FileWriter(file);
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }

        return fileWriter;
    }
}
