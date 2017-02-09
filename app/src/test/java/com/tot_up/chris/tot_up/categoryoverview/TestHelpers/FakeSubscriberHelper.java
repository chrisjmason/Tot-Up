package com.tot_up.chris.tot_up.categoryoverview.TestHelpers;

import com.tot_up.chris.tot_up.data.model.Category;

import org.junit.Test;

import java.util.List;

import rx.observers.TestSubscriber;

public class FakeSubscriberHelper {

    private FakeSubscriberHelper() {
    }

    public static TestSubscriber<List<Category>> getListTestSubscriber() {
        TestSubscriber<List<Category>> testSubscriber = new TestSubscriber<>();
        testSubscriber.assertNoErrors();
        return testSubscriber;
    }

    public static TestSubscriber<Boolean> getCsvTestSubscriber(){
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        testSubscriber.assertNoErrors();
        return testSubscriber;
    }
}
