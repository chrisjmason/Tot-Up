package com.tot_up.chris.tot_up.util;

import java.util.Currency;
import java.util.Locale;

public class StringFormatterUtil {

    private StringFormatterUtil(){}

    //Will check here to see which currency has been chosen and then display that
    public static String addCurrencySignToString(String string){
        String poundSign = Currency.getInstance(Locale.UK).getSymbol();

        return poundSign + string;

    }

}
