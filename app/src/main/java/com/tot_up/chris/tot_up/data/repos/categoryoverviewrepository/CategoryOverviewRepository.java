package com.tot_up.chris.tot_up.data.repos.categoryoverviewrepository;

import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class CategoryOverviewRepository implements CategoryOverviewRepositoryInterface {
    private DbInterface database;
    private Scheduler workScheduler;
    private Scheduler uiScheduler;

    public CategoryOverviewRepository(DbInterface database, Scheduler workThread, Scheduler uiThread){
        this.workScheduler = workThread;
        this.uiScheduler = uiThread;
        this.database = database;
    }

    @Override
    public Observable<List<Category>> addCategory(Category category){
        return Observable.just(database.addCategory(category))
                .subscribeOn(workScheduler)
                .observeOn(uiScheduler)
                .map(added -> database.getCategoryList());
    }

    @Override
    public Observable<List<Category>> deleteCategory(int position) {
        return Observable.just(database.deleteCategory(position))
                .subscribeOn(workScheduler)
                .observeOn(uiScheduler)
                .map(added -> database.getCategoryList());
    }

    @Override
    public Observable<List<Category>> getCategoryList() {
        return Observable.just(database.getCategoryList())
                .subscribeOn(workScheduler)
                .observeOn(uiScheduler);
    }

    @Override
    public Observable<Category> getCategory(int position) {
        return Observable.just(database.getCategory(position))
                .subscribeOn(workScheduler)
                .observeOn(uiScheduler);
    }
}
