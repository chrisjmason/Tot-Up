package com.tot_up.chris.tot_up.categorydetail;

import com.tot_up.chris.tot_up.base.MvpInterface;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

public interface CategoryDetailInterface {

    interface View extends MvpInterface.View{

        void showExpenses(List<Expense> expenseList);

        void showError();

        void showMessage(String message);

        void showEmpty();

    }

    interface Presenter{

        void getExpenses(String categoryName);

        void addExpense(Expense expense);

        void deleteExpense(int position, String categoryName);

    }
}
