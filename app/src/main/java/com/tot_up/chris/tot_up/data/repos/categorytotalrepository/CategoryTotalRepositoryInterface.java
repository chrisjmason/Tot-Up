package com.tot_up.chris.tot_up.data.repos.categorytotalrepository;

import com.tot_up.chris.tot_up.data.model.Category;

import java.util.List;

import rx.Observable;

public interface CategoryTotalRepositoryInterface {

    Observable<List<Category>> getCategoryListWithTotals(String totalFromDate);

    Observable<List<String>> getCategoryNameList(String totalFromDate);

    Observable<Boolean> makeSpreadsheet(List<String> tables, String dateFrom);
}
