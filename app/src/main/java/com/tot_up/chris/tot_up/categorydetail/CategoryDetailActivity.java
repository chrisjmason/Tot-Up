package com.tot_up.chris.tot_up.categorydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity implements CategoryDetailInterface.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
    }

    @Override
    public void showExpenses(List<Expense> expenseList) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void goToDetail(Expense expense) {

    }
}
