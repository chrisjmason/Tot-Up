package com.tot_up.chris.tot_up.categorytotals;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

public interface CategoryTotalInterface {

    interface View extends MvpInterface.View{

        void showTotals(List<Category> categoryList);

        void showEmpty();

        void showMessage(String error);

        void setCategoryList(List<String> categoryList);

        void goToCategory(String categoryName);

    }

    interface Presenter{
        void getCategoryListWithTotals(String totalDateFrom);

        void makeSpreadsheet(List<String> tablesForSpreadsheet, String dateFrom);

        void getCategoryNameList();

        void goToCategory(String categoryName);
    }
}
