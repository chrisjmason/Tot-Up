package com.tot_up.chris.tot_up.data.db;


import com.tot_up.chris.tot_up.data.model.Category;

import java.util.ArrayList;
import java.util.List;

//Mock database to use while developing app in order to put off exact database implementation for as long as possible

public class FakeDb implements DbInterface {

    private static FakeDb instance;
    private static List<Category> categoryList;

    private FakeDb(){
        categoryList = new ArrayList<>();
    }

    public static FakeDb getInstance(){
        if(instance == null){
            instance = new FakeDb();
        }
        return instance;
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryList;
    }

    @Override
    public boolean addCategory(Category category) {
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
}
