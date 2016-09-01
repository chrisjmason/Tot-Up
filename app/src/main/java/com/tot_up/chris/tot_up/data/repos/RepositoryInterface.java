package com.tot_up.chris.tot_up.data.repos;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

import rx.Observable;

public interface RepositoryInterface extends MvpInterface.Model {

    Observable<List<Category>> addCategory(Category category);

    Observable<List<Category>> deleteCategory(int position);

    Observable<Category> getCategory(int position);
}
