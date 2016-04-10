package com.adhamenaya.grability.appsfeed.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.adhamenaya.grability.appsfeed.R;
import com.adhamenaya.grability.appsfeed.data.SyncService;
import com.adhamenaya.grability.appsfeed.ui.base.BaseActivity;
import com.adhamenaya.grability.appsfeed.ui.category.CategoryActivity;

/**
 * Created by adhamenaya on 4/10/2016.
 */
public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject SplashPresenter mSplashPresenter;

    @Bind(R.id.text_view_percentage)
    TextView mTextViewPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mSplashPresenter.attachView(this);

        mSplashPresenter.loadSplash();
    }

    @Override
    public void showSplash() {
        startService(SyncService.getStartIntent(this));

        // Just simulate the loading process.
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                handler.postDelayed(this, 200);
                counter += 5;
                mTextViewPercentage.setText(String.valueOf(counter) + "%");
                if (counter >= 100) {
                    handler.removeCallbacks(this);
                    finish();
                    startActivity(CategoryActivity.getStartIntent(getApplicationContext()));
                }
            }
        });

    }
}
