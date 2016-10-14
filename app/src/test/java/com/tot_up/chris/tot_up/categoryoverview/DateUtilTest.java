package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class DateUtilTest {

    @Test
    public void getDate_Check(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTest = sdf.format(new Date()).toString();

        assertThat("Check get date", DateUtil.getDate(), is(dateTest));
    }

    @Test
    public void dateDifference_MinuteCheck(){
        long timestamp = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTenMins = sdf.format(new Date(timestamp - 600000));
        String dateFortySixMins = sdf.format(new Date(timestamp - 2760000));

        assertThat("Check 10 mins", DateUtil.getDifference(dateTenMins), is("10m"));
        assertThat("Check 46 mins", DateUtil.getDifference(dateFortySixMins), is("46m"));
    }

    @Test
    public void dateDifference_HourCheck(){
        long timestamp = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateFiveHours = sdf.format(new Date(timestamp - 18000000));
        String dateTwentyHours = sdf.format(new Date(timestamp - 72000000));

        assertThat("Check 5 hours", DateUtil.getDifference(dateFiveHours), is("5h"));
        assertThat("Check 20 hours", DateUtil.getDifference(dateTwentyHours), is("20h"));
    }

    @Test
    public void dateDifference_DayCheck(){
        long timestamp = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTenDays = sdf.format(new Date(timestamp - 864000000));
        String dateTwentyTwoDays = sdf.format(new Date(timestamp - 1900800000));

        assertThat("Check 10 Days", DateUtil.getDifference(dateTenDays), is("10d"));
        assertThat("Check 22 Days", DateUtil.getDifference(dateTwentyTwoDays), is("22d"));
    }

    @Test
    public void getStartOfWeek_check(){
        System.out.println(DateUtil.getStartOfWeek());
    }

    @Test
    public void getStartOfMonth_check(){
        System.out.println(DateUtil.getStartOfMonth());
    }
}
