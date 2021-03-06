package com.tot_up.chris.tot_up.categorytotals;

import android.util.Log;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categorytotalrepository.CategoryTotalRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;
import com.tot_up.chris.tot_up.util.StringCheckerUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryTotalPresenter extends BasePresenter<CategoryTotalInterface.View> implements CategoryTotalInterface.Presenter {
    public static final String LIST_ERROR_MESSAGE = "Error, please try again";
    public static final String SPREADSHEET_SUCCESS = "Spreadsheet exported!";
    public static final String SPREADSHEET_FAILURE = "Error exporting spreadsheet";
    public static final String EMPTY_LIST_ERROR = "Please select a table to export";

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
                            getView().showMessage(LIST_ERROR_MESSAGE);
                        });
    }

    @Override
    public void makeSpreadsheet(List<String> tablesForSpreadsheet, String dateFrom) {
        if(tablesForSpreadsheet.isEmpty()){
            getView().showMessage(EMPTY_LIST_ERROR);
            return;
        }

        repository.makeSpreadsheet(tablesForSpreadsheet, dateFrom)
                .subscribe(result -> {
                    if(result) {
                        getView().showMessage(SPREADSHEET_SUCCESS);
                    }else{
                        getView().showMessage(SPREADSHEET_FAILURE);
                    }
                }, e -> {
                        e.printStackTrace();
                        getView().showMessage(SPREADSHEET_FAILURE);
                });
    }

    @Override
    public  void getCategoryNameList() {
        List<String> categoryList = new ArrayList<>();

        repository.getCategoryNameList(DateUtil.getStartOfYear())
                .subscribe(categories -> getView().setCategoryList(categories));
    }

    @Override
    public void goToCategory(String categoryName) {
        getView().goToCategory(categoryName);
    }

    private void updateView(List<Category> categoryList){
        if(categoryList.isEmpty()){
            getView().showEmpty();
        }else{
            getView().showTotals(categoryList);
        }
    }
}
