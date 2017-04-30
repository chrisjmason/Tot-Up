package com.tot_up.chris.tot_up.categorytotals;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.tot_up.chris.tot_up.Injection;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryTotalsActivity extends AppCompatActivity implements CategoryTotalInterface.View {
    CategoryTotalInterface.Presenter presenter;
    CategoryTotalAdapter adapter;
    RecyclerView recyclerView;
    Button weekButton;
    Button monthButton;
    Button yearButton;
    Button spreadsheetButton;
    List<Button> buttonList;
    String currentTimePeriod;
    List<String> categoryNameList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_totals2);
        setUpPresenter();
        setUpUi();
        presenter.getCategoryListWithTotals(DateUtil.getStartOfWeek());
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
    public void showTotals(List<Category> categoryList) {
        adapter.setCategoryList(categoryList);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCategoryList(List<String> categoryList) {
        this.categoryNameList = categoryList;
    }

    public void setUpPresenter(){
        presenter = new CategoryTotalPresenter(this, Injection.provideCategoryTotalRepository());
    }

    public void setUpUi(){
        setUpToolbar();
        setUpAdapter();
        setUpRecycler();
        setUpTimeSelectorButtons();
        setUpSpreadsheetButton();
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.totals_toolbar);
        toolbar.setTitle("Reports");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);


    }

    private void setUpAdapter(){
        adapter = new CategoryTotalAdapter(presenter, getBaseContext());
    }

    private void setUpRecycler(){
        recyclerView = (RecyclerView) findViewById(R.id.totals_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpTimeSelectorButtons(){
        setUpWeekButton();
        setUpMonthButton();
        setUpYearButton();
        buttonList = new ArrayList<>();
        buttonList.add(weekButton);
        buttonList.add(monthButton);
        buttonList.add(yearButton);
        timeSelectorButtonPressed(weekButton);
    }

    private void setUpSpreadsheetButton(){
        spreadsheetButton = (Button) findViewById(R.id.report_button);
        spreadsheetButton.setOnClickListener(v -> makeSpreadsheetDialog());
    }


    private void setUpWeekButton(){
        weekButton = (Button) findViewById(R.id.week_button);

        weekButton.setOnClickListener(v -> {
            currentTimePeriod = DateUtil.getStartOfWeek();
            presenter.getCategoryListWithTotals(currentTimePeriod);
            timeSelectorButtonPressed(weekButton);
        });
    }

    private void setUpMonthButton(){
        monthButton = (Button) findViewById(R.id.month_button);
        monthButton.setOnClickListener(v -> {
            currentTimePeriod = DateUtil.getStartOfMonth();
            presenter.getCategoryListWithTotals(currentTimePeriod);
            timeSelectorButtonPressed(monthButton);
        });
    }

    private void setUpYearButton(){
        yearButton = (Button) findViewById(R.id.year_button);
        yearButton.setOnClickListener(v -> {
            currentTimePeriod = DateUtil.getStartOfYear();
            presenter.getCategoryListWithTotals(currentTimePeriod);
            timeSelectorButtonPressed(yearButton);
        });
    }

    private void timeSelectorButtonPressed(Button button){
        presenter.getCategoryNameList();
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGreen));
        
        for(Button buttonUnclick:buttonList){
            if(!buttonUnclick.equals(button)){
                buttonUnclick.setBackgroundColor(ContextCompat.getColor(this,R.color.colorLilac));
            }
        }
    }

    private void makeSpreadsheetDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryTotalsActivity.this, R.style.MyDialogTheme);
        Log.d("categoryList size", String.valueOf(categoryNameList.size()));
        CharSequence[] categoryNamesCharArray = categoryNameList.toArray(new CharSequence[categoryNameList.size()]);

        List<String> selectedCategories = new ArrayList<>();

        builder.setTitle("Which categories to export?")
                .setMultiChoiceItems(categoryNamesCharArray, null, (dialog, which, isChecked) -> {
                    String selected = categoryNamesCharArray[which].toString();
                    if(isChecked) {
                        selectedCategories.add(selected);
                    }else if(selectedCategories.contains(selected)){
                        selectedCategories.remove(which);
                    }
                })
                .setPositiveButton("Generate report", (dialog, which) -> {
                    presenter.makeSpreadsheet(selectedCategories, currentTimePeriod);
                })
                .create()
                .show();
    }


}
