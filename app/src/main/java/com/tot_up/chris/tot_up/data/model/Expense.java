package com.tot_up.chris.tot_up.data.model;

import java.math.BigDecimal;

public class Expense {
    private BigDecimal decimalPrice;
    private String date;
    private String imageSrc;

    public Expense(String price, String date){
        new Expense(price, date, null);
    }

    public Expense(String price, String date, String imageSrc){
        decimalPrice = new BigDecimal(price);
        this.date = date;
        this.imageSrc = imageSrc;
    }

    public BigDecimal getDecimalPrice() {
        return decimalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getImageSrc() {
        return imageSrc;
    }
}
