package com.tot_up.chris.tot_up.data.db;

import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

public interface DbInterface {

    List<Category> getCategoryList();

    boolean addCategory(Category category);

    boolean deleteCategory(int position);

    Category getCategory(int position);

    List<Expense> getExpenses(String categoryName);

    void addExpense(String categoryName, Expense expense);

    void deleteExpense(String categoryName, int Position);

}
