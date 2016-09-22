package com.tot_up.chris.tot_up.data.db;


import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Mock database to use while developing app in order to put off exact database implementation for as long as possible

public class FakeDb implements DbInterface {

    private static FakeDb instance;
    private static List<Category> categoryList;
    private static Map<String, List<Expense>> expenseMap;

    private FakeDb(){
        categoryList = new ArrayList<>();
        expenseMap = new HashMap<>();
    }

    public static FakeDb getInstance(){
        if(instance == null){
            instance = new FakeDb();
        }
        return instance;
    }

    public static FakeDb getFreshInstance(){
        instance = new FakeDb();
        return instance;
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryList;
    }

    @Override
    public boolean addCategory(Category category) {
        expenseMap.put(category.getName(), new ArrayList<>());
        return categoryList.add(category);
    }

    @Override
    public boolean deleteCategory(int position) {
        return categoryList.remove(categoryList.get(position));
    }

    @Override
    public Category getCategory(int position) {
        return categoryList.get(position);
    }

    @Override
    public List<Expense> getExpenseList(String categoryName) {
        return  expenseMap.get(categoryName);
    }

    @Override
    public boolean addExpense(String categoryName, Expense expense) {
         return expenseMap.get(categoryName).add(expense);
    }

    @Override
    public boolean deleteExpense(String categoryName, int position) {
        List<Expense> expenseList =  expenseMap.get(categoryName);
        Expense expense =  expenseList.get(position);
        return expenseMap.get(categoryName).remove(expense);
    }

    @Override
    public Expense getExpense(String categoryName, int position) {
        return null;
    }
}
