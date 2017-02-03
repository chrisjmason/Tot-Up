package com.tot_up.chris.tot_up.categoryoverview;


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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryTotalPresenterTest {

    @Mock
    CategoryTotalInterface.View view;
    @Mock
    CategoryTotalRepositoryInterface repository;

    CategoryTotalInterface.Presenter presenter;
    Observable<List<Category>> obsGetListSuccess = Observable.just(FakeListHelper.getFakeCategoryList());
    Observable<List<Category>> obsGetListEmpty = Observable.just(Collections.emptyList());
    Observable<List<Category>> obsGetListError = Observable.error(new IOException());

    Observable<Boolean> obsCreateSpreadsheetSuccess = Observable.just(true);
    Observable<Boolean> obsCreateSpreadsheetFailure = Observable.just(false);
    Observable<Boolean> obsCreateSpreadsheetError = Observable.error(new IOException());

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new CategoryTotalPresenter(view, repository);
    }

    @Test
    public void getCategoryList_Success(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsGetListSuccess);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showTotals(anyList());
    }

    @Test
    public void getCategoryList_Error(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsGetListError);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showMessage(CategoryTotalPresenter.LIST_ERROR_MESSAGE);
    }

    @Test
    public void getEmptyListEmptyScreenShown_Success(){
        when(repository.getCategoryListWithTotals(DateUtil.getDate())).thenReturn(obsGetListEmpty);

        presenter.getCategoryListWithTotals(DateUtil.getDate());

        verify(view).showEmpty();
    }

    @Test
    public void createSpreadsheet_Success(){
        when(repository.makeSpreadsheet(getTables())).thenReturn(obsCreateSpreadsheetSuccess);

        presenter.makeSpreadsheet(getTables());

        verify(view).showMessage(CategoryTotalPresenter.SPREADSHEET_SUCCESS);
    }

    @Test
    public void createSpreadsheet_Failure(){
        when(repository.makeSpreadsheet(getTables())).thenReturn(obsCreateSpreadsheetFailure);

        presenter.makeSpreadsheet(getTables());

        verify(view).showMessage(CategoryTotalPresenter.SPREADSHEET_FAILURE);
    }

    @Test
    public void createSpreadsheet_Error(){
        when(repository.makeSpreadsheet(getTables())).thenReturn(obsCreateSpreadsheetError);

        presenter.makeSpreadsheet(getTables());

        verify(view).showMessage(CategoryTotalPresenter.SPREADSHEET_FAILURE);
    }

    private List<String> getTables(){
        List<String> tempTable = new ArrayList<>();
        tempTable.add("food");
        tempTable.add("travel");
        return tempTable;
    }
}
