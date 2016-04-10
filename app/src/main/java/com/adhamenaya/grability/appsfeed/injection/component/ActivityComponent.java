package com.adhamenaya.grability.appsfeed.injection.component;

import dagger.Component;
import com.adhamenaya.grability.appsfeed.injection.PerActivity;
import com.adhamenaya.grability.appsfeed.injection.module.ActivityModule;
import com.adhamenaya.grability.appsfeed.ui.category.CategoryActivity;
import com.adhamenaya.grability.appsfeed.ui.details.DetailsActivity;
import com.adhamenaya.grability.appsfeed.ui.main.MainActivity;
import com.adhamenaya.grability.appsfeed.ui.splash.SplashActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(CategoryActivity categoryActivity);

    void inject(DetailsActivity detailsActivity);

    void inject(SplashActivity splashActivity);

}
