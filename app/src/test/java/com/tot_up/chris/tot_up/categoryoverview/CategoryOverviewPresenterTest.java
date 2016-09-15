package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.OverviewRepositoryInterface;
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
    OverviewRepositoryInterface repository;

    CategoryOverviewPresenter presenter;

    Observable<List<Category>> obsSuccess = Observable.just(getFakeList())
            .subscribeOn(Schedulers.immediate());
    Observable<List<Category>> obsSuccessEmpty = Observable.just(new ArrayList<>());
    Observable<List<Category>> obsError = Observable.error(new IOException());

    Category category = new Category("test1", DateUtil.getDate());
    int position = 1;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        repository.setSchedulers(Schedulers.immediate(), Schedulers.immediate());
        presenter = new CategoryOverviewPresenter(view,repository);
    }

    @Test
    public void addCategory_Success(){
        when(repository.addCategory(category)).thenReturn(obsSuccess);
        presenter.addCategory(category);
        verify(view).showMessage(CategoryOverviewPresenter.ADD_SUCCESS_MESSAGE);
        verify(view).showCategories(anyList());
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void addCategory_Failure(){
        when(repository.addCategory(category)).thenReturn(obsError);
        presenter.addCategory(category);
        verify(view).showMessage(CategoryOverviewPresenter.ADD_ERROR_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void deleteCategory_Success(){
        when(repository.deleteCategory(position)).thenReturn(obsSuccess);
        presenter.deleteCategory(position);
        verify(view).showCategories(anyList());
        verify(view).showMessage(CategoryOverviewPresenter.DELETE_SUCCESS_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void deleteCategory_Failure(){
        when(repository.deleteCategory(position)).thenReturn(obsError);
        presenter.deleteCategory(position);
        verify(view).showMessage(CategoryOverviewPresenter.DELETE_ERROR_MESSAGE);
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
        verify(view).showMessage(CategoryOverviewPresenter.LIST_ERROR_MESSAGE);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void getEmptyListEmptyScreenShown_Success(){
        when(repository.getCategoryList()).thenReturn(obsSuccessEmpty);
        presenter.getCategories();
        verify(view).showEmptyScreen();
    }


    public List<Category> getFakeList(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("test1", DateUtil.getDate()));
        categoryList.add(new Category("test2", DateUtil.getDate()));
        return categoryList;
    }
}
