package com.tot_up.chris.tot_up.categoryoverview;


import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepository;
import com.tot_up.chris.tot_up.data.repos.categorydetailrepository.CategoryDetailRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryDetailRepositoryTest {
    public static final String FOOD = "food";
    public static final Expense EXPENSE = new Expense("3.40", DateUtil.getDate(), FOOD);

    @Mock
    DbInterface database;

    CategoryDetailRepositoryInterface repository;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        repository = new CategoryDetailRepository(database, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void getExpenses_Success(){
        List<Expense> expenseList = FakeListHelper.getFakeExpenseList();
        when(database.getExpenseList(FOOD)).thenReturn(expenseList);
        TestSubscriber<List<Expense>> testSubscriber = new TestSubscriber<>();

        repository.getExpenseList(FOOD)
                .subscribe(testSubscriber);

        verify(database).getExpenseList(FOOD);
        testSubscriber.assertValue(expenseList);
    }

    @Test
    public void addExpense_Success(){
        when(database.addExpense(FOOD, EXPENSE)).thenReturn(true);
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();

        repository.addExpense(FOOD, EXPENSE)
                .subscribe(testSubscriber);

        verify(database).addExpense(FOOD, EXPENSE);
        testSubscriber.assertValue(true);
    }

    @Test
    public void deleteExpense_Success(){
        int positionToDelete = 1;
        when(database.deleteExpense(FOOD, positionToDelete)).thenReturn(true);
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();

        repository.deleteExpense(FOOD, positionToDelete)
                .subscribe(testSubscriber);

        verify(database).deleteExpense(FOOD, positionToDelete);
        testSubscriber.assertValue(true);
    }

    @Test
    public void getExpense_Success(){
        int positionToGet = 1;
        when(database.getExpense(FOOD, positionToGet)).thenReturn(EXPENSE);
        TestSubscriber<Expense> testSubscriber = new TestSubscriber<>();

        repository.getExpense(FOOD, positionToGet)
                .subscribe(testSubscriber);

        verify(database).getExpense(FOOD, positionToGet);
        testSubscriber.assertValue(EXPENSE);
    }

    @Test
    public void getExpenseTotal_Success(){
        String expenseTotal = "2.30";
        when(database.getExpenseTotalSince(FOOD, DateUtil.getStartOfWeek())).thenReturn(expenseTotal);
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        repository.getExpenseTotal(FOOD, DateUtil.getStartOfWeek())
                .subscribe(testSubscriber);

        verify(database).getExpenseTotalSince(FOOD, DateUtil.getStartOfWeek());
        testSubscriber.assertValue(expenseTotal);
    }

}
