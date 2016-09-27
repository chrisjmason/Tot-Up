package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

public interface CategoryOverviewInterface {

    interface View extends MvpInterface.View{

        void showCategories(List<Category> categoryList);

        void showEmptyScreen();

        void showMessage(String error);

        void goToDetail(String categoryName);

    }

    interface Presenter extends MvpInterface.Presenter<View>{

        void addCategory(Category category);

        void deleteCategory(int position);

        void getCategories();

        void goToDetail(Category category);

        void onStop();

    }
}
