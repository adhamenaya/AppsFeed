package com.adhamenaya.grability.appsfeed.ui.category;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.adhamenaya.grability.appsfeed.R;
import com.adhamenaya.grability.appsfeed.data.SyncService;
import com.adhamenaya.grability.appsfeed.data.model.Category;
import com.adhamenaya.grability.appsfeed.ui.base.BaseActivity;
import com.adhamenaya.grability.appsfeed.ui.main.MainActivity;
import com.adhamenaya.grability.appsfeed.util.OnItemClickListener;

public class CategoryActivity extends BaseActivity implements CategoryMvpView, OnItemClickListener {


    @Inject CategoryPresenter mCategoryPresenter;
    @Inject
    CategoryAdapter mCategoryAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.text_view_wait)
    TextView mTextViewWait;

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_POSITION = "category_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        mTextViewWait.setVisibility(View.VISIBLE);
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
        mTextViewWait.setVisibility(View.GONE);
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
    public void onItemClick(Object object, int position, View view) {
        Intent intent = MainActivity.getStartIntent(getApplicationContext());
        intent.putExtra(CATEGORY_ID,((Category)object).attributes.id);
        intent.putExtra(CATEGORY_POSITION,position);

        // Name of transition
        String transitionName = getApplicationContext().getResources().getString(R.string.animate_cate);

        // View of start transition
        View viewStartTransition = findViewById(R.id.relative_layout_category);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,view,transitionName);

        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CategoryActivity.class);
        return intent;
    }
}
