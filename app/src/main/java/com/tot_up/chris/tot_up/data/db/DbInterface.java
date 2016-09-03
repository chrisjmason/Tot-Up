package com.tot_up.chris.tot_up.data.db;

import com.tot_up.chris.tot_up.data.model.Category;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.List;

public interface DbInterface {

    List<Category> getCategoryList();

    boolean addCategory();

}
