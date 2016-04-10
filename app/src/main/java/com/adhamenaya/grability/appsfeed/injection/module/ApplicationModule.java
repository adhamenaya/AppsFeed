package com.adhamenaya.grability.appsfeed.injection.module;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.adhamenaya.grability.appsfeed.data.remote.RssService;
import com.adhamenaya.grability.appsfeed.injection.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    RssService provideRssService() {
        return RssService.Creator.newRssService();
    }


    @Provides
    @Singleton
    Picasso providePicasso(@ApplicationContext Context context){
        return new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context,Integer.MAX_VALUE))
               // .indicatorsEnabled(true)
               // .loggingEnabled(true)
                .build();
    }
}
