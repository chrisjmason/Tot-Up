package com.tot_up.chris.tot_up.categoryoverview;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bowyer.app.fabtoolbar.FabToolbar;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.db.FakeDb;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.OverviewRepository;
import com.tot_up.chris.tot_up.util.CustomFabToolbar.CustomFabToolbar;
import com.tot_up.chris.tot_up.util.DateUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryOverviewActivity extends AppCompatActivity implements CategoryOverviewInterface.View{

    RecyclerView recyclerView;
    FloatingActionButton openToolbarFab;
    FloatingActionButton addCategoryFab;
    DrawerLayout drawerLayout;
    CustomFabToolbar fabToolbar;

    CategoryOverviewInterface.Presenter presenter;
    CategoryOverviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
        setUpPresenter();
        setUpViews();
        presenter.getCategories();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        adapter.setCategoryList(categoryList);
    }

    @Override
    public void showMessage(String error) {
        Toast.makeText(CategoryOverviewActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyScreen() {

    }

    private void setUpPresenter(){
        //move repository initialisation to injector class
        OverviewRepository repository = new OverviewRepository(FakeDb.getInstance());
        repository.setSchedulers(Schedulers.io(), AndroidSchedulers.mainThread());

        presenter = new CategoryOverviewPresenter(this,repository);

    }

    private CategoryOverviewAdapter setUpAdapter(){
        return new CategoryOverviewAdapter(presenter,this);
    }

    private void setUpViews(){
        adapter = setUpAdapter();
        setUpRecyclerView(adapter);
        setUpToolbar();
        setUpNavView();
        setUpOpenToolbarFab();
        setUpFabToolbar();
        setUpAddCategoryFab();
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void setUpNavView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView!=null){
            drawerContentInit(navigationView);
        }
    }

    public void drawerContentInit(NavigationView navigationView){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(menu -> {
            //switch statement goes here

            return false;
        });
    }

    private void setUpRecyclerView(CategoryOverviewAdapter adapter){
        recyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpFabToolbar(){
        fabToolbar = (CustomFabToolbar) findViewById(R.id.fabtoolbar_category);
        fabToolbar.setFab(openToolbarFab);
        fabToolbar.setOnClickListener(view -> fabToolbar.contractFab());
    }

    private void setUpOpenToolbarFab(){
        openToolbarFab = (FloatingActionButton) findViewById(R.id.fab_open_toolbar_category);
        openToolbarFab.setOnClickListener(view -> {
            openToolbarFab.setVisibility(View.INVISIBLE);
            fabToolbar.expandFab();
        });
    }

    private void setUpAddCategoryFab(){
        addCategoryFab = (FloatingActionButton) findViewById(R.id.fab_add_category);
        addCategoryFab.setOnClickListener(view -> {
            String newCategoryName = getCategoryNameFromEditText();
            addCategory(new Category(newCategoryName, DateUtil.getDate()));
            fabToolbar.contractFab();
            closeKeyboard();
        });
    }

    private String getCategoryNameFromEditText(){
        EditText newCategoryNameEditText = (EditText) findViewById(R.id.new_category_name);
        String newCategoryName = newCategoryNameEditText.getText().toString();
        newCategoryNameEditText.setText("");
        return newCategoryName;
    }

    private void addCategory(Category category){
        presenter.addCategory(category);
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
