package com.tot_up.chris.tot_up.data.repos.categorydetailrepository;


import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class CategoryDetailRepository implements CategoryDetailRepositoryInterface {

    private DbInterface database;
    private Scheduler workThread;
    private Scheduler uiThread;

    public CategoryDetailRepository(DbInterface database, Scheduler workThread, Scheduler uiThread) {
        this.database = database;
        this.workThread = workThread;
        this.uiThread = uiThread;
    }

    @Override
    public Observable<List<Expense>> getExpenseList(String categoryName) {
        return Observable.just(database.getExpenseList(categoryName))
                .subscribeOn(workThread)
                .observeOn(uiThread);
    }

    @Override
    public Observable<Boolean> addExpense(String categoryName, Expense expenseToAdd) {
        return Observable.just(database.addExpense(categoryName, expenseToAdd))
                .subscribeOn(workThread)
                .observeOn(uiThread);
    }

    @Override
    public Observable<Boolean> deleteExpense(String categoryName, int position) {
        return Observable.just(database.deleteExpense(categoryName, position))
                .subscribeOn(workThread)
                .observeOn(uiThread);
    }

    @Override
    public Observable<Expense> getExpense(String categoryName, int position) {
        return Observable.just(database.getExpense(categoryName, position))
                .subscribeOn(workThread)
                .observeOn(uiThread);
    }
}
