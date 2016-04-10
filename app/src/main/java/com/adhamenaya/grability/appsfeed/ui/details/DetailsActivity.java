package com.adhamenaya.grability.appsfeed.ui.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.adhamenaya.grability.appsfeed.R;
import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.ui.base.BaseActivity;
import com.adhamenaya.grability.appsfeed.ui.category.CategoryActivity;
import com.adhamenaya.grability.appsfeed.ui.main.MainActivity;
import com.adhamenaya.grability.appsfeed.util.CommonUtils;

/**
 * Created by adhamenaya on 4/9/2016.
 */
public class DetailsActivity extends BaseActivity implements DetailsMvpView {

    @Inject DetailsPresenter mDetailsPresenter;

    @Inject
    Picasso picasso;

    @Bind(R.id.relative_layout_top)
    RelativeLayout mRelativeLayoutTop;

    @Bind(R.id.relative_layout_bottom)
    LinearLayout mLinearLayout;

    @Bind(R.id.text_view_name)
    TextView textViewName;

    @Bind(R.id.text_view_summary)
    TextView textViewSummary;

    @Bind(R.id.image_view_icon)
    ImageView mImageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mDetailsPresenter.attachView(this);

        int categoryPosition = getIntent().getExtras().getInt(CategoryActivity.CATEGORY_POSITION);
        int applicationId = getIntent().getExtras().getInt(MainActivity.APPLICATION_ID);

        mRelativeLayoutTop.setBackgroundColor(
                Color.parseColor(CommonUtils.getBackgroundColor(getApplicationContext(), categoryPosition)));

        int nextColorPosition = categoryPosition + 1;
        if(nextColorPosition ==15) nextColorPosition = 13;

        mLinearLayout.setBackgroundColor(
                Color.parseColor(CommonUtils.getBackgroundColor(getApplicationContext(), nextColorPosition)));

        mDetailsPresenter.loadApplication("-1",applicationId);

    }

    @Override
    public void showDetails(Application application) {
        textViewName.setText(application.name.label);
        textViewSummary.setText(application.summary.label);

        if(picasso !=null
                && application.images.size()>0
                && !application.images.get(0).label.isEmpty()){

            final String imagePath = application.images.get(0).label;

            picasso.load(imagePath)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mImageViewIcon, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError() {
                            // Try to get image online
                            picasso.load(imagePath).into(mImageViewIcon);
                        }
                    });
        }
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        return intent;
    }
}
