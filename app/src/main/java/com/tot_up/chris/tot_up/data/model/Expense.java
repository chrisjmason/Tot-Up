package com.tot_up.chris.tot_up.data.model;

import java.math.BigDecimal;

public class Expense {
    private BigDecimal decimalPrice;
    private String date;
    private String imageSrc;
    private String categoryName;

    public Expense(String price, String date, String categoryName){
        this(price, date, categoryName, null);
    }

    public Expense(String price, String date, String categoryName, String imageSrc){
        decimalPrice = new BigDecimal(price);
        this.date = date;
        this.categoryName = categoryName;
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

    public String getCategoryName() {
        return categoryName;
    }
}
