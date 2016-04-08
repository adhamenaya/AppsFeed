package uk.co.ribot.androidboilerplate.ui.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.data.model.Category;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.main.ApplicationsAdapter;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;
import uk.co.ribot.androidboilerplate.ui.main.MainMvpView;

public class CategoryActivity extends BaseActivity implements CategoryMvpView, CategoryAdapter.OnItemClickListener {


    @Inject CategoryPresenter mCategoryPresenter;
    @Inject
    CategoryAdapter mCategoryAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_POSITION = "category_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        // Sync data
        startService(SyncService.getStartIntent(this));

        mCategoryAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryPresenter.attachView(this);
        mCategoryPresenter.loadCategories();
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        mCategoryAdapter.setCategories(categoryList);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesEmpty() {
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCategoryPresenter.detachView();
    }

    @Override
    public void onItemClick(Category category, int position) {

        Intent intent = MainActivity.getStartIntent(getApplicationContext(),false);
        intent.putExtra(CATEGORY_ID,category.attributes.id);
        intent.putExtra(CATEGORY_POSITION,position);

        startActivity(intent);
    }
}
