package com.tot_up.chris.tot_up.data.db;

import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

public interface DbInterface {

    interface Category{
        List<Category> getCategoryList();

        boolean addCategory(Category category);

        boolean deleteCategory(int position);

        Category getCategory(int position);
    }

    interface Expense{

        List<Expense> getExpenseList(String categoryName);

        boolean addExpense(String categoryName, Expense expense);

        boolean deleteExpense(String categoryName, int position);

        Expense getExpense(String categoryName, int position);

    }



}
