package uk.co.ribot.androidboilerplate.ui.category;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Category;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

/**
 * Created by adhamenaya on 4/8/2016.
 */
public interface CategoryMvpView extends MvpView {

    public void showCategories(List<Category> categoryList);

    public void showCategoriesEmpty();

    public void showError();

}
