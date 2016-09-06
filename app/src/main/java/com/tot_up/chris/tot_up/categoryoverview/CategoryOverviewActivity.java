package com.tot_up.chris.tot_up.categoryoverview;

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
import android.widget.Toast;

import com.bowyer.app.fabtoolbar.FabToolbar;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.db.FakeDb;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.OverviewRepository;
import com.tot_up.chris.tot_up.util.CustomFabToolbar.CustomFabToolbar;

import java.util.List;

public class CategoryOverviewActivity extends AppCompatActivity implements CategoryOverviewInterface.View{

    RecyclerView recyclerView;
    FloatingActionButton addCategoryFab;
    DrawerLayout drawerLayout;
    CustomFabToolbar fabToolbar;

    CategoryOverviewInterface.Presenter presenter;
    CategoryOverviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
        presenter = new CategoryOverviewPresenter(this, new OverviewRepository(FakeDb.getInstance()));
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
    public void addCategory() {

    }

    @Override
    public void showMessage(String error) {
        Toast.makeText(CategoryOverviewActivity.this, error, Toast.LENGTH_SHORT).show();
    }


    private CategoryOverviewAdapter setUpAdapter(){
        return new CategoryOverviewAdapter(presenter,this);
    }

    private void setUpViews(){
        adapter = setUpAdapter();
        setUpRecyclerView(adapter);
        setUpToolbar();
        setUpNavView();
        setUpFabToolbar();
        setUpFab();
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
        addCategoryFab = (FloatingActionButton) findViewById(R.id.fab_add_category);
        fabToolbar.setFab(addCategoryFab);
    }

    private void setUpFab(){
        addCategoryFab.setOnClickListener(view -> {
            fabToolbar.expandFab();

        });

    }


}
