package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

public interface CategoryOverviewInterface {

    interface View extends MvpInterface.View{

        void showCategories(List<Category> categoryList);

        void addCategory();

        void showMessage(String error);

    }

    interface Presenter extends MvpInterface.Presenter<View>{

        void addCategory(Category category);

        void deleteCategory(int position);

        void onStop();

    }
}
