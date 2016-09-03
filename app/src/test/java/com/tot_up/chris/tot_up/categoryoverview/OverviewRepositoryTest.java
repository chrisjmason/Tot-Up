package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.OverviewRepository;
import com.tot_up.chris.tot_up.data.repos.OverviewRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

public class OverviewRepositoryTest {
    @Mock
    DbInterface database;

    OverviewRepositoryInterface repository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        repository = new OverviewRepository(database);
        repository.setSchedulers(Schedulers.immediate(),Schedulers.immediate());
    }

    @Test
    public void getCategoryList_Success(){
        List<Category> categoryList = getFakeList();
        when(database.getCategoryList()).thenReturn(categoryList);

        TestSubscriber<List<Category>> testSubscriber = new TestSubscriber<>();

        testSubscriber.assertNoErrors();

        repository.getCategoryList()
                .subscribe(testSubscriber);

        testSubscriber.assertValue(categoryList);

    }

    public List<Category> getFakeList(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("test1", DateUtil.getDate()));
        categoryList.add(new Category("test2", DateUtil.getDate()));
        return categoryList;
    }
}