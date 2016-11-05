package com.tot_up.chris.tot_up.categorytotals;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

public interface CategoryTotalInterface {

    interface View extends MvpInterface.View{

        void showTotals(List<Category> categoryList);

        void showEmpty();

        void showErrorMessage(String error);
    }

    interface Presenter{
        void getCategoryListWithTotals(String totalDateFrom);
    }
}
