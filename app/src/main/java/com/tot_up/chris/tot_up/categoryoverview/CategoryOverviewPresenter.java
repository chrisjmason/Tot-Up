package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.base.BasePresenter;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categoryoverviewrepository.CategoryOverviewRepositoryInterface;

import java.util.List;

public class CategoryOverviewPresenter extends BasePresenter<CategoryOverviewInterface.View> implements CategoryOverviewInterface.Presenter {

    //move these strings.xml eventually
    public static final String ADD_SUCCESS_MESSAGE = "Category added";
    public static final String ADD_ERROR_MESSAGE = "Error adding category";
    public static final String DELETE_SUCCESS_MESSAGE = "Category deleted";
    public static final String DELETE_ERROR_MESSAGE = "Error deleting category";
    public static final String LIST_ERROR_MESSAGE = "Error retrieving categories";

    private CategoryOverviewRepositoryInterface repository;

    public CategoryOverviewPresenter(CategoryOverviewInterface.View view, CategoryOverviewRepositoryInterface repository){
        super.attachView(view);
        this.repository = repository;
    }

    @Override
    public void addCategory(Category category) {
        repository.addCategory(category)
                .subscribe(categories -> {
                            updateView(categories);
                            showMessageInView(ADD_SUCCESS_MESSAGE);
                        },
                        e -> {
                            e.printStackTrace();
                            showMessageInView(ADD_ERROR_MESSAGE);
                        });
    }

    @Override
    public void deleteCategory(int position) {
        repository.deleteCategory(position)
                .subscribe(categories -> {
                    updateView(categories);
                    showMessageInView(DELETE_SUCCESS_MESSAGE);
                },
                e -> {
                    e.printStackTrace();
                    showMessageInView(DELETE_ERROR_MESSAGE);
                });
    }

    @Override
    public void getCategories() {
        repository.getCategoryList()
                .subscribe(categories -> updateView(categories),
                        e -> {
                            e.printStackTrace();
                            showMessageInView(LIST_ERROR_MESSAGE);
                        });
    }

    @Override
    public void goToDetail(Category category) {
        getView().goToDetail(category.getName());
    }

    @Override
    public void onStop() {

    }

    private void showMessageInView(String message){
        getView().showMessage(message);
    }

    private void updateView(List<Category> categoryList){
        if(categoryList.isEmpty()){
            getView().showEmptyScreen();
        }else{
            getView().showCategories(categoryList);
        }
    }
}
