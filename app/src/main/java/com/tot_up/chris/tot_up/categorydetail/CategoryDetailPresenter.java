package com.tot_up.chris.tot_up.categorydetail;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;

import java.util.List;

import rx.Observable;

public class CategoryDetailPresenter extends BasePresenter<CategoryDetailInterface.View> implements CategoryDetailInterface.Presenter {

    private CategoryDetailRepositoryInterface repository;

    public CategoryDetailPresenter(CategoryDetailInterface.View view, CategoryDetailRepositoryInterface repository){
        attachView(view);
        this.repository = repository;
    }

    @Override
    public void getExpenses(String categoryName) {
        Observable<List<Expense>> getExpenseObs = repository.getExpenseList(categoryName);

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

    @Override
    public void addExpense(Expense expense) {
        String categoryName = expense.getCategoryName();
        Observable<Boolean> addExpenseObs = repository.addExpense(categoryName,expense);

        addExpenseObs.subscribe(added -> updateView(added, expense.getCategoryName()),
                e -> {
                    e.printStackTrace();
                    updateView(false, expense.getCategoryName());
                });
    }

    @Override
    public void deleteExpense(int position, String categoryName) {
        Observable<Boolean> deleteExpenseObs= repository.deleteExpense(categoryName, position);

        deleteExpenseObs.subscribe(deleted -> updateView(deleted, categoryName),
                e -> {
                    e.printStackTrace();
                    updateView(false, categoryName);
                });
    }

    @Override
    public void goToDetail(Expense expense) {
        getView().goToDetail(expense);
    }

    private void updateView(Boolean success, String category){
        if(success) {
            getView().showMessage("Success");
            getExpenses(category);
        }else{
            getView().showError();
        }
    }
}
