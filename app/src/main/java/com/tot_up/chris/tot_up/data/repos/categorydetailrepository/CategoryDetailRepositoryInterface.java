package com.tot_up.chris.tot_up.data.repos.categorydetailrepository;

import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

import rx.Observable;

public interface CategoryDetailRepositoryInterface {

    Observable<List<Expense>> getExpenseList(String categoryName);

    Observable<Boolean> addExpense(String categoryName, Expense expenseToAdd);

    Observable<Boolean> deleteExpense(String categoryName, int position);

    Observable<Expense> getExpense(String categoryName, int position);

}
