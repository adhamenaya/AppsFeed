package com.adhamenaya.grability.appsfeed.ui.category;

import java.util.List;

import com.adhamenaya.grability.appsfeed.data.model.Category;
import com.adhamenaya.grability.appsfeed.ui.base.MvpView;

/**
 * Created by adhamenaya on 4/8/2016.
 */
public interface CategoryMvpView extends MvpView {

    public void showCategories(List<Category> categoryList);

    public void showCategoriesEmpty();

    public void showError();

}
