package com.tot_up.chris.tot_up.categoryoverview;

import android.database.DatabaseErrorHandler;

import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeSubscriberHelper;
import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.categorytotalrepository.CategoryTotalRepository;
import com.tot_up.chris.tot_up.data.repos.categorytotalrepository.CategoryTotalRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

public class TotalRepositoryTest {

    @Mock
    DbInterface db;

    CategoryTotalRepositoryInterface repository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        repository = new CategoryTotalRepository(db, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void getCategoryList_Success(){
        List<Category> fakeList = FakeListHelper.getFakeCategoryList();
        String date = DateUtil.getDate();
        when(db.getCategoryListWithTotals(date)).thenReturn(fakeList);

        TestSubscriber<List<Category>> testSubscriber = FakeSubscriberHelper.getListTestSubscriber();

        repository.getCategoryListWithTotals(date)
                .subscribe(testSubscriber);

        verify(db).getCategoryListWithTotals(date);
        testSubscriber.assertValue(fakeList);
    }
}
