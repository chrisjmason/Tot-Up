package com.tot_up.chris.tot_up.data.repos.categorytotalrepository;

import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class CategoryTotalRepository implements CategoryTotalRepositoryInterface {

    private DbInterface db;
    private Scheduler worker;
    private Scheduler ui;

    public CategoryTotalRepository(DbInterface db, Scheduler worker, Scheduler ui) {
        this.db = db;
        this.worker = worker;
        this.ui = ui;
    }

    @Override
    public Observable<List<Category>> getCategoryListWithTotals(String totalFromDate) {
        return Observable.just(db.getCategoryListWithTotals(totalFromDate))
                .observeOn(ui)
                .subscribeOn(worker);
    }

    @Override
    public Observable<Boolean> makeSpreadsheet(List<String> tables) {
        return null;
    }
}
