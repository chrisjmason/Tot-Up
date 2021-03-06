package com.tot_up.chris.tot_up;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.*;

import com.tot_up.chris.tot_up.categoryoverview.CategoryOverviewActivity;

@RunWith(AndroidJUnit4.class)
public class CategoryOverviewTests {

    private static final String CATEGORY_NAME = "food";
    private static final String CATEGORY_NAME2 = "travel";

    @Rule
    public ActivityTestRule<CategoryOverviewActivity> categoryOverviewTestRule = new ActivityTestRule<>(CategoryOverviewActivity.class);

    @Test
    public void startActivity_checkFabVisible(){
        checkViewVisible(R.id.fab_open_toolbar_category);
    }

    @Test
    public void startActivity_checkFabToolbarNotVisible(){
        checkNotVisible(R.id.inner_fabtoolbar);
    }

    @Test
    public void clickFab_checkFabToolbarVisible(){
        clickView(R.id.fab_open_toolbar_category);

        checkViewVisible(R.id.new_category_name);
        checkViewVisible(R.id.fab_add_category);
    }

    @Test
    public void addCategory_checkIsDisplayed(){
        clickView(R.id.fab_open_toolbar_category);
        type(R.id.new_category_name, CATEGORY_NAME);
        clickView(R.id.fab_add_category);

        checkViewHasDescendantText(R.id.category_recycler_view, CATEGORY_NAME);
    }

    @Test
    public void addEmptyCategory_checkIsNotDisplayed(){
        clickView(R.id.fab_open_toolbar_category);
        type(R.id.new_category_name, "");
        clickView(R.id.fab_add_category);

        checkRecyclerHasNoDescendant(R.id.category_recycler_view);
    }

    @Test
    public void clickCategory_checkDetailScreenDisplayed(){
        clickView(R.id.fab_open_toolbar_category);
        type(R.id.new_category_name, CATEGORY_NAME2);
        clickView(R.id.fab_add_category);

        clickRecyclerViewDescendant(R.id.category_recycler_view, CATEGORY_NAME2);
        checkViewHasDescendantText(R.id.category_detail_toolbar, CATEGORY_NAME2);
    }




}
