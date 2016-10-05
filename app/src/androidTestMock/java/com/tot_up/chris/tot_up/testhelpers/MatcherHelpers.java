package com.tot_up.chris.tot_up.testhelpers;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.tot_up.chris.tot_up.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class MatcherHelpers {

    public static void checkVisible(int viewId){
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    public static void checkNotVisible(int viewId){
        onView(withId(viewId)).check(matches(not(isDisplayed())));
    }

    public static void clickView(int viewId){
        onView(withId(viewId)).perform(click());
    }

    public static void type(int viewId, String textToType){
        onView(withId(viewId)).perform(typeText(textToType));
    }

    public static void checkViewHasDescendant(int viewId, String descendantText){
        onView(withId(viewId)).check(matches(hasDescendant(withText(descendantText))));
    }

    public static void checkRecyclerHasNoDescendant(int viewId){
        onView(withId(viewId)).check(matches(not(hasDescendant(withText("")))));
    }

    public static void clickRecyclerViewDescendant(int viewId, String descendantText){
        onView(withId(viewId)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(descendantText)),click()));
    }

}
