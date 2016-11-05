package com.tot_up.chris.tot_up.categorytotals;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categorytotalrepository.CategoryTotalRepositoryInterface;

import java.util.List;

public class CategoryTotalPresenter extends BasePresenter<CategoryTotalInterface.View> implements CategoryTotalInterface.Presenter {
    public static final String LIST_ERROR_MESSAGE = "Error, please try again";

    private CategoryTotalInterface.View view;
    private CategoryTotalRepositoryInterface repository;

    public CategoryTotalPresenter(CategoryTotalInterface.View view, CategoryTotalRepositoryInterface repository) {
        super.attachView(view);
        this.repository = repository;
    }

    @Override
    public void getCategoryListWithTotals(String totalDateFrom) {
        repository.getCategoryListWithTotals(totalDateFrom)
                .subscribe(categories -> updateView(categories),
                        e ->{
                            e.printStackTrace();
                            getView().showErrorMessage(LIST_ERROR_MESSAGE);
                        });
    }

    private void updateView(List<Category> categoryList){
        if(categoryList.isEmpty()){
            getView().showEmpty();
        }else{
            getView().showTotals(categoryList);
        }
    }
}
