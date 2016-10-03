package com.tot_up.chris.tot_up.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class ImageFileUtil {

    static File totUpDir;

    public static File createImageFile(Context context) throws IOException{
        makeTotUpDir(context);
        String timestamp = DateUtil.getTimestamp().toString();
        String fileName = "JPEG_" + timestamp;
        return File.createTempFile(fileName, ".jpg", totUpDir);
    }

    public static Uri getImageUri(Context context, File imageFile){
        return FileProvider.getUriForFile(context, "com.tot_up.chris.fileprovider", imageFile);
    }

    public static String getImagePath(File imageFile){
        return imageFile.getAbsolutePath();
    }

    private static void makeTotUpDir(Context context) throws IOException{
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        totUpDir = new File(storageDir + File.pathSeparator + "tot-up");

        if(!totUpDir.exists()){
            if(totUpDir.mkdir()) {
                Log.d("Make image dir", "dir created");
            }else{
                Log.d("Make image dir", "dir not created");
                throw new IOException();
            }
        }else{
            Log.d("Make image dir", "dir already exists");
        }
    }
}
