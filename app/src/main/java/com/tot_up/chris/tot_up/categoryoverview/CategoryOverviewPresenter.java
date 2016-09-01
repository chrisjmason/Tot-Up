package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.RepositoryInterface;

public class CategoryOverviewPresenter extends BasePresenter<CategoryOverviewInterface.View> implements CategoryOverviewInterface.Presenter {

    public static final String ADD_SUCCESS_MESSAGE = "Category added";
    public static final String ADD_ERROR_MESSAGE = "Error adding category";
    public static final String DELETE_SUCCESS_MESSAGE = "Category deleted";
    public static final String DELETE_ERROR_MESSAGE = "Error deleting category";

    private RepositoryInterface repository;

    public CategoryOverviewPresenter(CategoryOverviewInterface.View view, RepositoryInterface repository){
        super.attachView(view);
        this.repository = repository;
    }

    @Override
    public void addCategory(Category category) {
        repository.addCategory(category)
                .subscribe(categories -> {
                            getView().showCategories(categories);
                            getView().showMessage(ADD_SUCCESS_MESSAGE);
                        },
                        e -> {
                            e.printStackTrace();
                            getView().showMessage(ADD_ERROR_MESSAGE);
                        });
    }

    @Override
    public void deleteCategory(int position) {
        repository.deleteCategory(position)
                .subscribe(categories -> {
                    getView().showCategories(categories);
                    getView().showMessage(DELETE_SUCCESS_MESSAGE);
                },
                e -> {
                    e.printStackTrace();
                    getView().showMessage(DELETE_ERROR_MESSAGE);
                });
    }

    @Override
    public void onStop() {
        super.detachView();
    }
}
