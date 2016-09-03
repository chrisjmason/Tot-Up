package com.tot_up.chris.tot_up.data.db;

import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

public interface DbInterface {

    List<Category> getCategoryList();

    boolean addCategory(Category category);

    boolean deleteCategory(int position);

    Category getCategory(int position);

}
