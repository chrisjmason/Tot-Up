package com.tot_up.chris.tot_up.categoryoverview;

import com.tot_up.chris.tot_up.categoryoverview.TestHelpers.FakeListHelper;
import com.tot_up.chris.tot_up.data.db.DbInterface;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.repos.OverviewRepository;
import com.tot_up.chris.tot_up.data.repos.OverviewRepositoryInterface;
import com.tot_up.chris.tot_up.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
        List<Category> categoryList = FakeListHelper.getFakeCategoryList();
        when(database.getCategoryList()).thenReturn(categoryList);

        TestSubscriber<List<Category>> testSubscriber = new TestSubscriber<>();

        testSubscriber.assertNoErrors();

        repository.getCategoryList()
                .subscribe(testSubscriber);

        verify(database).getCategoryList();
        testSubscriber.assertValue(categoryList);
    }

    @Test
    public void addCategory_Success(){
        List<Category> categoryList = FakeListHelper.getFakeCategoryList();
        Category categoryToAdd = new Category("test3",DateUtil.getDate());

        when(database.addCategory(categoryToAdd)).thenReturn(categoryList.add(categoryToAdd));
        when(database.getCategoryList()).thenReturn(categoryList);

        TestSubscriber<List<Category>> testSubscriber = new TestSubscriber<>();
        testSubscriber.assertNoErrors();

        repository.addCategory(categoryToAdd)
                .subscribe(testSubscriber);

        verify(database).addCategory(categoryToAdd);
        verify(database).getCategoryList();
        verifyNoMoreInteractions(database);

        testSubscriber.assertValue(categoryList);
    }

    @Test
    public void deleteCategory_Success(){
        List<Category> categoryList = FakeListHelper.getFakeCategoryList();
        int positionToDelete = 1;
        Category categoryToDelete = categoryList.get(positionToDelete);

        when(database.deleteCategory(positionToDelete)).thenReturn(categoryList.remove(categoryToDelete));
        when(database.getCategoryList()).thenReturn(categoryList);

        TestSubscriber<List<Category>> testSubscriber = new TestSubscriber<>();
        testSubscriber.assertNoErrors();

        repository.deleteCategory(positionToDelete)
                .subscribe(testSubscriber);

        verify(database).deleteCategory(positionToDelete);
        verify(database).getCategoryList();
        verifyNoMoreInteractions(database);

        testSubscriber.assertValue(categoryList);
    }

    @Test
    public void getCategory_Success(){
        List<Category> categoryList = FakeListHelper.getFakeCategoryList();
        int positionToGet = 1;

        when(database.getCategory(positionToGet)).thenReturn(categoryList.get(positionToGet));

        TestSubscriber<Category> testSubscriber = new TestSubscriber<>();
        testSubscriber.assertNoErrors();

        repository.getCategory(positionToGet)
                .subscribe(testSubscriber);

        verify(database).getCategory(positionToGet);
        verifyNoMoreInteractions(database);

        testSubscriber.assertValue(categoryList.get(positionToGet));
    }
}
