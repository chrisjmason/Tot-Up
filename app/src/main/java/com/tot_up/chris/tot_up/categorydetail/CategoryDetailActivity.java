package com.tot_up.chris.tot_up.categorydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tot_up.chris.tot_up.Injection;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.categoryoverview.CategoryOverviewActivity;
import com.tot_up.chris.tot_up.data.model.Expense;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity implements CategoryDetailInterface.View {
    private CategoryDetailAdapter adapter;
    private CategoryDetailInterface.Presenter presenter;
    private TextView monthPriceText;
    private TextView weekPriceText;
    String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        categoryName = getIntent().getStringExtra(CategoryOverviewActivity.CATEGORY_NAME);
        setUpPresenter();
        setUpUi();
        getExpenses();
    }

    @Override
    public void showExpenses(List<Expense> expenseList) {
        adapter.setExpenseList(expenseList);
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

    private void setUpPresenter() {
        presenter = new CategoryDetailPresenter(this, Injection.provideCategoryDetailRepository());
    }

    private void setUpUi(){
        adapter = setUpAdapter();
        setUpRecyclerView();
        setUpToolbar();
        setUpCollapsingToolbar();
        setUpCollapsingLayout();
    }

    private CategoryDetailAdapter setUpAdapter(){
        return new CategoryDetailAdapter(presenter, this);
    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.expense_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_detail_toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.category_detail_collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(true);
        collapsingToolbar.setTitle(categoryName);
    }

    private void setUpCollapsingLayout(){
        monthPriceText = (TextView) findViewById(R.id.month_price_text);
        weekPriceText = (TextView) findViewById(R.id.week_price_text);

        monthPriceText.setText("20.30");
        weekPriceText.setText("4.50");
    }

    private void getExpenses(){
        presenter.getExpenses(categoryName);
    }
}
