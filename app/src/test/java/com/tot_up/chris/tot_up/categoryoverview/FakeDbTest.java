package com.tot_up.chris.tot_up.categoryoverview;


import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.db.FakeDb;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class FakeDbTest {

    Expense expenseToAdd = new Expense("2.30", DateUtil.getDate(),"Food");

    @Test
    public void getCategoryList_emptyListReturned(){
        DbInterface fakeDb = FakeDb.getFreshInstance();

        List<Category> receivedList = fakeDb.getCategoryList();

        assertThat("Category list is empty", receivedList.isEmpty());
    }

    @Test
    public void addCategory_categoryAdded(){
        DbInterface fakeDb = FakeDb.getFreshInstance();
        Category categoryToAdd = new Category("Train", DateUtil.getDate());

        fakeDb.addCategory(categoryToAdd);
        List<Category> receivedList = fakeDb.getCategoryList();

        assertThat("Category added", receivedList.contains(categoryToAdd));
    }

    @Test
    public void addCategoryGetExpenseList_emptyListReturned(){
        DbInterface fakeDb = FakeDb.getFreshInstance();
        Category categoryToAdd = new Category("Food", DateUtil.getDate());
        fakeDb.addCategory(categoryToAdd);

        List<Expense> receivedList = fakeDb.getExpenses(categoryToAdd.getName());
        assertThat("Expense List is empty", receivedList.isEmpty());
    }

    @Test
    public void addExpenseToCategory_expenseAdded(){
        DbInterface fakeDb = FakeDb.getFreshInstance();
        Category categoryToAdd = new Category("Food", DateUtil.getDate());

        fakeDb.addCategory(categoryToAdd);
        fakeDb.addExpense(categoryToAdd.getName(), expenseToAdd);
        List<Expense> listReceived = fakeDb.getExpenses(categoryToAdd.getName());

        assertThat("Expense is added", listReceived.contains(expenseToAdd));
    }

    @Test
    public void deleteExpenseInCategory_checkDeleted(){
        DbInterface fakeDb = FakeDb.getFreshInstance();
        Category categoryToAdd = new Category("Food", DateUtil.getDate());

        fakeDb.addCategory(categoryToAdd);
        fakeDb.addExpense(categoryToAdd.getName(), expenseToAdd);
        fakeDb.deleteExpense(categoryToAdd.getName(), 0);
        List<Expense> listReceived = fakeDb.getExpenses(categoryToAdd.getName());

        assertThat("Expense has been deleted", !listReceived.contains(expenseToAdd));
    }
}
