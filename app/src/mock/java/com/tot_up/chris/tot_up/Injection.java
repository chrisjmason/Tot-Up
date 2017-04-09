package com.tot_up.chris.tot_up;

import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.db.FakeDb;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepository;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;
import com.tot_up.chris.tot_up.data.repos.categoryoverviewrepository.CategoryOverviewRepository;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Injection {
    private static Scheduler uiThread = AndroidSchedulers.mainThread();
    private static Scheduler workThread = Schedulers.io();
    private static Scheduler mockThread = Schedulers.immediate();

    private Injection(){}

    public static CategoryOverviewRepository provideMockCategoryOverviewRepository(){
        return new CategoryOverviewRepository(provideDb(),mockThread, mockThread);
    }

    public static CategoryOverviewRepository provideCategoryOverviewRepository(){
        return new CategoryOverviewRepository(provideDb(),workThread, uiThread);
    }

    public static CategoryDetailRepositoryInterface provideCategoryDetailRepository(){
        return new CategoryDetailRepository(provideDb(), workThread, uiThread);
    }

    public static CategoryTotalRepositoryInterface provideCategoryTotalRepository(){
        return new CategoryTotalRepository(provideDb(), new CsvUtil(), workThread, uiThread);
    }

    private static DbInterface provideDb(){
        return FakeDb.getInstance();
    }
}
