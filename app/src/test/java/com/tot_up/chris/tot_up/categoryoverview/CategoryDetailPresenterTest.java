package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.categorydetail.CategoryDetailInterface;
import com.tot_up.chris.tot_up.categorydetail.CategoryDetailPresenter;
import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import rx.Observable;

public class CategoryDetailPresenterTest {

    public static final String FOOD = "Food";
    public static final int POSITION = 1;

    @Mock
    CategoryDetailRepositoryInterface repository;
    @Mock
    CategoryDetailInterface.View view;

    private CategoryDetailInterface.Presenter presenter;
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
        mockGetExpensesRepositoryResponse(Observable.just(expenseList));

        presenterGetExpenses();

        verifyViewShowExpenses(expenseList);
    }

    @Test
    public void getExpenseList_Failure(){
        mockGetExpensesRepositoryResponse(Observable.error(new IOException()));

        presenterGetExpenses();

        verifyViewShowError();
    }


    @Test
    public void getEmptyExpenseList_Success(){
        mockGetExpensesRepositoryResponse(Observable.just(Collections.emptyList()));

        presenterGetExpenses();

        verify(view).showEmpty();
    }

    @Test
    public void addExpense_Success(){
        mockAddExpenseRepositoryResponse(Observable.just(true));

        presenterAddExpense();

        verify(view).showMessage(anyString());
    }

    @Test
    public void addExpense_Failure(){
        mockAddExpenseRepositoryResponse(Observable.just(false));

        presenterAddExpense();

        verifyViewShowError();
    }
    
    @Test
    public void addExpense_Error(){
        mockAddExpenseRepositoryResponse(Observable.error(new IOException()));

        presenterAddExpense();

        verifyViewShowError();
    }

    @Test
    public void addExpenseViewUpdated_Success(){
        mockAddExpenseRepositoryResponse(Observable.just(true));

        presenterAddExpense();

        verify(repository).getExpenseList(expenseToAdd.getCategoryName());
    }

    @Test
    public void deleteExpense_Failure(){
        mockDeleteExpenseRepositoryResponse(Observable.just(false));

        presenterDeleteExpense(POSITION);

        verifyViewShowError();
    }

    @Test
    public void deleteExpense_Error(){
        mockDeleteExpenseRepositoryResponse(Observable.error(new IOException()));

        presenterDeleteExpense(POSITION);

        verifyViewShowError();
    }

    @Test
    public void deleteExpenseViewUpdated_Success(){
        mockDeleteExpenseRepositoryResponse(Observable.just(true));

        presenterDeleteExpense(POSITION);

        verify(repository).getExpenseList(FOOD);
    }

    @Test
    public void goToExpenseDetail_Success(){
        presenter.goToDetail(expenseToAdd);

        verify(view).goToDetail(expenseToAdd);
    }

    private void mockGetExpensesRepositoryResponse(Observable observableResponse) {
        when(repository.getExpenseList(FOOD)).thenReturn(observableResponse);
    }

    private void mockAddExpenseRepositoryResponse(Observable observableResponse) {
        when(repository.addExpense(expenseToAdd.getCategoryName(),expenseToAdd)).thenReturn(observableResponse);
    }

    private void mockDeleteExpenseRepositoryResponse(Observable observableResponse){
        when(repository.deleteExpense(FOOD, POSITION)).thenReturn(observableResponse);
    }

    private void verifyViewShowExpenses(List<Expense> expenseList) {
        verify(view).showExpenses(expenseList);
    }

    private void verifyViewShowError() {
        verify(view).showError();
    }

    private void presenterGetExpenses() {
        presenter.getExpenses(FOOD);
    }

    private void presenterAddExpense() {
        presenter.addExpense(expenseToAdd);
    }

    private void presenterDeleteExpense(int position) {
        presenter.deleteExpense(position, FOOD);
    }



}
