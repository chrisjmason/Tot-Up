package com.tot_up.chris.tot_up.util;

public class StringFormatterUtil {

    private StringFormatterUtil(){}

    public static String addPoundSignToString(String string){
        return "£" + string;
    }

    public static String addEuroSignToString(String string){
        return "€" + string;
    }

    public static String addDollarSignToString(String string){
        return "$" + string;
    }
}
