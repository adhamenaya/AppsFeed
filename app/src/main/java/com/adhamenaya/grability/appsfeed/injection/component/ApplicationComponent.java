package com.adhamenaya.grability.appsfeed.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import com.adhamenaya.grability.appsfeed.data.DataManager;
import com.adhamenaya.grability.appsfeed.data.SyncService;
import com.adhamenaya.grability.appsfeed.data.local.DatabaseHelper;
import com.adhamenaya.grability.appsfeed.data.local.PreferencesHelper;
import com.adhamenaya.grability.appsfeed.data.remote.RssService;
import com.adhamenaya.grability.appsfeed.injection.ApplicationContext;
import com.adhamenaya.grability.appsfeed.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RssService rssService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();
    Picasso picasso();

}
