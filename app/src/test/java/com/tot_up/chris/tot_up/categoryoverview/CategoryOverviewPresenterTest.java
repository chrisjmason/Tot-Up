package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categoryoverviewrepository.CategoryOverviewRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;


public class CategoryOverviewPresenterTest {

    @Mock
    CategoryOverviewInterface.View view;

    @Mock
    CategoryOverviewRepositoryInterface repository;

    CategoryOverviewPresenter presenter;

    Observable<List<Category>> obsSuccess = Observable.just(FakeListHelper.getFakeCategoryList())
            .subscribeOn(Schedulers.immediate());
    Observable<List<Category>> obsSuccessEmpty = Observable.just(new ArrayList<>());
    Observable<List<Category>> obsError = Observable.error(new IOException());

    Category category = new Category("test1", DateUtil.getDate());
    Category emptyCategory = new Category("", DateUtil.getDate());
    int position = 1;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new CategoryOverviewPresenter(view,repository);
    }

    @Test
    public void addCategory_Success(){
        when(repository.addCategory(category)).thenReturn(obsSuccess);
        presenter.addCategory(category);
        verify(view).errorAdding(CategoryOverviewPresenter.ADD_SUCCESS_MESSAGE);
        verify(view).showCategories(anyList());
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void addCategory_Failure(){
        when(repository.addCategory(category)).thenReturn(obsError);
        presenter.addCategory(category);
        verify(view).errorAdding(CategoryOverviewPresenter.ADD_ERROR_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void addEmptyCategory_Failure(){
        presenter.addCategory(emptyCategory);
        verify(view).errorAdding(CategoryOverviewPresenter.ADD_ERROR_MESSAGE);
    }

    @Test
    public void deleteCategory_Success(){
        when(repository.deleteCategory(position)).thenReturn(obsSuccess);
        presenter.deleteCategory(position);
        verify(view).showCategories(anyList());
        verify(view).errorAdding(CategoryOverviewPresenter.DELETE_SUCCESS_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void deleteCategory_Failure(){
        when(repository.deleteCategory(position)).thenReturn(obsError);
        presenter.deleteCategory(position);
        verify(view).errorAdding(CategoryOverviewPresenter.DELETE_ERROR_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void getList_Success(){
        when(repository.getCategoryList()).thenReturn(obsSuccess);
        presenter.getCategories();
        verify(view).showCategories(anyList());
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void getList_Error(){
        when(repository.getCategoryList()).thenReturn(obsError);
        presenter.getCategories();
        verify(view).errorAdding(CategoryOverviewPresenter.LIST_ERROR_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void getEmptyListEmptyScreenShown_Success(){
        when(repository.getCategoryList()).thenReturn(obsSuccessEmpty);
        presenter.getCategories();
        verify(view).showEmptyScreen();
    }

    @Test
    public void goToDetail_Success(){
        presenter.goToDetail(category);
        verify(view).goToDetail(category.getName());
    }



}
