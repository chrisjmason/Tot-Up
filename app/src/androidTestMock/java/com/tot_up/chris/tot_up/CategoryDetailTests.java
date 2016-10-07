package com.tot_up.chris.tot_up;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tot_up.chris.tot_up.categorydetail.CategoryDetailActivity;

import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CategoryDetailTests {

    static final String EXPENSE_COST = "3.00";

    @Rule
    public ActivityTestRule<CategoryDetailActivity> activityTestRule = new ActivityTestRule<>(CategoryDetailActivity.class);

    @Test
    public void startActivity_checkEmptyScreenShown(){
        checkViewVisible(R.id.expense_empty_text);
        checkNotVisible(R.id.expense_recycler);
    }

    @Test
    public void startActivity_checkFabVisible(){
        checkViewVisible(R.id.fab_open_toolbar_expense);
    }

    @Test
    public void startActivity_checkFabToolbarNotVisible(){
        checkNotVisible(R.id.inner_fabtoolbar_expense);
    }

    @Test
    public void clickFab_checkFabToolbarVisible(){
        clickView(R.id.fab_open_toolbar_expense);
        checkViewVisible(R.id.inner_fabtoolbar_expense);
        checkNotVisible(R.id.fab_open_toolbar_expense);
    }

//    @Test
//    public void clickToolbar_checkToolbarNotVisible(){
//        clickView(R.id.fab_open_toolbar_expense);
//        checkViewVisible(R.id.inner_fabtoolbar_expense);
//
//        activityTestRule.getActivity().onBackPressed();
//
//        checkNotVisible(R.id.inner_fabtoolbar_expense);
//    }

    @Test
    public void addExpenseNoCamera_checkShown(){
        clickView(R.id.fab_open_toolbar_expense);
        type(R.id.new_expense_cost, EXPENSE_COST);
        clickView(R.id.fab_add_expense_no_camera);

        checkNotVisible(R.id.inner_fabtoolbar_expense);
        checkViewHasDescendantText(R.id.expense_recycler, EXPENSE_COST);
    }

}
