package com.tot_up.chris.tot_up.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.tot_up.chris.tot_up.util.Application.MyApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileUtil {

    public FileWriter getFileWriter(String tablename) {
        if(!isExternalStorageWritable()){
            return null;
        }

        Context context = MyApplication.getContext();

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File exportDir = new File(storageDir + File.pathSeparator +  "tot-up");
        if (!exportDir.mkdirs()) {
            Log.e("Filewriter error", "Directory not created");
        }

        exportDir.getParentFile().mkdirs();
        exportDir.mkdirs();

        Log.d("filewriter dir ", exportDir.toString());
        File file = new File(exportDir, tablename + "expenses" + DateUtil.getTimestamp());
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

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }
}
