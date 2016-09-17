package com.tot_up.chris.tot_up.categorydetail;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;

import java.util.List;

import rx.Observable;

public class CategoryDetailPresenter extends BasePresenter<CategoryDetailInterface.View> implements CategoryDetailInterface.Presenter {

    CategoryDetailRepositoryInterface repository;

    public CategoryDetailPresenter(CategoryDetailInterface.View view, CategoryDetailRepositoryInterface repository){
        attachView(view);
        this.repository = repository;
    }

    @Override
    public void getExpenses(String categoryName) {
        Observable<List<Expense>> getExpenseObs = repository.getExpenses(categoryName);

        getExpenseObs.subscribe(expenseList ->{
                if(expenseList.isEmpty()){
                    getView().showEmpty();
                }else{
                    getView().showExpenses(expenseList);
                }
            },
            e ->{
                e.printStackTrace();
                getView().showError();
            });
    }
}
