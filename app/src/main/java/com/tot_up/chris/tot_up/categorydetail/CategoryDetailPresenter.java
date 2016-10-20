package com.tot_up.chris.tot_up.categorydetail;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;
import com.tot_up.chris.tot_up.util.StringFormatterUtil;

import java.util.Date;
import java.util.List;

import rx.Observable;

//// TODO: 14/10/2016 Move methods retrieving totals to seperate presenter
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

    //TODO check for empty string
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

    @Override
    public void getMonthExpenseTotal(String categoryName) {
        Observable<String> expenseTotalObs = repository.getExpenseTotal(categoryName, DateUtil.getStartOfMonth());

        expenseTotalObs.subscribe(total -> getView().showMonthTotal(StringFormatterUtil.addCurrencySignToString(total)),
                e->{
                    e.printStackTrace();
                    updateView(false, categoryName);
                });
    }

    @Override
    public void getWeekExpenseTotal(String categoryName) {
        Observable<String> expenseTotalObs = repository.getExpenseTotal(categoryName, DateUtil.getStartOfWeek());

        expenseTotalObs.subscribe(total -> getView().showWeekTotal(StringFormatterUtil.addCurrencySignToString(total)),
                e->{
                    e.printStackTrace();
                    updateView(false, categoryName);
                });
    }

    private void updateView(Boolean success, String category){
        if(success) {
            getView().showMessage("Success");
            getExpenses(category);
            getMonthExpenseTotal(category);
            getWeekExpenseTotal(category);
        }else{
            getView().showError();
        }
    }


}
