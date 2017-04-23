package com.tot_up.chris.tot_up.data.repos.categorytotalrepository;

import android.util.Log;

import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.util.CsvUtil;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class CategoryTotalRepository implements CategoryTotalRepositoryInterface {

    private DbInterface db;
    private CsvUtil csvUtil;
    private Scheduler worker;
    private Scheduler ui;

    public CategoryTotalRepository(DbInterface db, CsvUtil csvUtil, Scheduler worker, Scheduler ui) {
        this.db = db;
        this.csvUtil = csvUtil;
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
    //need to get date from
    public Observable<Boolean> makeSpreadsheet(List<String> tables, String dateFrom) {
        return Observable.from(tables)
                .map(table -> csvUtil.makeCSV(db.getTableCursor(table, dateFrom), table))
                .observeOn(ui)
                .subscribeOn(worker);
    }
}
