package com.tot_up.chris.tot_up.categorydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tot_up.chris.tot_up.Injection;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.categoryoverview.CategoryOverviewActivity;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.expensedetail.ExpenseDetailActivity;
import com.tot_up.chris.tot_up.util.customfabtoolbar.CustomFabToolbar;
import com.tot_up.chris.tot_up.util.DateUtil;
import com.tot_up.chris.tot_up.util.ImageFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity implements CategoryDetailInterface.View {
    public static final String IMAGE_PATH = "imgpath";
    public static final String EXPENSE_PRICE = "price";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private CategoryDetailAdapter adapter;
    private CategoryDetailInterface.Presenter presenter;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private TextView monthPriceText;
    private TextView weekPriceText;
    private EditText expenseCostText;
    private FloatingActionButton openToolbarFab;
    CustomFabToolbar fabToolbar;
    String categoryName;
    String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        categoryName = getIntent().getStringExtra(CategoryOverviewActivity.CATEGORY_NAME);
        setUpPresenter();
        setUpUi();
        getExpenses();
        getExpenseTotals();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            String expenseCost = expenseCostText.getText().toString();
            Expense expenseToAdd = new Expense(expenseCost, DateUtil.getDate(), categoryName, imagePath);
            presenter.addExpense(expenseToAdd);
            expenseCostText.setText("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(fabToolbar.isFabExpanded()){
            fabToolbar.contractFab();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void showExpenses(List<Expense> expenseList) {
        emptyText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setExpenseList(expenseList);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmpty() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToDetail(Expense expense) {
        Intent intent = new Intent(this, ExpenseDetailActivity.class);
        intent.putExtra(IMAGE_PATH, expense.getImageSrc());
        intent.putExtra(EXPENSE_PRICE, expense.getDecimalPrice().toString());
        startActivity(intent);
    }

    @Override
    public void showMonthTotal(String total) {
        monthPriceText.setText(total);
    }

    @Override
    public void showWeekTotal(String total) {
        weekPriceText.setText(total);
    }

    private void setUpPresenter() {
        presenter = new CategoryDetailPresenter(this, Injection.provideCategoryDetailRepository());
    }

    private void setUpUi(){
        adapter = setUpAdapter();
        setUpRecyclerView();
        setUpEmptyText();
        setUpToolbar();
        setUpCollapsingToolbar();
        setUpCollapsingLayout();
        setUpOpenToolbarFab();
        setUpFabToolbar();
        setUpAddExpenseNoCameraFab();
        setUpAddExpenseCameraFab();
    }

    private CategoryDetailAdapter setUpAdapter(){
        return new CategoryDetailAdapter(presenter, this);
    }

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.expense_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpEmptyText(){
        emptyText = (TextView) findViewById(R.id.expense_empty_text);
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_detail_toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
    }

    private void setUpCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.category_detail_collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(true);
        collapsingToolbar.setTitle(categoryName);
    }

    private void setUpCollapsingLayout(){
        monthPriceText = (TextView) findViewById(R.id.month_price_text);
        weekPriceText = (TextView) findViewById(R.id.week_price_text);
    }

    private void setUpFabToolbar(){
        fabToolbar = (CustomFabToolbar) findViewById(R.id.fabtoolbar_expense);
        RelativeLayout innerLayout = (RelativeLayout) findViewById(R.id.inner_fabtoolbar_expense);
        expenseCostText = (EditText) findViewById(R.id.new_expense_cost);
        fabToolbar.setFab(openToolbarFab);

        innerLayout.setOnClickListener(v -> {
            fabToolbar.contractFab();
            closeKeyboard();
        });
    }

    private void setUpOpenToolbarFab(){
        openToolbarFab = (FloatingActionButton) findViewById(R.id.fab_open_toolbar_expense);
        openToolbarFab.setOnClickListener(v -> {
            openToolbarFab.setVisibility(View.INVISIBLE);
            fabToolbar.expandFab();
        });
    }

    private void setUpAddExpenseNoCameraFab(){
        FloatingActionButton addExpenseFab = (FloatingActionButton) findViewById(R.id.fab_add_expense_no_camera);

        addExpenseFab.setOnClickListener(v -> {
            String expenseCost = expenseCostText.getText().toString();
            presenter.addExpense(new Expense(expenseCost, DateUtil.getDate(), categoryName));
            expenseCostText.setText("");
            closeKeyboard();
            fabToolbar.contractFab();
        });
    }

    private void setUpAddExpenseCameraFab(){
        FloatingActionButton addExpenseCameraFab = (FloatingActionButton) findViewById(R.id.fab_add_expense_camera);
        addExpenseCameraFab.setOnClickListener(v -> {
            takePhoto();
            closeKeyboard();
            fabToolbar.contractFab();
        });
    }

    private void getExpenses(){
        presenter.getExpenses(categoryName);
    }

    private void getExpenseTotals(){
        presenter.getWeekExpenseTotal(categoryName);
        presenter.getMonthExpenseTotal(categoryName);
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = getImageFile();
        imagePath = imageFile.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageFileUtil.getImageUri(getBaseContext(), imageFile));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private File getImageFile(){
        File imageFile = null;
        try{
            imageFile = ImageFileUtil.createImageFile(getBaseContext());
        }catch (IOException ex){
            ex.printStackTrace();
            showMessage("Error, please try again");
        }
        return imageFile;
    }


}
