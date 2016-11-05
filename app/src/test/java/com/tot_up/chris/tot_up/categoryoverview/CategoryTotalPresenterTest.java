package com.tot_up.chris.tot_up.categoryoverview;


import android.database.DatabaseErrorHandler;

import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.categorytotals.CategoryTotalInterface;
import com.tot_up.chris.tot_up.categorytotals.CategoryTotalPresenter;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categorytotalrepository.CategoryTotalRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

import rx.Observable;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CategoryTotalPresenterTest {

    @Mock
    CategoryTotalInterface.View view;
    @Mock
    CategoryTotalRepositoryInterface repository;

    CategoryTotalInterface.Presenter presenter;
    Observable<List<Category>> obsSuccess = Observable.just(FakeListHelper.getFakeCategoryList());
    Observable<List<Category>> obsEmpty = Observable.just(Collections.emptyList());
    Observable<List<Category>> obsError = Observable.error(new IOException());

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new CategoryTotalPresenter(view, repository);
    }

    @Test
    public void getCategoryList_Success(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsSuccess);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showTotals(anyList());
    }

    @Test
    public void getCategoryList_Error(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsError);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showErrorMessage(CategoryTotalPresenter.LIST_ERROR_MESSAGE);
    }

    @Test
    public void getEmptyListEmptyScreenShown_Success(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsEmpty);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showEmpty();
    }
}
