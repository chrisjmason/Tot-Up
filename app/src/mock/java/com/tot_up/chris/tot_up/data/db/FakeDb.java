package com.tot_up.chris.tot_up.data.db;


import android.support.annotation.VisibleForTesting;

import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Mock database to use while developing app in order to put off exact database implementation for as long as possible

public class FakeDb implements DbInterface {

    static final String TEST_LIST = "test";

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

    // Very hacky way of getting Espresso tests to work, temporary solution.
    @Override
    public List<Expense> getExpenseList(String categoryName) {
        if(expenseMap.get(categoryName) == null){
            return expenseMap.get(TEST_LIST);
        }else{
            return expenseMap.get(categoryName);
        }
    }

    // Very hacky way of getting Espresso tests to work, temporary solution.
    @Override
    public boolean addExpense(String categoryName, Expense expense) {
        if(expenseMap.get(categoryName) == null){
            addCategory(new Category(TEST_LIST, DateUtil.getDate()));
            return expenseMap.get(TEST_LIST).add(expense);
        }else{
            return expenseMap.get(categoryName).add(expense);
        }
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
