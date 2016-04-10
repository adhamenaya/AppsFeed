package com.adhamenaya.grability.appsfeed;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;
import com.adhamenaya.grability.appsfeed.injection.component.ApplicationComponent;
import com.adhamenaya.grability.appsfeed.injection.component.DaggerApplicationComponent;
import com.adhamenaya.grability.appsfeed.injection.module.ApplicationModule;

public class GrabilityApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
         }
    }

    public static GrabilityApplication get(Context context) {
        return (GrabilityApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
