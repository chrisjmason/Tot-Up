package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.categorydetail.CategoryDetailInterface;
import com.tot_up.chris.tot_up.categorydetail.CategoryDetailPresenter;
import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import rx.Observable;

public class CategoryDetailPresenterTest {

    public static final String FOOD = "Food";

    @Mock
    CategoryDetailRepositoryInterface repository;
    @Mock
    CategoryDetailInterface.View view;

    CategoryDetailInterface.Presenter presenter;
    private Expense expenseToAdd;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new CategoryDetailPresenter(view, repository);
        expenseToAdd = new Expense("3.40", DateUtil.getDate(),FOOD);
    }

    @Test
    public void getExpenseList_Success(){
        List<Expense> expenseList = FakeListHelper.getFakeExpenseList();
        when(repository.getExpenses(FOOD)).thenReturn(Observable.just(expenseList));

        presenter.getExpenses(FOOD);

        verify(view).showExpenses(expenseList);
    }

    @Test
    public void getExpenseList_Failure(){
        when(repository.getExpenses(FOOD)).thenReturn(Observable.error(new IOException()));

        presenter.getExpenses(FOOD);

        verify(view).showError();
    }


    @Test
    public void getEmptyExpenseList_Success(){
        when(repository.getExpenses(FOOD)).thenReturn(Observable.just(FakeListHelper.getEmptyList()));

        presenter.getExpenses(FOOD);

        verify(view).showEmpty();
    }

    @Test
    public void addExpense_Success(){
        when(repository.addExpense(expenseToAdd)).thenReturn(Observable.just(true));

        presenter.addExpense(expenseToAdd);

        verify(view).showMessage();
    }

    @Test
    public void addExpense_Failure(){
        when(repository.addExpense(expenseToAdd)).thenReturn(Observable.just(false));

        presenter.addExpense(expenseToAdd);

        verify(view).showError();
    }
    
    @Test
    public void addExpense_Error(){
        when(repository.addExpense(expenseToAdd)).thenReturn(Observable.error(new IOException()));

        presenter.addExpense(expenseToAdd);

        verify(view).showError();
    }

    @Test
    public void expenseAddedViewUpdated_Success(){
        when(repository.addExpense(expenseToAdd)).thenReturn(Observable.just(true));

        presenter.addExpense(expenseToAdd);

        verify(repository).getExpenses(expenseToAdd.getCategoryName());
    }

}
