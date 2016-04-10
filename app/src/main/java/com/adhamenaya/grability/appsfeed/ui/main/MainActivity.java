package com.adhamenaya.grability.appsfeed.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.adhamenaya.grability.appsfeed.R;
import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.ui.base.BaseActivity;
import com.adhamenaya.grability.appsfeed.ui.category.CategoryActivity;
import com.adhamenaya.grability.appsfeed.ui.details.DetailsActivity;
import com.adhamenaya.grability.appsfeed.util.CommonUtils;
import com.adhamenaya.grability.appsfeed.util.DialogFactory;
import com.adhamenaya.grability.appsfeed.util.OnItemClickListener;

public class MainActivity extends BaseActivity implements MainMvpView, OnItemClickListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.com.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    public static final String APPLICATION_ID = "application_id";

    @Inject MainPresenter mMainPresenter;
    @Inject
    ApplicationsAdapter mApplicationsAdapter;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    @Bind(R.id.relative_layout_main)
    RelativeLayout mRelativeLayout;

    int categoryPosition = 0;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate.
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mApplicationsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);

        String categoryId = getIntent().getExtras().getString(CategoryActivity.CATEGORY_ID);
        categoryPosition = getIntent().getExtras().getInt(CategoryActivity.CATEGORY_POSITION);

        mRelativeLayout.setBackgroundColor(
                Color.parseColor(CommonUtils.getBackgroundColor(getApplicationContext(),categoryPosition)));

        mMainPresenter.loadApplications(categoryId);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showApplications(List<Application> applications) {
        mApplicationsAdapter.setApplications(this,applications);
        mApplicationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_applications))
                .show();
    }

    @Override
    public void showApplicationsEmpty() {
        mApplicationsAdapter.setApplications(this,Collections.<Application>emptyList());
        mApplicationsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_applications, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(Object object, int position, View view) {
        Application application = (Application) object;
        Intent intent = DetailsActivity.getStartIntent(getApplicationContext());
        intent.putExtra(CategoryActivity.CATEGORY_POSITION,categoryPosition);
        intent.putExtra(APPLICATION_ID, Integer.parseInt(application.id.idAttributes.id));

        String animationName = getApplicationContext().getResources().getString(R.string.animate_icon);

        View viewStartAnimation = view.findViewById(R.id.image_view_application);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,viewStartAnimation,animationName);

        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
    }
}
