package com.tot_up.chris.tot_up;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.checkNotVisible;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.checkRecyclerHasDescendant;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.checkRecyclerHasNoDescendant;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.checkVisible;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.clickView;
import static com.tot_up.chris.tot_up.testhelpers.MatcherHelpers.type;


import com.tot_up.chris.tot_up.categoryoverview.CategoryOverviewActivity;

@RunWith(AndroidJUnit4.class)
public class CategoryOverviewTests {

    private static final String CATEGORY_NAME = "food";

    @Rule
    public ActivityTestRule<CategoryOverviewActivity> categoryOverviewTestRule = new ActivityTestRule<>(CategoryOverviewActivity.class);

    @Test
    public void startActivity_checkFabVisible(){
        checkVisible(R.id.fab_open_toolbar_category);
    }

    @Test
    public void startActivity_checkFabToolbarNotVisible(){
        checkNotVisible(R.id.inner_fabtoolbar);
    }

    @Test
    public void clickFab_checkFabToolbarVisible(){
        clickView(R.id.fab_open_toolbar_category);

        checkVisible(R.id.new_category_name);
        checkVisible(R.id.fab_add_category);
    }

    @Test
    public void addCategory_checkIsDisplayed(){
        clickView(R.id.fab_open_toolbar_category);
        type(R.id.new_category_name, CATEGORY_NAME);
        clickView(R.id.fab_add_category);

        checkRecyclerHasDescendant(R.id.category_recycler_view, CATEGORY_NAME);
    }

    @Test
    public void addEmptyCategory_checkIsNotDisplayed(){
        clickView(R.id.fab_open_toolbar_category);
        type(R.id.new_category_name, "");
        clickView(R.id.fab_add_category);

        checkRecyclerHasNoDescendant(R.id.category_recycler_view);
    }




}
