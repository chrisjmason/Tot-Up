package com.tot_up.chris.tot_up.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static String getDate() {
        return formatDate(new Date());
    }

    public static Long getTimestamp(){
        return new Date().getTime();
    }

    public static String getDifference(String thenDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thenDateObj;

        try {
            thenDateObj = sdf.parse(thenDate);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }

        Date nowDateObj = new Date();

        return calcDiff(nowDateObj, thenDateObj);
    }

    public static String getStartOfWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return formatDate(calendar.getTime());
    }

    public static String getStartOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return formatDate(calendar.getTime());
    }

    public static String getStartOfYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return formatDate(calendar.getTime());
    }

    private static String formatDate(Date dateToFormat){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(dateToFormat);
    }

    private static String calcDiff(Date nowDateObj, Date thenDateObj){
        long differenceMinutes = TimeUnit.MILLISECONDS.toMinutes(nowDateObj.getTime() - thenDateObj.getTime());

        long differenceHours = TimeUnit.MILLISECONDS.toHours(nowDateObj.getTime() - thenDateObj.getTime());

        long differenceDays =  TimeUnit.MILLISECONDS.toDays(nowDateObj.getTime() - thenDateObj.getTime());

        if(differenceDays ==0 && differenceHours ==0){
            return String.valueOf(differenceMinutes) + "m";
        }else if(differenceDays == 0){
            return String.valueOf(differenceHours) + "h";
        }
        return String.valueOf(differenceDays) + "d";
    }


}
