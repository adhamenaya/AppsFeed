package com.adhamenaya.grability.appsfeed.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adhamenaya.grability.appsfeed.GrabilityApplication;
import com.adhamenaya.grability.appsfeed.injection.component.ActivityComponent;
import com.adhamenaya.grability.appsfeed.injection.component.DaggerActivityComponent;
import com.adhamenaya.grability.appsfeed.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(GrabilityApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
