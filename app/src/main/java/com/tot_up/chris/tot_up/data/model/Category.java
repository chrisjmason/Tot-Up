package com.tot_up.chris.tot_up.data.model;

import java.math.BigDecimal;

public class Category {
    private String name;
    private BigDecimal total;
    private String date;

    public Category(String name, String date){
        this(name, date, "0");
    }

    public Category(String name, String date, String total){
        this.name = name;
        this.date = date;
        this.total = new BigDecimal(total).setScale(2);
    }

    public String getName(){
        return name;
    }

    public void setTotal(String total){
        this.total = new BigDecimal(total).setScale(2);
    }

    public BigDecimal getTotal(){
        return total;
    }

    public String getDate(){return date;}

    @Override
    public String toString() {
        return "Name: " + name
                + "Date: " + date
                + "Total: " + total.toString();
    }
}
