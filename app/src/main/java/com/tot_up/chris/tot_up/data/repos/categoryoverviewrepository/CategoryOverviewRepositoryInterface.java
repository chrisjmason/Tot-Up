package com.tot_up.chris.tot_up.data.repos.categoryoverviewrepository;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.sql.SQLDataException;
import java.util.List;

import rx.Observable;
import rx.Scheduler;

public interface CategoryOverviewRepositoryInterface extends MvpInterface.Model {

    Observable<List<Category>> addCategory(Category category);

    Observable<List<Category>> deleteCategory(int position);

    Observable<List<Category>> getCategoryList();

    Observable<Category> getCategory(int position);

}
