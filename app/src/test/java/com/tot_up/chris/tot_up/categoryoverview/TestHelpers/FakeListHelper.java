package com.tot_up.chris.tot_up.categoryoverview.TestHelpers;


import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.data.model.Expense;
import com.tot_up.chris.tot_up.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakeListHelper {

    public static List<Category> getFakeCategoryList(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("test1", DateUtil.getDate(), "2.40"));
        categoryList.add(new Category("test2", DateUtil.getDate(),"2.70"));
        return categoryList;
    }

    public static List<Expense> getFakeExpenseList(){
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(new Expense("2.30", DateUtil.getDate(), "Test"));
        expenseList.add(new Expense("1.60", DateUtil.getDate(), "Test"));
        expenseList.add(new Expense("3.55", DateUtil.getDate(), "Test"));
        return expenseList;
    }

    public static List<String> getFakeTablesList(){
        List<String> tempTable = new ArrayList<>();
        tempTable.add("food");
        tempTable.add("travel");
        return tempTable;
    }

    public static List getEmptyList(){
        return Collections.emptyList();
    }


}
