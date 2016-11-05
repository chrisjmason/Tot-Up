package com.tot_up.chris.tot_up.data.db;

import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

//Need to split this interface up, way too big
public interface DbInterface {

    List<Category> getCategoryList();

    boolean addCategory(Category category);

    boolean deleteCategory(int position);

    Category getCategory(int position);

    List<Expense> getExpenseList(String categoryName);

    boolean addExpense(String categoryName, Expense expense);

    boolean deleteExpense(String categoryName, int position);

    Expense getExpense(String categoryName, int position);

    List<Category> getCategoryListWithTotals(String totalFromDate);

    String getExpenseTotalSince(String categoryName, String expenseFromDate);

}
