package com.tot_up.chris.tot_up.data.model;

public class Category {
    private String name;
    private double total;
    private String date;

    public Category(String name, String date){
        this.name = name;
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public void setTotal(double total){
        this.total = total;
    }

    public double getTotal(){
        return total;
    }

    public String getDate(){return date;}

    @Override
    public String toString() {
        return "Name: " + name
                + "Date: " + date;
    }
}
